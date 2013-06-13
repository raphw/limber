package no.kantega.lab.limber.ajax.abstraction;

import no.kantega.lab.limber.dom.element.ElementNode;

public interface IAjaxCallback<N extends ElementNode> {

    void onEvent(AjaxEventTrigger ajaxEventTrigger, N eventTarget);
}
