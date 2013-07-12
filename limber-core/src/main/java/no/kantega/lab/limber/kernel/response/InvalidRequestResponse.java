package no.kantega.lab.limber.kernel.response;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.meta.PageVersioning;
import no.kantega.lab.limber.kernel.meta.VersioningPolicy;
import no.kantega.lab.limber.kernel.request.IRenderContext;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@PageVersioning(VersioningPolicy.NONE)
public class InvalidRequestResponse extends AbstractRenderable {

    @Override
    public boolean render(@Nonnull OutputStream outputStream, @Nonnull IRenderContext renderContext) throws IOException {
        renderContext.getHttpServletResponseWrapper().setStatusCode(HttpServletResponse.SC_GONE);
        return true;
    }
}
