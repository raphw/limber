package no.kantega.lab.limber.servlet;

import no.kantega.lab.limber.servlet.meta.*;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;

@PageRenderSynchronization(true)
@PageVersioning(VersioningPolicy.COMPLETE)
@PageRestitution(RestitutionPolicy.RECREATE_NEVER)
public abstract class AbstractRenderable {

    public abstract boolean render(@Nonnull OutputStream outputStream, @Nonnull IRenderContext renderContext) throws IOException;
}
