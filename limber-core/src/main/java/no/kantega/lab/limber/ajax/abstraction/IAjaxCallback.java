package no.kantega.lab.limber.ajax.abstraction;

import no.kantega.lab.limber.dom.element.ElementNode;

public interface IAjaxCallback {

    void onEvent(AjaxEventTrigger ajaxEventTrigger, ElementNode eventTarget);

}
