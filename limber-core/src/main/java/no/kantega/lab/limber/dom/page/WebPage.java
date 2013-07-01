package no.kantega.lab.limber.dom.page;

import no.kantega.lab.limber.dom.abstraction.IDomSelectable;
import no.kantega.lab.limber.dom.comparison.CompareBy;
import no.kantega.lab.limber.dom.comparison.RelapsingComparisonStrategy;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.page.context.DefaultHtmlRenderContext;
import no.kantega.lab.limber.dom.page.context.DefaultHtmlRenderOptions;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.dom.page.util.WebPageRenderSupport;
import no.kantega.lab.limber.dom.parser.DomTreeProvider;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.meta.ResourceIdentification;
import no.kantega.lab.limber.servlet.meta.ResourceType;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@CompareBy(RelapsingComparisonStrategy.class)
@ResourceIdentification(ResourceType.HTML)
public class WebPage extends AbstractRenderable implements IDomSelectable<ElementNode<?>> {

    private final HtmlDocumentRootSelection htmlDocumentRootSelection;
    private final Map<UUID, IEventTriggerable> subroutineRegisterMap;

    public WebPage() {
        htmlDocumentRootSelection = DomTreeProvider.getInstance().makeDocumentRootSelection(getClass());
        subroutineRegisterMap = new WeakHashMap<UUID, IEventTriggerable>();
    }

    @Nonnull
    @Override
    public final HtmlDocumentRootSelection dom() {
        return htmlDocumentRootSelection;
    }

    @Override
    public final boolean render(@Nonnull OutputStream outputStream, @Nonnull IRenderContext renderContext) throws IOException {

        // Assure that root not was not attached as a child to another node
        // (this would possibly cause endless loop during rendering)
        if (htmlDocumentRootSelection.getRootNode().getParent() != null) {
            throw new IllegalStateException();
        }

        // Take care of possible subroutine call.
        if (renderContext.getRequestMapping().isSubroutine()) {
            IEventTriggerable eventTriggerable = subroutineRegisterMap.get(renderContext.getRequestMapping().getSubroutineId());
            if (eventTriggerable == null) {
                // No subroutine found. Page cannot handle call.
                return false;
            } else {
                return WebPageRenderSupport.getInstance().renderSubroutineResponse(
                        outputStream, getClass(), eventTriggerable, makeHtmlRenderContext(renderContext), dom(), subroutineRegisterMap);
            }
        }

        // No subroutine. Send full page as a response.
        return WebPageRenderSupport.getInstance().renderPageResponse(
                outputStream, makeHtmlRenderContext(renderContext), dom(), subroutineRegisterMap);
    }

    private IHtmlRenderContext makeHtmlRenderContext(IRenderContext renderContext) {
        return new DefaultHtmlRenderContext(renderContext, new DefaultHtmlRenderOptions(), dom(), subroutineRegisterMap);
    }

}
