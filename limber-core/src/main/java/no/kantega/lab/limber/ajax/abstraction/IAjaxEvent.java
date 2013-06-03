package no.kantega.lab.limber.ajax.abstraction;

import org.jsoup.nodes.Element;

public interface IAjaxEvent {

    IAjaxCallback getCallback();

    AjaxEventTrigger getEventTrigger();

    Element getEventTarget();

}
