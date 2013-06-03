package no.kantega.lab.limber.ajax.abstraction;

import no.kantega.lab.limber.dom.abstraction.element.IDomElement;

public interface IAjaxEvent {

    IAjaxCallback getCallback();

    AjaxEventTrigger getEventTrigger();

    IDomElement getEventTarget();

}
