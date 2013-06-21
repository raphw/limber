package no.kantega.lab.limber.servlet.request.standard;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.meta.PageVersioning;
import no.kantega.lab.limber.servlet.meta.VersioningType;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.UUID;

@PageVersioning(VersioningType.NONE)
public class RedirectionResponse implements IRenderable {

    private final Class<? extends IRenderable> renderableClass;
    private final UUID uuid;

    public RedirectionResponse(Class<? extends IRenderable> renderableClass, UUID uuid) {
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
