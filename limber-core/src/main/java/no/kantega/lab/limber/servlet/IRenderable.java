package no.kantega.lab.limber.servlet;

import no.kantega.lab.limber.servlet.meta.PageVersioning;
import no.kantega.lab.limber.servlet.meta.VersioningType;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;

@PageVersioning(VersioningType.COMPLETE)
public interface IRenderable {

    boolean render(@Nonnull OutputStream outputStream, @Nonnull IRenderContext renderContext) throws IOException;
}
