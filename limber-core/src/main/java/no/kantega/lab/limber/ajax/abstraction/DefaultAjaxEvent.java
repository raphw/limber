package no.kantega.lab.limber.ajax.abstraction;

public class DefaultAjaxEvent implements IAjaxEvent {

    private final AjaxEventTrigger ajaxEventTrigger;
    private final IAjaxCallback ajaxCallback;

    public DefaultAjaxEvent(AjaxEventTrigger ajaxEventTrigger, IAjaxCallback ajaxCallback) {
        this.ajaxEventTrigger = ajaxEventTrigger;
        this.ajaxCallback = ajaxCallback;
    }

    @Override
    public IAjaxCallback getCallback() {
        return null;
    }

    @Override
    public AjaxEventTrigger getEvent() {
        return null;
    }

}
