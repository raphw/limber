package no.kantega.lab.limber.ajax.abstraction;

import no.kantega.lab.limber.dom.abstraction.element.IDomElement;

public interface IAjaxCallback {

    void onEvent(AjaxEventTrigger ajaxEventTrigger, IDomElement target);

}
