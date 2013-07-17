package no.kantega.lab.limber.dom.page.context;

import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;
import no.kantega.lab.limber.kernel.request.IHttpServletRequestWrapper;
import no.kantega.lab.limber.kernel.request.IPageContext;
import no.kantega.lab.limber.kernel.request.IRenderContext;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.kernel.response.IHttpServletResponseWrapper;
import no.kantega.lab.limber.kernel.store.IStoreCollection;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

public class DefaultHtmlRenderContext implements IHtmlRenderContext {

    private final IRenderContext renderContext;
    private final ISubroutineRegister subroutineRegister;
    private final IHtmlRenderOptions htmlRenderOptions;
    private final HtmlDocumentRootSelection htmlDocumentRootSelection;

    public DefaultHtmlRenderContext(@Nonnull IRenderContext renderContext,
                                    @Nonnull IHtmlRenderOptions htmlRenderOptions,
                                    @Nonnull HtmlDocumentRootSelection htmlDocumentRootSelection,
                                    @Nonnull Map<UUID, IEventTriggerable> subroutineRegister) {
        this.renderContext = renderContext;
        this.subroutineRegister = new DefaultSubroutineRegister(subroutineRegister);
        this.htmlRenderOptions = htmlRenderOptions;
        this.htmlDocumentRootSelection = htmlDocumentRootSelection;
    }

    @Nonnull
    @Override
    public ISubroutineRegister getSubroutineRegister() {
        return subroutineRegister;
    }

    @Nonnull
    @Override
    public IHtmlRenderOptions getHtmlRenderOptions() {
        return htmlRenderOptions;
    }

    @Nonnull
    @Override
    public HtmlDocumentRootSelection getDocumentRootSelection() {
        return htmlDocumentRootSelection;
    }

    @Nonnull
    @Override
    public ILimberApplicationContext getLimberApplicationContext() {
        return renderContext.getLimberApplicationContext();
    }

    @Nonnull
    @Override
    public IRequestMapping getRequestMapping() {
        return renderContext.getRequestMapping();
    }

    @Nonnull
    @Override
    public IStoreCollection getStoreCollection() {
        return renderContext.getStoreCollection();
    }

    @Nonnull
    @Override
    public IHttpServletRequestWrapper getHttpServletRequestWrapper() {
        return renderContext.getHttpServletRequestWrapper();
    }

    @Nonnull
    @Override
    public IHttpServletResponseWrapper getHttpServletResponseWrapper() {
        return renderContext.getHttpServletResponseWrapper();
    }

    @Override
    public IPageContext getPageContext() {
        return renderContext.getPageContext();
    }
}
