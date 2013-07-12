package no.kantega.lab.limber.kernel.mapper;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.request.IHttpServletRequestWrapper;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.UUID;

public interface IRequestMapper {

    static final String UUID_V4_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    static final String JAVA_PACKAGE_NAME_REGEX = "[a-zA-Z_]{1}[a-zA-Z0-9_]*(?:\\.[a-zA-Z_]{1}[a-zA-Z0-9_]*)*";

    IRequestMapping map(@Nonnull IHttpServletRequestWrapper rawRequest);

    URI resolve(@Nonnull Class<? extends AbstractRenderable> renderableClass, UUID versionId, UUID ajaxId);
}
