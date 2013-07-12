package no.kantega.lab.limber.kernel.response;


import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.meta.PageVersioning;
import no.kantega.lab.limber.kernel.meta.VersioningPolicy;
import no.kantega.lab.limber.kernel.request.IRenderContext;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.UUID;

@PageVersioning(VersioningPolicy.NONE)
public class RedirectionResponse extends AbstractRenderable {

    private final Class<? extends AbstractRenderable> renderableClass;
    private final UUID uuid;

    public RedirectionResponse(Class<? extends AbstractRenderable> renderableClass, UUID uuid) {
        this.renderableClass = renderableClass;
        this.uuid = uuid;
    }

    @Override
    public boolean render(@Nonnull OutputStream outputStream, @Nonnull IRenderContext renderContext) throws IOException {
        URI referralURI = renderContext.getLimberPageRegister().decodeLink(renderableClass, uuid);
        renderContext.getHttpServletResponseWrapper().putHeaderValue("Location", referralURI.toString());
        renderContext.getHttpServletResponseWrapper().setStatusCode(HttpServletResponse.SC_FOUND);
        return true;
    }
}
