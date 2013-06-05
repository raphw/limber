package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.TextNode;

import javax.annotation.Nonnull;

public class TextNodeFilter implements INodeFilter<TextNode> {

    @Override
    public boolean filter(@Nonnull TextNode element) {
        return true;
    }
}
