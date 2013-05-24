package no.kantega.lab.limber.ajax.abstraction;

public interface IAjaxEvent {

    IAjaxCallback getCallback();

    AjaxEventTrigger getEvent();
}
