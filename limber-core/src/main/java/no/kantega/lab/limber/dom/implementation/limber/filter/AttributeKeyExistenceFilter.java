package no.kantega.lab.limber.dom.implementation.limber.filter;

import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;

public class AttributeKeyExistenceFilter implements INodeFilter<ElementNode> {

    private final CharSequence attrKey;

    public AttributeKeyExistenceFilter(CharSequence attrKey) {
        this.attrKey = attrKey;
    }

    @Override
    public boolean filter(ElementNode element) {
        return element.getAttr(attrKey) != null;
    }

    protected CharSequence getAttrKey() {
        return attrKey;
    }
}
