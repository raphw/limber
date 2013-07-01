package no.kantega.lab.limber.dom.page;

import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;

public interface IHtmlRenderable {

    boolean render(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext) throws IOException;
}
