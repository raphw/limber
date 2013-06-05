package no.kantega.lab.limber.dom.implementation.limber.filter;

import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;

public class TagNameFilter implements INodeFilter<ElementNode> {

    private final CharSequence tagName;

    public TagNameFilter(CharSequence tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean filter(ElementNode element) {
        return tagName.equals(element.getTagName());
    }
}
