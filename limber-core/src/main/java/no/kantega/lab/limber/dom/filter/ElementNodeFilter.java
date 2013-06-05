package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.ElementNode;

public class ElementNodeFilter implements INodeFilter<ElementNode> {
    @Override
    public boolean filter(ElementNode element) {
        return true;
    }
}
