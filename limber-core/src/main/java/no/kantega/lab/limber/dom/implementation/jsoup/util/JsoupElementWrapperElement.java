package no.kantega.lab.limber.dom.implementation.jsoup.util;

import no.kantega.lab.limber.dom.abstraction.element.IDomElement;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.UUID;

public class JsoupElementWrapperElement extends Element implements IDomElement {

    public static JsoupElementWrapperElement make(IDomElement domElement) {
        return new JsoupElementWrapperElement(domElement);
    }

    private final IDomElement wrappedElement;

    private JsoupElementWrapperElement(IDomElement wrappedElement) {
        super(Tag.valueOf(wrappedElement.getTagName()), "http://localhost:8080");
        this.wrappedElement = wrappedElement;
    }

    @Override
    public String getTagName() {
        return wrappedElement.getTagName();
    }

    @Override
    public String getBestUniqueIdentifier() {
        String id = attr("id");
        if(StringUtil.isBlank(id)) {
            attr("id", id = UUID.randomUUID().toString());
        }
        return "#" + id;
    }
}
