package no.kantega.lab.limber.servlet.request.standard;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.IResponseContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class InvalidRequestResponse implements IRenderable {

    @Override
    public boolean render(OutputStream outputStream, IResponseContainer response) throws IOException {
        response.setStatusCode(HttpServletResponse.SC_GONE);
        return true;
    }
}
