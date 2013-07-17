package no.kantega.lab.limber.dom.page.render;

import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.WebPage;
import no.kantega.lab.limber.dom.page.context.DefaultHtmlRenderContext;
import no.kantega.lab.limber.dom.page.context.DefaultHtmlRenderOptions;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.kernel.request.IRenderContext;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractWebPageRenderStrategy {

    public static AbstractWebPageRenderStrategy make(
            @Nonnull IRenderContext renderContext,
            @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) {

        if (renderContext.getRequestMapping().isSubroutine()) {
            IEventTriggerable eventTriggerable = subroutineRegisterMap.get(renderContext.getRequestMapping().getSubroutineId());
            if (eventTriggerable != null && eventTriggerable.isAjaxEventTrigger()) {
                return new AjaxResponseRenderStrategy();
            } else {
                return new NonAjaxSubroutineRenderStrategy();
            }
        } else {
            return new FullPageRenderStrategy();
        }
    }

    public boolean render(@Nonnull OutputStream outputStream,
                          @Nonnull Class<? extends WebPage> webPageClass,
                          @Nonnull IRenderContext renderContext,
                          @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                          @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {

        Writer pageWriter = new OutputStreamWriter(new BufferedOutputStream(outputStream));
        try {
            return renderResponse(
                    pageWriter,
                    webPageClass,
                    new DefaultHtmlRenderContext(
                            renderContext,
                            new DefaultHtmlRenderOptions(),
                            htmlDocumentSelection,
                            subroutineRegisterMap),
                    htmlDocumentSelection,
                    subroutineRegisterMap);
        } finally {
            pageWriter.flush();
        }
    }

    public abstract boolean renderResponse(@Nonnull Writer pageWriter,
                                           @Nonnull Class<? extends WebPage> webPageClass,
                                           @Nonnull IHtmlRenderContext htmlRenderContext,
                                           @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                           @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException;
}
