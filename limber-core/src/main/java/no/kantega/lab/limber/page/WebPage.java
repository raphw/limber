package no.kantega.lab.limber.page;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.DefaultAjaxEvent;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.ajax.abstraction.IAjaxEvent;
import no.kantega.lab.limber.dom.parser.HtmlSAXParser;
import no.kantega.lab.limber.dom.renderer.DomTreeRenderer;
import no.kantega.lab.limber.dom.selection.HtmlDocumentSelection;
import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.IResponseContainer;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebPage implements IRenderable {

    private final HtmlDocumentSelection htmlDocumentSelection;

    private final Map<UUID, IAjaxEvent> ajaxEventRegister;

    // TODO: Remove this hack and replace by actual solution (annotation methods)
    private boolean renderedAjax = false;

    public WebPage() {
        htmlDocumentSelection = new HtmlDocumentSelection(HtmlSAXParser.make().translateToDomTree(getDocumentResourceStream()));
        ajaxEventRegister = new HashMap<UUID, IAjaxEvent>();
    }

    public final HtmlDocumentSelection dom() {
        return htmlDocumentSelection;
    }

    public final InputStream getDocumentResourceStream() {
        String name = getClass().getSimpleName() + ".html";
        InputStream inputStream = getClass().getResourceAsStream(name);
        if (inputStream == null) {
            throw new RuntimeException("Cannot find resource.");
        }

        return inputStream;
    }

    @Override
    public final boolean render(OutputStream outputStream, IResponseContainer response) throws IOException {


        if (response.getRequest().isAjax()) {
            UUID ajaxId = response.getRequest().getAjaxId();
            IAjaxEvent ajaxEvent = ajaxEventRegister.get(ajaxId);
            if (ajaxEvent == null) {
                return false;
            }
            IAjaxCallback ajaxCallback = ajaxEvent.getCallback();
            ajaxCallback.onEvent(ajaxEvent.getEventTrigger(), ajaxEvent.getEventTarget());
            return true;
        }

        if (!renderedAjax) {
            appendAjaxEvents(response);
            renderedAjax = true;
        }

        DomTreeRenderer.getInstance().render(dom().getRootNode(), outputStream);
        return true;
    }

    public final void registerAjaxEvent(Element ajaxEventTarget,
                                        AjaxEventTrigger ajaxEventTrigger,
                                        IAjaxCallback ajaxCallback) {
        ajaxEventRegister.put(
                UUID.randomUUID(),
                new DefaultAjaxEvent(
                        ajaxEventTrigger,
                        ajaxCallback,
                        ajaxEventTarget));
    }

    private final void appendAjaxEvents(IResponseContainer response) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jQuery(document).ready(function(){");
        for (Map.Entry<UUID, IAjaxEvent> entry : ajaxEventRegister.entrySet()) {

            IAjaxEvent ajax = entry.getValue();
            UUID ajaxId = entry.getKey();

            stringBuilder.append("jQuery('");
            stringBuilder.append(makeUniqueIdentifier(ajax.getEventTarget()));
            stringBuilder.append("').bind('");
            stringBuilder.append("click"); // Prelim.
            stringBuilder.append("',function(){");
            stringBuilder.append("jQuery.ajax(");
            stringBuilder.append("'");
            stringBuilder.append(response.decodeLink(
                    response.getRequest().getRenderableClass(),
                    response.getRequest().getVersionId(),
                    ajaxId).toASCIIString());
            stringBuilder.append("'");

            stringBuilder.append(");");
            stringBuilder.append("})");
        }
        stringBuilder.append("})");
//        domSelection.addEmbededResource(HeadResource.JS, stringBuilder.toString());
    }

    private String makeUniqueIdentifier(Element element) {
        String id = element.attr("id");
        if (StringUtils.isEmpty(id)) {
            id = UUID.randomUUID().toString();
            element.attr("id", id);
        }
        return "#" + id;
    }
}
