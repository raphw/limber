package no.kantega.lab.limber.dom.page.render;

import no.kantega.lab.limber.dom.ajax.XhrResponseRenderSupport;
import no.kantega.lab.limber.dom.comparison.CompareBy;
import no.kantega.lab.limber.dom.comparison.IDomComparisonStrategy;
import no.kantega.lab.limber.dom.comparison.IReplacementStep;
import no.kantega.lab.limber.dom.comparison.TreeComparisonSupport;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.WebPage;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AjaxResponseRenderStrategy extends AbstractSubroutineRenderStrategy {

    @Override
    public boolean renderSubroutineResponse(@Nonnull Writer pageWriter,
                                            @Nonnull IEventTriggerable eventTriggerable,
                                            @Nonnull Class<? extends WebPage> webPageClass,
                                            @Nonnull IHtmlRenderContext htmlRenderContext,
                                            @Nonnull HtmlDocumentRootSelection htmlDocumentSelection,
                                            @Nonnull Map<UUID, IEventTriggerable> subroutineRegisterMap) throws IOException {

        ElementNode<?> rootNodeClone = htmlDocumentSelection.getRootNode().clone(),
                rootNodeOriginal = htmlDocumentSelection.getRootNode();

        eventTriggerable.trigger();

        List<IReplacementStep> replacementSteps = TreeComparisonSupport.getInstance().compare(
                findDomComparisonStrategy(webPageClass), rootNodeClone, rootNodeOriginal);

        XhrResponseRenderSupport.getInstance().renderReplacementSteps(pageWriter, htmlRenderContext, replacementSteps);

        return true;
    }

    private Class<? extends IDomComparisonStrategy> findDomComparisonStrategy(Class<? extends WebPage> webPageClass) {
        return webPageClass.getAnnotation(CompareBy.class).value();
    }
}
