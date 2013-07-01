package no.kantega.lab.limber.servlet;

import no.kantega.lab.limber.servlet.meta.PageVersioning;
import no.kantega.lab.limber.servlet.meta.VersioningPolicy;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;

@PageVersioning(VersioningPolicy.COMPLETE)
public abstract class AbstractRenderable {

    public abstract boolean render(@Nonnull OutputStream outputStream, @Nonnull IRenderContext renderContext) throws IOException;
}
