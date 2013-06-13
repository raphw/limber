package no.kantega.lab.limber.ajax.container;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.element.ElementNode;

public class AjaxCallbackEventTriggerTupel<T extends ElementNode> {

    private final IAjaxCallback<? super T> ajaxCallback;

    private final AjaxEventTrigger ajaxEventTrigger;

    public AjaxCallbackEventTriggerTupel(IAjaxCallback<? super T> ajaxCallback, AjaxEventTrigger ajaxEventTrigger) {
        this.ajaxCallback = ajaxCallback;
        this.ajaxEventTrigger = ajaxEventTrigger;
    }

    public IAjaxCallback<?> getAjaxCallback() {
        return ajaxCallback;
    }

    public AjaxEventTrigger getAjaxEventTrigger() {
        return ajaxEventTrigger;
    }
}
