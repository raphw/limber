package no.kantega.lab.limber.servlet.request.interpreter;

import no.kantega.lab.limber.exception.LimberRequestMappingException;
import no.kantega.lab.limber.exception.LimberRequestPathConflictException;
import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.meta.RequestMapping;
import no.kantega.lab.limber.servlet.request.DefaultLimberRequest;
import no.kantega.lab.limber.servlet.request.ILimberRequest;
import no.kantega.lab.limber.servlet.request.RawRequest;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnnotationRequestInterpreter implements IRequestInterpreter {

    private final Map<String, Class<? extends IRenderable>> requestMapping;
    private final Map<Class<? extends IRenderable>, String> requestMappingBacklink;

    private static final String LIMBER_REQUEST_REGEX = "^"
            // Version ID (group 1)
            + "(" + UUID_V4_REGEX + ")"
            // Optional Ajax ID (group 2)
            + "(?:"
            + ":(" + UUID_V4_REGEX + ")"
            + ")?"
            + "$";

    private final Pattern pathPattern;

    public AnnotationRequestInterpreter(@Nonnull String scanPackage) {
        pathPattern = Pattern.compile(LIMBER_REQUEST_REGEX);
        this.requestMapping = new HashMap<String, Class<? extends IRenderable>>();
        this.requestMappingBacklink = new WeakHashMap<Class<? extends IRenderable>, String>();
        scanPath(scanPackage);
    }

    private void scanPath(@Nonnull String scanPackage) {

        Reflections reflections = new Reflections(scanPackage);
        Collection<Class<?>> foundTypes = reflections.getTypesAnnotatedWith(RequestMapping.class);

        for (Class<?> type : foundTypes) {
            if (!IRenderable.class.isAssignableFrom(type)) {
                throw new LimberRequestMappingException(type);
            }
            RequestMapping requestMappingAnnotation = type.getAnnotation(RequestMapping.class);
            boolean firstRun = true;
            for (String path : requestMappingAnnotation.value()) {
                Class<?> conflictingClass = requestMapping.get(path);
                if (conflictingClass != null) {
                    throw new LimberRequestPathConflictException(path, type, conflictingClass);
                }
                Class<? extends IRenderable> renderableClass = castToRenderable(type);
                if (firstRun) {
                    firstRun = false;
                    requestMappingBacklink.put(renderableClass, path);
                }
                requestMapping.put(path, renderableClass);
            }
        }

    }

    @SuppressWarnings("unchecked")
    private Class<? extends IRenderable> castToRenderable(@Nonnull Class<?> type) {
        return (Class<? extends IRenderable>) type;
    }

    @Override
    public ILimberRequest interpret(@Nonnull RawRequest rawRequest) {

        // Try to find registered class.
        Class<? extends IRenderable> renderableClass = requestMapping.get(rawRequest.getRequestURI());
        if (renderableClass == null) {
            return null;
        }

        String limberQuery = rawRequest.getQueryArgument("limber");
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

        return new DefaultLimberRequest(
                rawRequest.getSessionId(),
                renderableClass,
                versionId,
                ajaxId);
    }

    @Override
    public URI resolve(@Nonnull Class<? extends IRenderable> renderableClass, UUID versionId, UUID ajaxId) {
        String backlink = requestMappingBacklink.get(renderableClass);
        if (backlink == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(backlink);
        if (versionId != null) {
            stringBuilder.append("?limber=");
            stringBuilder.append(versionId.toString());
            if(ajaxId != null) {
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
