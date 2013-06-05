package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.filter.QueryMatchMode;

import java.util.List;
import java.util.Set;

public interface IDomElementQueryable extends IDomNodeQueryable {

    String getTagName();

    List<AbstractNode<?>> children();

    String getAttr(CharSequence key);

    Set<String> attrs();

    boolean isAttribute(CharSequence key);

    boolean isAttribute(CharSequence key, CharSequence value, QueryMatchMode queryMatchMode);

    boolean isCssClass(CharSequence cssClassName);

    boolean isCssStyle(CharSequence key);

    boolean isCssStyle(CharSequence key, CharSequence value, QueryMatchMode queryMatchMode);
}
