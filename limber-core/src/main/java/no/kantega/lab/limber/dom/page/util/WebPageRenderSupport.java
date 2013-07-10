package no.kantega.lab.limber.dom.page.util;

import no.kantega.lab.limber.dom.ajax.AjaxResponseRenderSupport;
import no.kantega.lab.limber.dom.comparison.CompareBy;
import no.kantega.lab.limber.dom.comparison.IDomComparisonStrategy;
import no.kantega.lab.limber.dom.comparison.IReplacementStep;
import no.kantega.lab.limber.dom.comparison.TreeComparisonSupport;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.WebPage;
import no.kantega.lab.limber.dom.page.context.DefaultHtmlRenderContext;
import no.kantega.lab.limber.dom.page.context.DefaultHtmlRenderOptions;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

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

    public boolean renderResponse(@Nonnull OutputStream outputStream,
                                  @Nonnull Class<? extends WebPage> webPageClass,
                                  @Nonnull IRenderContext renderContext,
                                  @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                  @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {

        Writer pageWriter = new OutputStreamWriter(new BufferedOutputStream(outputStream));
        IHtmlRenderContext htmlRenderContext = makeHtmlRenderContext(renderContext, htmlDocumentSelection, subroutineRegisterMap);

        try {
            if (renderContext.getRequestMapping().isSubroutine()) {
                return renderSubroutineResponse(pageWriter, webPageClass,
                        subroutineRegisterMap.get(renderContext.getRequestMapping().getSubroutineId()),
                        htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
            } else {
                return renderPageResponse(pageWriter, htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
            }
        } finally {
            pageWriter.flush();
        }
    }

    public boolean renderPageResponse(@Nonnull Writer pageWriter,
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

    public boolean renderSubroutineResponse(@Nonnull Writer pageWriter,
                                            @Nonnull Class<? extends WebPage> webPageClass,
                                            IEventTriggerable eventTriggerable,
                                            @Nonnull IHtmlRenderContext htmlRenderContext,
                                            @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                            @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {
        if (eventTriggerable == null) {
            return false;
        } else if (eventTriggerable.isAjaxEventTrigger()) {
            return renderAjaxSubroutineResponse(pageWriter, eventTriggerable,
                    findDomComparisonStrategy(webPageClass),
                    htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
        } else {
            return renderNonAjaxSubroutineResponse(pageWriter, eventTriggerable,
                    htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
        }
    }

    private boolean renderNonAjaxSubroutineResponse(@Nonnull Writer pageWriter,
                                                    @Nonnull IEventTriggerable eventTriggerable,
                                                    @Nonnull IHtmlRenderContext htmlRenderContext,
                                                    @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                                    @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {
        eventTriggerable.trigger();
        return renderPageResponse(pageWriter, htmlRenderContext, htmlDocumentSelection, subroutineRegisterMap);
    }

    private boolean renderAjaxSubroutineResponse(@Nonnull Writer pageWriter,
                                                 @Nonnull IEventTriggerable eventTriggerable,
                                                 @Nonnull Class<? extends IDomComparisonStrategy> comparisonStrategyClass,
                                                 @Nonnull IHtmlRenderContext htmlRenderContext,
                                                 @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                                 @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {

        ElementNode<?> rootNodeClone = htmlDocumentSelection.getRootNode().clone(),
                rootNodeOriginal = htmlDocumentSelection.getRootNode();

        eventTriggerable.trigger();

        List<IReplacementStep> replacementSteps = TreeComparisonSupport.getInstance().compare(
                comparisonStrategyClass, rootNodeClone, rootNodeOriginal);

        AjaxResponseRenderSupport.getInstance().renderReplacementSteps(pageWriter, htmlRenderContext, replacementSteps);

        return true;
    }

    public Class<? extends IDomComparisonStrategy> findDomComparisonStrategy(Class<? extends WebPage> webPageClass) {
        return webPageClass.getAnnotation(CompareBy.class).value();
    }

    public IHtmlRenderContext makeHtmlRenderContext(@Nonnull IRenderContext renderContext,
                                                    @Nonnull HtmlDocumentRootSelection htmlDocumentRootSelection,
                                                    @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) {
        return new DefaultHtmlRenderContext(renderContext, new DefaultHtmlRenderOptions(), htmlDocumentRootSelection, subroutineRegisterMap);
    }

}
