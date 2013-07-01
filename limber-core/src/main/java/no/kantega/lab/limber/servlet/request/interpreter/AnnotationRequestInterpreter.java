package no.kantega.lab.limber.servlet.request.interpreter;

import no.kantega.lab.limber.exception.LimberRequestMappingException;
import no.kantega.lab.limber.exception.LimberRequestPathConflictException;
import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.DefaultRequestMapping;
import no.kantega.lab.limber.servlet.context.IHttpServletRequestWrapper;
import no.kantega.lab.limber.servlet.context.ILimberApplicationContext;
import no.kantega.lab.limber.servlet.context.IRequestMapping;
import no.kantega.lab.limber.servlet.meta.RequestMapping;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnnotationRequestInterpreter implements IRequestInterpreter {

    private final Map<String, Class<? extends AbstractRenderable>> requestMapping;
    private final Map<Class<? extends AbstractRenderable>, String> requestMappingBacklink;

    private static final String LIMBER_REQUEST_REGEX = "^"
            // Version ID (group 1)
            + "(" + UUID_V4_REGEX + ")"
            // Optional Ajax ID (group 2)
            + "(?:"
            + ":(" + UUID_V4_REGEX + ")"
            + ")?"
            + "$";

    private final Pattern pathPattern;

    public AnnotationRequestInterpreter(@Nonnull ILimberApplicationContext limberApplicationContext) {
        pathPattern = Pattern.compile(LIMBER_REQUEST_REGEX);
        this.requestMapping = new HashMap<String, Class<? extends AbstractRenderable>>();
        this.requestMappingBacklink = new WeakHashMap<Class<? extends AbstractRenderable>, String>();
        for (String packageElement : limberApplicationContext.getRegisteredPackages()) {
            scanPath(packageElement);
        }
    }

    private void scanPath(@Nonnull String scanPackage) {

        Reflections reflections = new Reflections(scanPackage);
        Collection<Class<?>> foundTypes = reflections.getTypesAnnotatedWith(RequestMapping.class);

        for (Class<?> type : foundTypes) {
            if (!AbstractRenderable.class.isAssignableFrom(type)) {
                throw new LimberRequestMappingException(type);
            }
            RequestMapping requestMappingAnnotation = type.getAnnotation(RequestMapping.class);
            boolean firstRun = true;
            for (String path : requestMappingAnnotation.value()) {
                Class<?> conflictingClass = requestMapping.get(path);
                if (conflictingClass != null) {
                    throw new LimberRequestPathConflictException(path, type, conflictingClass);
                }
                Class<? extends AbstractRenderable> renderableClass = castToRenderable(type);
                if (firstRun) {
                    firstRun = false;
                    requestMappingBacklink.put(renderableClass, path);
                }
                requestMapping.put(path, renderableClass);
            }
        }

    }

    @SuppressWarnings("unchecked")
    private Class<? extends AbstractRenderable> castToRenderable(@Nonnull Class<?> type) {
        return (Class<? extends AbstractRenderable>) type;
    }

    @Override
    public IRequestMapping interpret(@Nonnull IHttpServletRequestWrapper httpServletRequestWrapper) {

        // Try to find registered class.
        Class<? extends AbstractRenderable> renderableClass = requestMapping.get(httpServletRequestWrapper.getRequestUri());
        if (renderableClass == null) {
            return null;
        }

        String limberQuery = httpServletRequestWrapper.getQueryArgument("limber");
        UUID versionId = null, ajaxId = null;
        if (limberQuery != null) {
            Matcher pathMatcher = pathPattern.matcher(limberQuery);
            if (pathMatcher.find()) {
                String versionIdString = pathMatcher.group(1);
                if (versionIdString != null) {
                    versionId = UUID.fromString(versionIdString);
                }
                String ajaxIdString = pathMatcher.group(2);
                if (pathMatcher.group(2) != null) {
                    ajaxId = UUID.fromString(ajaxIdString);
                }
            }
        }

        return new DefaultRequestMapping(
                httpServletRequestWrapper.getSessionId(),
                renderableClass,
                versionId,
                ajaxId);
    }

    @Override
    public URI resolve(@Nonnull Class<? extends AbstractRenderable> renderableClass, UUID versionId, UUID ajaxId) {
        String backlink = requestMappingBacklink.get(renderableClass);
        if (backlink == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(backlink);
        if (versionId != null) {
            stringBuilder.append("?limber=");
            stringBuilder.append(versionId.toString());
            if (ajaxId != null) {
                stringBuilder.append(":");
                stringBuilder.append(ajaxId.toString());
            }
        }
        try {
            return new URI(stringBuilder.toString());
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
