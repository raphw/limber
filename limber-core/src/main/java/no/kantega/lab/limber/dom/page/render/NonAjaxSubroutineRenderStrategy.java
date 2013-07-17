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

public class NonAjaxSubroutineRenderStrategy extends AbstractSubroutineRenderStrategy {

    @Override
    public boolean renderSubroutineResponse(@Nonnull Writer pageWriter,
                                            @Nonnull IEventTriggerable eventTriggerable,
                                            @Nonnull Class<? extends WebPage> webPageClass,
                                            @Nonnull IHtmlRenderContext htmlRenderContext,
                                            @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                            @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {

        eventTriggerable.trigger();
        return new FullPageRenderStrategy().renderResponse(pageWriter, webPageClass, htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
    }
}
