package no.kantega.lab.limber.servlet.request.responses;

import com.google.common.io.ByteStreams;
import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.meta.PageVersioning;
import no.kantega.lab.limber.servlet.meta.VersioningPolicy;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;

@PageVersioning(VersioningPolicy.NONE)
public class LocalResourceResponse extends AbstractRenderable {

    @Override
    public boolean render(@Nonnull OutputStream outputStream, @Nonnull IRenderContext renderContext) throws IOException {

        String requestUri = renderContext.getHttpServletRequestWrapper().getRequestUri();
        if (renderContext.getLimberApplicationContext().isLocalResource(requestUri)) {
            ByteStreams.copy(
                    renderContext.getLimberApplicationContext().getLocalResourceAsStream(requestUri),
                    outputStream);
            return true;
        } else {
            return false;
        }
    }
}
