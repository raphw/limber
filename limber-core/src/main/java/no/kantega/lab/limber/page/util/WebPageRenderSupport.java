package no.kantega.lab.limber.page.util;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.page.IEventTriggerable;
import no.kantega.lab.limber.page.context.IHtmlRenderContext;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Map;
import java.util.UUID;

public class WebPageRenderSupport {

    private static final WebPageRenderSupport INSTANCE = new WebPageRenderSupport();

    public static WebPageRenderSupport getInstance() {
        return INSTANCE;
    }

    private WebPageRenderSupport() {
        /* empty */
    }

    public boolean renderPageResponse(@Nonnull OutputStream outputStream,
                                      @Nonnull IHtmlRenderContext htmlRenderContext,
                                      @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                      @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {

        Writer pageWriter = new OutputStreamWriter(new BufferedOutputStream(outputStream));

        subroutineRegisterMap.clear();
        ElementNode<?> magicJQueryNode = new MagicJQueryNode(subroutineRegisterMap.values());
        htmlDocumentSelection.getBodyNode().appendChild(magicJQueryNode);

        htmlDocumentSelection.getDoctypeDeclaration().render(pageWriter, htmlRenderContext);
        htmlDocumentSelection.getRootNode().render(pageWriter, htmlRenderContext);

        magicJQueryNode.remove();
        pageWriter.close();

        return true;
    }

    public boolean renderAjaxResponse(@Nonnull OutputStream outputStream,
                                      @Nonnull IHtmlRenderContext htmlRenderContext,
                                      @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                      @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {


        return true;
    }

}
