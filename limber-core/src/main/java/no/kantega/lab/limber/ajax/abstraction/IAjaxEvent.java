package no.kantega.lab.limber.ajax.abstraction;

import no.kantega.lab.limber.dom.element.ElementNode;

public interface IAjaxEvent {

    IAjaxCallback getCallback();

    AjaxEventTrigger getEventTrigger();

    ElementNode getEventTarget();

}
