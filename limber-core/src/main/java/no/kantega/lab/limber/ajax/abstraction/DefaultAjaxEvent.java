package no.kantega.lab.limber.ajax.abstraction;

import no.kantega.lab.limber.dom.element.ElementNode;

public class DefaultAjaxEvent implements IAjaxEvent {

    private final AjaxEventTrigger ajaxEventTrigger;
    private final IAjaxCallback ajaxCallback;
    private final ElementNode ajaxEventTarget;

    public DefaultAjaxEvent(AjaxEventTrigger ajaxEventTrigger,
                            IAjaxCallback ajaxCallback,
                            ElementNode ajaxEventTarget) {
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
    public ElementNode getEventTarget() {
        return ajaxEventTarget;
    }
}
