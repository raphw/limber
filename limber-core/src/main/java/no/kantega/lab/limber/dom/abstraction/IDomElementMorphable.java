package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IDomElementMorphable<M extends IDomElementMorphable<M>> extends IDomNodeMorphable<ElementNode, M> {

    M setTagName(@Nonnull CharSequence tagName);

    M addChild(int index, @Nonnull AbstractNode<?> node);

    M addChild(int index, @Nonnull CharSequence tagName);

    M addText(int index, @Nonnull CharSequence text);

    M addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    M appendChild(@Nonnull AbstractNode<?> node);

    M appendChild(@Nonnull CharSequence tagName);

    M appendText(@Nonnull CharSequence text);

    M appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    M prependChild(@Nonnull AbstractNode<?> node);

    M prependChild(@Nonnull CharSequence tagName);

    M prependText(@Nonnull CharSequence text);

    M prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    M putAttr(@Nonnull CharSequence key, CharSequence value);

    M removeAttr(@Nonnull CharSequence key);

    M setId(@Nonnull CharSequence id);

    M setRandomId();

    M setRandomIdIfNone();

    M removeId();

    M addCssClass(@Nonnull CharSequence cssClassName);

    M setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames);

    M removeCssClass(@Nonnull CharSequence cssClassName);

    M addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue);

    M setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles);

    M removeCssStyle(@Nonnull CharSequence styleKey);
}
