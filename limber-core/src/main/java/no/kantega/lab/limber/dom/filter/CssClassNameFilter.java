package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.ElementNode;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class CssClassNameFilter extends AttributeKeyExistenceFilter {

    private final String cssClassName;

    public CssClassNameFilter(CharSequence cssClassName) {
        super("class");
        this.cssClassName = cssClassName.toString();
    }

    @Override
    public boolean filter(@Nonnull ElementNode element) {
        if (!super.filter(element)) {
            return false;
        }
        String value = element.getAttr(getAttrKey());
        List<String> cssClasses = Arrays.asList(value.split("( )+"));
        for (String cssClass : cssClasses) {
            if (StringUtils.equalsIgnoreCase(cssClass, cssClassName)) {
                return true;
            }
        }
        return false;
    }
}
