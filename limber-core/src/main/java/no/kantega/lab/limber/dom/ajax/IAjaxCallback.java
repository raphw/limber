package no.kantega.lab.limber.dom.ajax;

import no.kantega.lab.limber.dom.element.ElementNode;

public interface IAjaxCallback<N extends ElementNode<? extends N>> {

    void onEvent(AjaxEventTrigger ajaxEventTrigger, N eventTarget);
}
