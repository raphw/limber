package no.kantega.lab.limber.servlet;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;

public interface IRenderable {

    boolean render(@Nonnull OutputStream outputStream, @Nonnull IResponseContainer response) throws IOException;
}
