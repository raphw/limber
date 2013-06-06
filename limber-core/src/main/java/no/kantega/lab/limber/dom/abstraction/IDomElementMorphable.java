package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IDomElementMorphable<M extends IDomElementMorphable<M>> extends IDomNodeMorphable<ElementNode, M> {

    @Nonnull
    M setTagName(@Nonnull CharSequence tagName);

    @Nonnull
    M addChild(int index, @Nonnull AbstractNode<?> node);

    @Nonnull
    M addChild(int index, @Nonnull CharSequence tagName);

    @Nonnull
    M addText(int index, @Nonnull CharSequence text);

    @Nonnull
    M addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    M appendChild(@Nonnull AbstractNode<?> node);

    @Nonnull
    M appendChild(@Nonnull CharSequence tagName);

    @Nonnull
    M appendText(@Nonnull CharSequence text);

    @Nonnull
    M appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    M prependChild(@Nonnull AbstractNode<?> node);

    @Nonnull
    M prependChild(@Nonnull CharSequence tagName);

    @Nonnull
    M prependText(@Nonnull CharSequence text);

    @Nonnull
    M prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

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
