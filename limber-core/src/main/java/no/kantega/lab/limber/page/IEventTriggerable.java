package no.kantega.lab.limber.page;

import no.kantega.lab.limber.dom.target.EventTrigger;

import java.util.UUID;

public interface IEventTriggerable {

    void trigger();

    EventTrigger getEventTrigger();

    boolean isAjaxResponse();

    UUID getUUID();
}
