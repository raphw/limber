package no.kantega.lab.limber.page;

import no.kantega.lab.limber.ajax.AjaxBoundEventTupel;
import no.kantega.lab.limber.ajax.jquery.JQueryRenderSupport;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.parser.DomTreeProvider;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.IResponseContainer;
import no.kantega.lab.limber.servlet.meta.IDomSelectable;
import no.kantega.lab.limber.servlet.meta.ResourceIdentification;
import no.kantega.lab.limber.servlet.meta.ResourceType;

import javax.annotation.Nonnull;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

@ResourceIdentification(ResourceType.HTML)
public class WebPage implements IRenderable, IDomSelectable<ElementNode<?>> {

    private final HtmlDocumentRootSelection htmlDocumentSelection;

    private Map<UUID, AjaxBoundEventTupel<?>> ajaxEventRegister;
    private ElementNode<?> limberScriptNode;

    public WebPage() {
        htmlDocumentSelection = DomTreeProvider.getInstance().provideDocumentSelection(getClass());
    }

    @Override
    public final HtmlDocumentRootSelection dom() {
        return htmlDocumentSelection;
    }

    @Override
    public final boolean render(@Nonnull OutputStream outputStream, @Nonnull IResponseContainer response) throws IOException {
        if (response.getRequest().isSubroutine()) {
            return renderAjaxResponse(outputStream, response);
        } else {
            return renderFullPage(outputStream, response);
        }
    }

    private boolean renderFullPage(@Nonnull OutputStream outputStream, @Nonnull IResponseContainer response) throws IOException {
        ajaxEventRegister = WebPageRenderSupport.getInstance().makeAjaxEventMap(htmlDocumentSelection.getRootNode());
        ElementNode scriptNode = getOrMakeLimberScriptNode();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JQueryRenderSupport.getInstance().makeEventJavascript(byteArrayOutputStream, response, ajaxEventRegister);
        scriptNode.setContent(byteArrayOutputStream.toString());
        scriptNode.setRendered(!scriptNode.isEmpty());
        outputStream = new BufferedOutputStream(outputStream);
        htmlDocumentSelection.getDoctypeDeclaration().render(outputStream, response);
        htmlDocumentSelection.getRootNode().render(outputStream, response);
        outputStream.close();
        return true;
    }

    private ElementNode getOrMakeLimberScriptNode() {
        if (limberScriptNode == null || limberScriptNode.getRoot() == htmlDocumentSelection.getRootNode()) {
            limberScriptNode = htmlDocumentSelection.getHeadNode().appendChild("script").putAttr("type", "text/javascript");
        }
        return limberScriptNode;
    }

    @SuppressWarnings("unchecked")
    private boolean renderAjaxResponse(@Nonnull OutputStream outputStream, @Nonnull IResponseContainer response) throws IOException {
        UUID ajaxId = response.getRequest().getSubroutineId();
        AjaxBoundEventTupel<?> ajaxEvent = ajaxEventRegister.get(ajaxId);
        if (ajaxEvent == null) {
            return false;
        }
        ajaxEvent.triggerEvent();
        // TODO: Revert!
//        JQueryRenderSupport.getInstance().makeUpdateResponse(outputStream, Arrays.asList(htmlDocumentSelection.findByTag("ul").get(0)), response);
        outputStream.close();
        return true;
    }
}
