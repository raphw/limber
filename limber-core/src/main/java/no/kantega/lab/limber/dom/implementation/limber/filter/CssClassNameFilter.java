package no.kantega.lab.limber.dom.implementation.limber.filter;

import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;

import java.util.Arrays;
import java.util.List;

public class CssClassNameFilter extends AttributeKeyExistenceFilter {

    private final String cssClassName;

    public CssClassNameFilter(CharSequence cssClassName) {
        super("class");
        this.cssClassName = cssClassName.toString();
    }

    @Override
    public boolean filter(ElementNode element) {
        if (!super.filter(element)) {
            return false;
        }
        String value = element.getAttr(getAttrKey());
        List<String> elementList = Arrays.asList(value.split("( )+"));
        return elementList.contains(cssClassName);
    }
}
