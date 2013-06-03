package no.kantega.lab.limber.dom.implementation.limber.parser;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class LimberElement extends Element {

    public LimberElement(String tagName) {
        super(Tag.valueOf(tagName), "http://localhost:8080");
    }

}
