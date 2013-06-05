package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IDomElementMorphable<T extends IDomElementMorphable<T>> extends IDomNodeMorphable<T, ElementNode> {

    T setTagName(@Nonnull CharSequence tagName);

    T addChild(int index, @Nonnull AbstractNode<?> node);

    T addChild(int index, @Nonnull CharSequence tagName);

    T addText(int index, @Nonnull CharSequence text);

    T addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    T appendChild(@Nonnull AbstractNode<?> node);

    T appendChild(@Nonnull CharSequence tagName);

    T appendText(@Nonnull CharSequence text);

    T appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    T prependChild(@Nonnull AbstractNode<?> node);

    T prependChild(@Nonnull CharSequence tagName);

    T prependText(@Nonnull CharSequence text);

    T prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    T putAttr(@Nonnull CharSequence key, CharSequence value);

    T removeAttr(@Nonnull CharSequence key);

    T setId(@Nonnull CharSequence id);

    T setRandomId();

    T setRandomIdIfNone();

    T removeId();

    T addCssClass(@Nonnull CharSequence cssClassName);

    T setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames);

    T removeCssClass(@Nonnull CharSequence cssClassName);

    T addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue);

    T setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles);

    T removeCssStyle(@Nonnull CharSequence styleKey);
}
