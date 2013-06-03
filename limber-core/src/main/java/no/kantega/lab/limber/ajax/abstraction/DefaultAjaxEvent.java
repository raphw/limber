package no.kantega.lab.limber.ajax.abstraction;

import no.kantega.lab.limber.dom.abstraction.element.IDomElement;

public class DefaultAjaxEvent implements IAjaxEvent {

    private final AjaxEventTrigger ajaxEventTrigger;
    private final IAjaxCallback ajaxCallback;
    private final IDomElement ajaxEventTarget;

    public DefaultAjaxEvent(AjaxEventTrigger ajaxEventTrigger, IAjaxCallback ajaxCallback,
                            IDomElement ajaxEventTarget) {
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
    public IDomElement getEventTarget() {
        return ajaxEventTarget;
    }
}
