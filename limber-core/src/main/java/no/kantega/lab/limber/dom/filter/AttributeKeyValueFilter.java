package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;

import javax.annotation.Nonnull;

public class AttributeKeyValueFilter extends AttributeKeyExistenceFilter {

    private final String attrValue;

    private final QueryMatchMode queryMatchMode;

    public AttributeKeyValueFilter(CharSequence attrKey, CharSequence attrValue, QueryMatchMode queryMatchMode) {
        super(attrKey);
        this.attrValue = attrValue.toString();
        this.queryMatchMode = queryMatchMode;
    }

    @Override
    public boolean filter(@Nonnull ElementNode element) {
        if (!super.filter(element)) {
            return false;
        }
        String actualValue = element.getAttr(getAttrKey());
        return queryMatchMode.compare(actualValue, attrValue);
    }
}
