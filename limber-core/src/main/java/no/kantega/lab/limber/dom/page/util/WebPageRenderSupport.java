package no.kantega.lab.limber.dom.page.util;

import no.kantega.lab.limber.dom.ajax.AjaxResponseRenderSupport;
import no.kantega.lab.limber.dom.comparison.CompareBy;
import no.kantega.lab.limber.dom.comparison.IComparisonStrategy;
import no.kantega.lab.limber.dom.comparison.IReplacementStep;
import no.kantega.lab.limber.dom.comparison.TreeComparisonSupport;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.WebPage;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.List;
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

    public boolean renderSubroutineResponse(@Nonnull OutputStream outputStream,
                                            @Nonnull Class<? extends WebPage> webPageClass,
                                            @Nonnull IEventTriggerable eventTriggerable,
                                            @Nonnull IHtmlRenderContext htmlRenderContext,
                                            @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                            @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {
        if (eventTriggerable.isAjaxEventTrigger()) {
            return renderAjaxSubroutineResponse(outputStream, eventTriggerable,
                    findComparisonStrategy(webPageClass),
                    htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
        } else {
            return renderNonAjaxSubroutineResponse(outputStream, eventTriggerable,
                    htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
        }


    }

    private boolean renderNonAjaxSubroutineResponse(@Nonnull OutputStream outputStream,
                                                    @Nonnull IEventTriggerable eventTriggerable,
                                                    @Nonnull IHtmlRenderContext htmlRenderContext,
                                                    @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                                    @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {
        eventTriggerable.trigger();
        return renderPageResponse(outputStream, htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
    }

    private boolean renderAjaxSubroutineResponse(@Nonnull OutputStream outputStream,
                                                 @Nonnull IEventTriggerable eventTriggerable,
                                                 @Nonnull Class<? extends IComparisonStrategy> comparisonStrategyClass,
                                                 @Nonnull IHtmlRenderContext htmlRenderContext,
                                                 @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                                 @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {

        Writer pageWriter = new OutputStreamWriter(new BufferedOutputStream(outputStream));

        ElementNode<?> rootNodeClone = htmlDocumentSelection.getRootNode().clone(),
                rootNodeOriginal = htmlDocumentSelection.getRootNode();

        eventTriggerable.trigger();

        List<IReplacementStep> replacementSteps = TreeComparisonSupport.getInstance().compare(
                comparisonStrategyClass, rootNodeClone, rootNodeOriginal);

        AjaxResponseRenderSupport.getInstance().renderReplacementSteps(pageWriter, htmlRenderContext, replacementSteps);

        pageWriter.close();

        return true;
    }

    private Class<? extends IComparisonStrategy> findComparisonStrategy(Class<? extends WebPage> webPageClass) {
        return webPageClass.getAnnotation(CompareBy.class).value();
    }

}
