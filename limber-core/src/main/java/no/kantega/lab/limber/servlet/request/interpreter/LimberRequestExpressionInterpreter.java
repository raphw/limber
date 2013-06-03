package no.kantega.lab.limber.servlet.request.interpreter;

import no.kantega.lab.limber.exception.LimberRequestMappingException;
import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.request.DefaultLimberRequest;
import no.kantega.lab.limber.servlet.request.ILimberRequest;
import no.kantega.lab.limber.servlet.request.RawRequest;

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
    public ILimberRequest interpret(RawRequest rawRequest) {

        // Check if this expression interpreter can be applied at all.
        String limberQuery = rawRequest.getQueryArgument("limber");
        if (!"/".equals(rawRequest.getRequestURI()) || limberQuery == null) {
            return null;
        }
        Matcher pathMatcher = pathPattern.matcher(limberQuery);
        if (!pathMatcher.find()) {
            return null;
        }

        // Find represented class and check if it interprets IRenderable .
        Class<?> clazz;
        try {
            clazz = Class.forName(pathMatcher.group(1));
        } catch (ClassNotFoundException e) {
            return null;
        }
        if (!IRenderable.class.isAssignableFrom(clazz)) {
            throw new LimberRequestMappingException(clazz);
        }
        Class<? extends IRenderable> renderableClass = castToRenderable(clazz);

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

        return new DefaultLimberRequest(
                rawRequest.getSessionId(),
                renderableClass,
                versionId,
                ajaxId);

    }

    @SuppressWarnings("unchecked")
    private Class<? extends IRenderable> castToRenderable(Class<?> type) {
        return (Class<? extends IRenderable>) type;
    }

    @Override
    public URI resolve(Class<? extends IRenderable> renderableClass, UUID versionId, UUID ajaxId) {
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
