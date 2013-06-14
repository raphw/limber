package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

public class ElementNodeFilter implements INodeFilter<ElementNode<?>> {

    @Override
    public boolean filter(@Nonnull ElementNode<?> element) {
        return true;
    }
}
