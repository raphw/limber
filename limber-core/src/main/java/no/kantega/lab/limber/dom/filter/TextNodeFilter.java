package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.TextNode;

public class TextNodeFilter implements INodeFilter<TextNode> {
    @Override
    public boolean filter(TextNode element) {
        return true;
    }
}
