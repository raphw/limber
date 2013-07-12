package no.kantega.lab.limber.kernel.response;

import com.google.common.io.ByteStreams;
import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.meta.PageVersioning;
import no.kantega.lab.limber.kernel.meta.VersioningPolicy;
import no.kantega.lab.limber.kernel.request.IRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;

@PageVersioning(VersioningPolicy.NONE)
public class LocalResourceResponse extends AbstractRenderable {

    @Override
    public boolean render(@Nonnull OutputStream outputStream, @Nonnull IRenderContext renderContext) throws IOException {

        String requestUri = renderContext.getHttpServletRequestWrapper().getRequestUri();
        if (renderContext.getLimberApplicationContext().isLocalReadableFile(requestUri)) {
            ByteStreams.copy(
                    renderContext.getLimberApplicationContext().getLocalResourceAsStream(requestUri),
                    outputStream);
            return true;
        } else {
            return false;
        }
    }
}
