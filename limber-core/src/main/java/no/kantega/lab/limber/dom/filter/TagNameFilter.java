package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

public class TagNameFilter implements INodeFilter<ElementNode> {

    private final CharSequence tagName;

    public TagNameFilter(CharSequence tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean filter(@Nonnull ElementNode element) {
        return element.isTag(tagName);
    }
}
