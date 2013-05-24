package no.kantega.lab.limber.dom.abstraction.element;

public class DefaultElement implements IDomElement {

    private final String tagName;

    public DefaultElement(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String getTagName() {
        return tagName;
    }
}
