package no.kantega.lab.limber.dom.page.render;

import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.WebPage;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractSubroutineRenderStrategy extends AbstractWebPageRenderStrategy {

    @Override
    public boolean renderResponse(@Nonnull Writer pageWriter,
                                  @Nonnull Class<? extends WebPage> webPageClass,
                                  @Nonnull IHtmlRenderContext htmlRenderContext,
                                  @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                  @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {

        IEventTriggerable eventTriggerable = subroutineRegisterMap.get(htmlRenderContext.getRequestMapping().getSubroutineId());
        if (eventTriggerable == null) {
            return false;
        } else {
            return renderSubroutineResponse(pageWriter, eventTriggerable, webPageClass,
                    htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
        }
    }

    public abstract boolean renderSubroutineResponse(@Nonnull Writer pageWriter,
                                                     @Nonnull IEventTriggerable eventTriggerable,
                                                     @Nonnull Class<? extends WebPage> webPageClass,
                                                     @Nonnull IHtmlRenderContext htmlRenderContext,
                                                     @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                                     @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException;
}
