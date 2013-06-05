package no.kantega.lab.limber.dom.implementation.limber.filter;

import no.kantega.lab.limber.dom.implementation.limber.element.TextNode;

public class TextNodeFilter implements INodeFilter<TextNode> {
    @Override
    public boolean filter(TextNode element) {
        return true;
    }
}
