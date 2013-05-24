package no.kantega.lab.limber.dom.implementation.jsoup;

import no.kantega.lab.limber.ajax.abstraction.IAjaxEventTarget;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

public class AjaxEventTarget implements IAjaxEventTarget {

    private final Element element;

    public AjaxEventTarget(Element element) {
        this.element = element;
    }

    @Override
    public String getIdentifier() {
        String id = element.attr("id");
        if (StringUtils.isBlank(id)) {
            id = String.format("id%d", this.hashCode());
            element.attr("id", id);
        }
        return String.format("#%s", id);
    }
}
