package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

public class AttributeKeyExistenceFilter implements INodeFilter<ElementNode<?>> {

    private final CharSequence attrKey;

    public AttributeKeyExistenceFilter(CharSequence attrKey) {
        this.attrKey = attrKey;
    }

    @Override
    public boolean filter(@Nonnull ElementNode<?> element) {
        return element.isAttribute(attrKey);
    }

    protected CharSequence getAttrKey() {
        return attrKey;
    }
}
