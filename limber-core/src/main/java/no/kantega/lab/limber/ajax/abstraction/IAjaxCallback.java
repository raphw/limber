package no.kantega.lab.limber.ajax.abstraction;

import no.kantega.lab.limber.dom.abstraction.element.IDomElement;

public interface IAjaxCallback {

    void onEvent(IDomElement element, IAjaxEvent ajaxEvent);

}
