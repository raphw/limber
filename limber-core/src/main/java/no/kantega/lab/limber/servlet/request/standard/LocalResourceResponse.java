package no.kantega.lab.limber.servlet.request.standard;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.meta.PageVersioning;
import no.kantega.lab.limber.servlet.meta.VersioningType;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;

@PageVersioning(VersioningType.NONE)

public class LocalResourceResponse implements IRenderable {

    @Override
    public boolean render(@Nonnull OutputStream outputStream, @Nonnull IRenderContext renderContext) throws IOException {
        return true;
    }
}
