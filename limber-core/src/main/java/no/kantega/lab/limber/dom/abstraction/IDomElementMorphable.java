package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IDomElementMorphable<M extends IDomElementMorphable> extends IDomNodeMorphable<M> {

    @Nonnull
    <N extends AbstractNode> Object addChild(int index, @Nonnull N node);

    @Nonnull
    M addChildAndStay(int index, @Nonnull AbstractNode<?> node);

    @Nonnull
    Object addChild(int index, @Nonnull CharSequence tagName);

    @Nonnull
    M addChildAndStay(int index, @Nonnull CharSequence tagName);

    @Nonnull
    Object addText(int index, @Nonnull CharSequence text);

    @Nonnull
    M addTextAndStay(int index, @Nonnull CharSequence text);

    @Nonnull
    Object addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    M addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N extends AbstractNode> Object appendChild(@Nonnull N node);

    @Nonnull
    M appendChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    Object appendChild(@Nonnull CharSequence tagName);

    @Nonnull
    M appendChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    Object appendText(@Nonnull CharSequence text);

    @Nonnull
    M appendTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    Object appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    M appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N extends AbstractNode> Object prependChild(@Nonnull N node);

    @Nonnull
    M prependChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    Object prependChild(@Nonnull CharSequence tagName);

    @Nonnull
    M prependChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    Object prependText(@Nonnull CharSequence text);

    @Nonnull
    M prependTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    Object prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    M prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    Object wrap(@Nonnull ElementNode elementNode);

    @Nonnull
    M wrapAndStay(@Nonnull ElementNode elementNode);

    @Nonnull
    Object wrap(@Nonnull CharSequence tagName);

    @Nonnull
    M wrapAndStay(@Nonnull CharSequence tagName);

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
