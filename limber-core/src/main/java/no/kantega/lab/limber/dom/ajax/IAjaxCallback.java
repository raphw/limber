package no.kantega.lab.limber.dom.ajax;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.target.EventTrigger;

public interface IAjaxCallback<N extends ElementNode<? extends N>> {

    void onEvent(EventTrigger ajaxEventTrigger, N eventTarget);
}
