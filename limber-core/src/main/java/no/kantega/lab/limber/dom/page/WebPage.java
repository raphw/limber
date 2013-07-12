package no.kantega.lab.limber.dom.page;

import no.kantega.lab.limber.dom.abstraction.IDomSelectable;
import no.kantega.lab.limber.dom.comparison.CompareBy;
import no.kantega.lab.limber.dom.comparison.RelapsingComparisonStrategy;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.page.util.WebPageRenderSupport;
import no.kantega.lab.limber.dom.parser.DomTreeProvider;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.meta.ResourceIdentification;
import no.kantega.lab.limber.kernel.meta.ResourceType;
import no.kantega.lab.limber.kernel.request.IRenderContext;

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

        return WebPageRenderSupport.getInstance().renderResponse(
                outputStream, getClass(), renderContext, htmlDocumentRootSelection, subroutineRegisterMap);
    }

}
