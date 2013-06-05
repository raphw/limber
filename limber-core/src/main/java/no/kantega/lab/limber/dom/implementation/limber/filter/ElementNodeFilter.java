package no.kantega.lab.limber.dom.implementation.limber.filter;

import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;

public class ElementNodeFilter implements INodeFilter<ElementNode> {
    @Override
    public boolean filter(ElementNode element) {
        return true;
    }
}
