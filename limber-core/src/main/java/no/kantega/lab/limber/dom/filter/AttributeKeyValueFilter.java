package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.ElementNode;

public class AttributeKeyValueFilter extends AttributeKeyExistenceFilter {

    private final String attrValue;

    private final QueryMatchMode filterMatchMode;

    public AttributeKeyValueFilter(CharSequence attrKey, CharSequence attrValue, QueryMatchMode filterMatchMode) {
        super(attrKey);
        this.attrValue = attrValue.toString();
        this.filterMatchMode = filterMatchMode;
    }

    @Override
    public boolean filter(ElementNode element) {
        if (!super.filter(element)) {
            return false;
        }
        String actualValue = element.getAttr(getAttrKey());
        switch (filterMatchMode) {
            case FULL_MATCH:
                return attrValue.equals(actualValue);
            case ENDS_WITH:
                return attrValue.endsWith(actualValue);
            case STARTS_WITH:
                return attrValue.startsWith(actualValue);
            case CONTAINS:
                return attrValue.contains(actualValue);
            default:
                return false;
        }
    }
}