package no.kantega.lab.limber.ajax.abstraction;

import org.jsoup.nodes.Element;

public class DefaultAjaxEvent implements IAjaxEvent {

    private final AjaxEventTrigger ajaxEventTrigger;
    private final IAjaxCallback ajaxCallback;
    private final Element ajaxEventTarget;

    public DefaultAjaxEvent(AjaxEventTrigger ajaxEventTrigger,
                            IAjaxCallback ajaxCallback,
                            Element ajaxEventTarget) {
        this.ajaxEventTrigger = ajaxEventTrigger;
        this.ajaxCallback = ajaxCallback;
        this.ajaxEventTarget = ajaxEventTarget;
    }

    @Override
    public IAjaxCallback getCallback() {
        return ajaxCallback;
    }

    @Override
    public AjaxEventTrigger getEventTrigger() {
        return ajaxEventTrigger;
    }

    @Override
    public Element getEventTarget() {
        return ajaxEventTarget;
    }
}
