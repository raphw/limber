package no.kantega.lab.limber.servlet.request.standard;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.IResponseContainer;
import no.kantega.lab.limber.servlet.request.ILimberRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.UUID;

public class RedirectionResponse implements IRenderable {

    private final ILimberRequest limberRequest;
    private final UUID uuid;

    public RedirectionResponse(ILimberRequest limberRequest, UUID uuid) {
        this.limberRequest = limberRequest;
        this.uuid = uuid;
    }

    @Override
    public boolean render(OutputStream outputStream, IResponseContainer responseContainer) throws IOException {
        URI referralURI = responseContainer.decodeLink(limberRequest.getRenderableClass(), uuid, null);
        responseContainer.addHeader("Location", referralURI.toString());
        responseContainer.setStatusCode(HttpServletResponse.SC_FOUND);
        return true;
    }
}
