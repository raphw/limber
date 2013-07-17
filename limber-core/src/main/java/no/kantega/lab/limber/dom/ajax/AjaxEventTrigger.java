package no.kantega.lab.limber.dom.ajax;

public enum AjaxEventTrigger {
    CLICK("click"),
    DOUBLE_CLICL("dblclick"),
    BLUR("blur"),
    MOUSE_MOVE("mousemove"),
    MOUSE_DOWN("mousedown"),
    MOUSE_ENTER("mouseenter"),
    MOUSE_LEAVE("mouseleave");

    private final String eventDescription;

    private AjaxEventTrigger(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDescription() {
        return eventDescription;
    }
    }
