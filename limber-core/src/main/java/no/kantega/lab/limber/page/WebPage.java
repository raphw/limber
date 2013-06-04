package no.kantega.lab.limber.page;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.DefaultAjaxEvent;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.ajax.abstraction.IAjaxEvent;
import no.kantega.lab.limber.dom.abstraction.selection.HeadResource;
import no.kantega.lab.limber.dom.abstraction.selection.IDomDocumentSelection;
import no.kantega.lab.limber.dom.implementation.jsoup.DomDocumentSelection;
import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;
import no.kantega.lab.limber.dom.implementation.limber.parser.HtmlSAXParser;
import no.kantega.lab.limber.dom.implementation.limber.renderer.DomTreeRenderer;
import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.IResponseContainer;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebPage implements IRenderable {

    private final IDomDocumentSelection domSelection;

    private final Map<UUID, IAjaxEvent> ajaxEventRegister;

    // TODO: Remove this hack and replace by actual solution (annotation methods)
    private boolean renderedAjax = false;

    public WebPage() {
        domSelection = DomDocumentSelection.makeDomDocumentSelection(this);
        try {
            domSelection.addExternalResource(
                    HeadResource.JS,
                    new URI("//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // TODO: Enable resource
//        domSelection.addExternalResource(HeadResource.JS, getClass().getResource("/web/js/jquery-2.0.0.min.js"));
        ajaxEventRegister = new HashMap<UUID, IAjaxEvent>();
    }

    public final IDomDocumentSelection dom() {
        return domSelection;
    }

    //TODO: The following methods should at best reside outside this class to hold the API clean.
    // All of the following implementations are very preliminary and for working demonstration.

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

        mytry();

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

        InputStream inputStream = getDocumentResourceStream();
        String documentHtml = domSelection.getOutput();
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(documentHtml);
        writer.flush();
        inputStream.close();
        return true;
    }

    private void mytry() {
        System.out.println("-------");
        HtmlSAXParser saxParser = HtmlSAXParser.make();
        ElementNode elementNode = saxParser.translateToDomTree(getDocumentResourceStream());
        DomTreeRenderer domTreeRenderer = new DomTreeRenderer();
        domTreeRenderer.render(elementNode, System.out);
        System.out.println("-------");
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
        domSelection.addEmbededResource(HeadResource.JS, stringBuilder.toString());
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
