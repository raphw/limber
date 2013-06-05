package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.QueryMatchMode;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDomElementQueryable extends IDomNodeQueryable {

    String getTagName();

    List<AbstractNode<?>> children();

    String getAttr(CharSequence key);

    Set<String> attrs();

    String getId();

    List<String> getCssClasses();

    Map<String, String> getCssStyles();

    boolean isAttribute(CharSequence key);

    boolean isAttribute(CharSequence key, CharSequence value, QueryMatchMode queryMatchMode);

    boolean isCssClass(CharSequence cssClassName);

    boolean isIdSet();

    boolean isCssStyle(CharSequence key);

    boolean isCssStyle(CharSequence key, CharSequence value, QueryMatchMode queryMatchMode);
}
