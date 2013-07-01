package no.kantega.lab.limber.servlet.request.interpreter;

import no.kantega.lab.limber.exception.LimberRequestMappingException;
import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.DefaultRequestMapping;
import no.kantega.lab.limber.servlet.context.IHttpServletRequestWrapper;
import no.kantega.lab.limber.servlet.context.IRequestMapping;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LimberRequestExpressionInterpreter implements IRequestInterpreter {

    private static final String LIMBER_REQUEST_REGEX = "^"
            // Package name is required (group 1)
            + "(" + JAVA_PACKAGE_NAME_REGEX + ")"
            // Optionally followed by UUID representing version (group 2)
            + "(?:"
            + ":(" + UUID_V4_REGEX + ")"
            //Optionally followed by another UUID representing Ajax callback (group 3)
            + "(?:"
            + ":(" + UUID_V4_REGEX + ")" +
            ")?"
            + ")?"
            + "$";

    private final Pattern pathPattern;

    public LimberRequestExpressionInterpreter() {
        pathPattern = Pattern.compile(LIMBER_REQUEST_REGEX);
    }

    @Override
    public IRequestMapping interpret(@Nonnull IHttpServletRequestWrapper httpServletRequestWrapper) {

        // Check if this expression interpreter can be applied at all.
        String limberQuery = httpServletRequestWrapper.getQueryArgument("limber");
        if (limberQuery == null || !"/".equals(httpServletRequestWrapper.getRequestUri())) {
            return null;
        }
        Matcher pathMatcher = pathPattern.matcher(limberQuery);
        if (!pathMatcher.find()) {
            return null;
        }

        // Find represented class and check if it interprets AbstractRenderable .
        Class<?> clazz;
        try {
            clazz = Class.forName(pathMatcher.group(1));
        } catch (ClassNotFoundException e) {
            return null;
        }
        if (!AbstractRenderable.class.isAssignableFrom(clazz)) {
            throw new LimberRequestMappingException(clazz);
        }
        Class<? extends AbstractRenderable> renderableClass = castToRenderable(clazz);

        // Check for second group match, representing the page version.
        UUID versionId = null;
        String versionIdString = pathMatcher.group(2);
        if (versionIdString != null) {
            versionId = UUID.fromString(versionIdString);
        }

        // Check for third group match, representing the Ajax handler.
        UUID ajaxId = null;
        String ajaxIdString = pathMatcher.group(2);
        if (ajaxIdString != null) {
            ajaxId = UUID.fromString(ajaxIdString);
        }

        return new DefaultRequestMapping(
                httpServletRequestWrapper.getSessionId(),
                renderableClass,
                versionId,
                ajaxId);

    }

    @SuppressWarnings("unchecked")
    private Class<? extends AbstractRenderable> castToRenderable(@Nonnull Class<?> type) {
        return (Class<? extends AbstractRenderable>) type;
    }

    @Override
    public URI resolve(@Nonnull Class<? extends AbstractRenderable> renderableClass, UUID versionId, UUID ajaxId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/?limber=");
        stringBuilder.append(renderableClass.getName());
        if (versionId != null) {
            stringBuilder.append(':').append(versionId.toString());
            if (ajaxId != null) {
                stringBuilder.append(':').append(ajaxId.toString());
            }
        }
        try {
            return new URI(stringBuilder.toString());
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
