package no.kantega.lab.limber.dom.page.context;

import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.servlet.context.*;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

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

    @Override
    public ILimberApplicationContext getLimberApplicationContext() {
        return renderContext.getLimberApplicationContext();
    }

    @Override
    public ILimberPageRegister getLimberPageRegister() {
        return renderContext.getLimberPageRegister();
    }

    @Override
    public IRequestMapping getRequestMapping() {
        return renderContext.getRequestMapping();
    }

    @Override
    public IHttpServletRequestWrapper getHttpServletRequestWrapper() {
        return renderContext.getHttpServletRequestWrapper();
    }

    @Override
    public IHttpServletResponseWrapper getHttpServletResponseWrapper() {
        return renderContext.getHttpServletResponseWrapper();
    }
}
