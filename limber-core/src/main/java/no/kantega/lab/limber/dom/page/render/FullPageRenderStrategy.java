package no.kantega.lab.limber.dom.page.render;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.WebPage;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;

public class FullPageRenderStrategy extends AbstractWebPageRenderStrategy {

    @Override
    public boolean renderResponse(
            @Nonnull Writer pageWriter,
            @Nonnull Class<? extends WebPage> webPageClass,
            @Nonnull IHtmlRenderContext htmlRenderContext,
            @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
            @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {

        subroutineRegisterMap.clear();
        ElementNode<?> magicJQueryNode = new MagicJQueryNode(subroutineRegisterMap.values());
        htmlDocumentSelection.getBodyNode().appendChild(magicJQueryNode);

        htmlDocumentSelection.getDoctypeDeclaration().render(pageWriter, htmlRenderContext);
        htmlDocumentSelection.getRootNode().render(pageWriter, htmlRenderContext);

        magicJQueryNode.remove();

        return true;
    }
}
