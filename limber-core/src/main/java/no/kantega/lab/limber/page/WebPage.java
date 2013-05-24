package no.kantega.lab.limber.page;

import no.kantega.lab.limber.ajax.abstraction.IAjaxEvent;
import no.kantega.lab.limber.ajax.abstraction.IAjaxEventTarget;
import no.kantega.lab.limber.dom.abstraction.selection.HeadResource;
import no.kantega.lab.limber.dom.abstraction.selection.IDomDocumentSelection;
import no.kantega.lab.limber.dom.implementation.jsoup.DomDocumentSelection;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class WebPage {

    private final IDomDocumentSelection domSelection;

    private final Map<IAjaxEventTarget, Collection<IAjaxEvent>> ajaxEventRegister;

    public WebPage() {
        this.domSelection = DomDocumentSelection.makeDomDocumentSelection(this);
        // TODO: Remove external link and let users choose where jQuery should be hosted.
        try {
            domSelection.addExternalResource(
                    HeadResource.JS,
                    new URI("//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        domSelection.addExternalResource(HeadResource.JS, getClass().getResource("/web/js/jquery-2.0.0.min.js"));
        ajaxEventRegister = new HashMap<IAjaxEventTarget, Collection<IAjaxEvent>>();
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

    public final void writePageToStream(OutputStream outputStream) throws IOException {
        InputStream inputStream = getDocumentResourceStream();
        appendAjaxEvents();
        String documentHtml = domSelection.getOutput();
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(documentHtml);
        writer.flush();
        inputStream.close();
    }

    public final void registerAjaxEvent(IAjaxEventTarget ajaxEventTarget, IAjaxEvent ajaxEvent) {
        Collection<IAjaxEvent> eventSet = ajaxEventRegister.get(domSelection);
        if (eventSet == null) {
            eventSet = new HashSet<IAjaxEvent>();
            ajaxEventRegister.put(ajaxEventTarget, eventSet);
        }
        eventSet.add(ajaxEvent);
    }

    private final void appendAjaxEvents() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jQuery(document).ready(function(){");
        for (Map.Entry<IAjaxEventTarget, Collection<IAjaxEvent>> entry : ajaxEventRegister.entrySet()) {
            IAjaxEventTarget ajaxEventTarget = entry.getKey();
            for (IAjaxEvent ajaxEvent : entry.getValue()) {
                stringBuilder.append("jQuery('");
                stringBuilder.append(ajaxEventTarget.getIdentifier());
                stringBuilder.append("').bind('");
                stringBuilder.append("click"); // Prelim.
                stringBuilder.append("',function(){");
                stringBuilder.append("alert('hi');"); // Prelim.
                stringBuilder.append("})");
            }
        }
        stringBuilder.append("})");
        domSelection.addEmbededResource(HeadResource.JS, stringBuilder.toString());
    }

}
