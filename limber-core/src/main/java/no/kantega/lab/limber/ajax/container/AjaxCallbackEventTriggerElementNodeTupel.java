package no.kantega.lab.limber.ajax.container;

import no.kantega.lab.limber.dom.element.ElementNode;

@SuppressWarnings("unchecked")
public class AjaxCallbackEventTriggerElementNodeTupel extends AjaxCallbackEventTriggerTupel {

    private final ElementNode element;

    @SuppressWarnings("unchecked")
    public AjaxCallbackEventTriggerElementNodeTupel(AjaxCallbackEventTriggerTupel ajaxCallbackEventTriggerTupel, ElementNode element) {
        super(ajaxCallbackEventTriggerTupel.getAjaxCallback(), ajaxCallbackEventTriggerTupel.getAjaxEventTrigger());
        this.element = element;
    }

    public ElementNode getElement() {
        return element;
    }
}
