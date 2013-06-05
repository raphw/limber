package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.QueryMatchMode;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IDomElementQueryable extends IDomNodeQueryable {

    String getTagName();

    List<AbstractNode<?>> children();

    String getAttr(@Nonnull CharSequence key);

    Map<String, String> getAttrs();

    String getId();

    List<String> getCssClasses();

    Map<String, String> getCssStyles();

    boolean isTag(CharSequence tagName);

    boolean isAttribute(CharSequence key);

    boolean isAttribute(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);

    boolean isCssClass(CharSequence cssClassName);

    boolean isIdSet();

    boolean isCssStyle(CharSequence key);

    boolean isCssStyle(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);
}
