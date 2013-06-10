package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IDomElementQueryable extends IDomNodeQueryable {

    @Nonnull
    String getTagName();

    String getAttr(@Nonnull CharSequence key);

    @Nonnull
    Map<String, String> getAttrs();

    String getId();

    @Nonnull
    List<String> getCssClasses();

    @Nonnull
    Map<String, String> getCssStyles();

    boolean isTag(CharSequence tagName);

    boolean isAttribute(CharSequence key);

    boolean isAttribute(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);

    boolean isCssClass(CharSequence cssClassName);

    boolean isIdSet();

    boolean isRoot();

    int size();

    boolean isCssStyle(CharSequence key);

    boolean isCssStyle(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);
}
