package no.kantega.lab.limber.dom.page;

import no.kantega.lab.limber.dom.ajax.AjaxEventTrigger;
import no.kantega.lab.limber.dom.element.ElementNode;

import java.util.UUID;

public interface IEventTriggerable {

    void trigger();

    AjaxEventTrigger getAjaxEventTrigger();

    boolean isAjaxEventTrigger();

    ElementNode<?> getEventTarget();

    UUID getUUID();
}
