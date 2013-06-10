package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IDomElementMorphable<M extends IDomElementMorphable<M>> extends IDomNodeMorphable<M> {

    @Nonnull
    M addChildAndStay(int index, @Nonnull AbstractNode<?> node);

    @Nonnull
    M addChildAndStay(int index, @Nonnull CharSequence tagName);

    @Nonnull
    M addTextAndStay(int index, @Nonnull CharSequence text);

    @Nonnull
    M addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    M appendChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    M appendChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    M appendTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    M appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    M prependChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    M prependChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    M prependTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    M prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    M setTagName(@Nonnull CharSequence tagName);

    @Nonnull
    M putAttr(@Nonnull CharSequence key, CharSequence value);

    @Nonnull
    M removeAttr(@Nonnull CharSequence key);

    @Nonnull
    M setId(@Nonnull CharSequence id);

    @Nonnull
    M setRandomId();

    @Nonnull
    M setRandomIdIfNone();

    @Nonnull
    M removeId();

    @Nonnull
    M addCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    M setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames);

    @Nonnull
    M removeCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    M addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue);

    @Nonnull
    M setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles);

    @Nonnull
    M removeCssStyle(@Nonnull CharSequence styleKey);
}
