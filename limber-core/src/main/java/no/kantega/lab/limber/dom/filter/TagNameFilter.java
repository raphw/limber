package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.ElementNode;

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
