package no.kantega.lab.limber.page;

import no.kantega.lab.limber.dom.abstraction.IDomSelectable;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.parser.DomTreeProvider;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.exception.NotYetImplementedException;
import no.kantega.lab.limber.page.context.DefaultHtmlRenderContext;
import no.kantega.lab.limber.page.context.DefaultHtmlRenderOptions;
import no.kantega.lab.limber.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.page.util.WebPageRenderSupport;
import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.meta.ResourceIdentification;
import no.kantega.lab.limber.servlet.meta.ResourceType;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@ResourceIdentification(ResourceType.HTML)
public class WebPage implements IRenderable, IDomSelectable<ElementNode<?>> {

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

        if (renderContext.getRequestMapping().isSubroutine()) {
            throw new NotYetImplementedException();
        }

        // Create HTML render context
        IHtmlRenderContext htmlRenderContext = new DefaultHtmlRenderContext(
                renderContext,
                new DefaultHtmlRenderOptions(),
                dom(),
                subroutineRegisterMap);

        WebPageRenderSupport.getInstance().renderPageResponse(
                outputStream,
                htmlRenderContext,
                dom(),
                subroutineRegisterMap);

        return true;
    }
}
