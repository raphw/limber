package no.kantega.lab.limber.ajax.abstraction;

import org.jsoup.nodes.Element;

public interface IAjaxCallback {

    void onEvent(AjaxEventTrigger ajaxEventTrigger, Element eventTarget);

}
