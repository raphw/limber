package no.kantega.lab.limber.page.context;

import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import javax.annotation.Nonnull;

public interface IHtmlRenderContext extends IRenderContext {

    @Nonnull
    HtmlDocumentRootSelection getDocumentRootSelection();

    @Nonnull
    ISubroutineRegister getSubroutineRegister();

    @Nonnull
    IHtmlRenderOptions getHtmlRenderOptions();
}
