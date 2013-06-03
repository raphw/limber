package no.kantega.lab.limber.servlet;

import java.io.IOException;
import java.io.OutputStream;

public interface IRenderable {

    boolean render(OutputStream outputStream, IResponseContainer response) throws IOException;
}
