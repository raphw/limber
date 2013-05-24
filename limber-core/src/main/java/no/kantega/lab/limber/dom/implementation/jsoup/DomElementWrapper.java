package no.kantega.lab.limber.dom.implementation.jsoup;

import no.kantega.lab.limber.dom.abstraction.element.IDomElement;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class DomElementWrapper extends Element {

    public static Element make(IDomElement element) {
        return new DomElementWrapper(element, Tag.valueOf(element.getTagName()), "http://localhost:8080");
    }

    private final IDomElement representative;

    private DomElementWrapper(IDomElement representative, Tag tag, String baseUri) {
        super(tag, baseUri);
        this.representative = representative;
    }
}
