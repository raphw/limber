package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;

public interface IDomElementMorphable<T extends IDomElementMorphable<T>> extends IDomNodeMorphable<T, ElementNode> {

    T setTagName(CharSequence tagName);

    T addChild(int index, AbstractNode<?> node);

    T addChild(int index, CharSequence tagName);

    T addText(int index, CharSequence text);

    T appendChild(AbstractNode<?> node);

    T appendChild(CharSequence tagName);

    T appendText(CharSequence text);

    T prependChild(AbstractNode<?> node);

    T prependChild(CharSequence tagName);

    T prependText(CharSequence text);

    T putAttr(CharSequence key, CharSequence value);

    T removeAttr(CharSequence key);

    T setId(CharSequence id);

    T setRandomId();

    T removeId();

    T addCssClass(CharSequence cssClassName);

    T removeCssClass(CharSequence cssClassName);

    T addCssStyle(CharSequence styleKey, CharSequence styleValue);

    T removeCssStyle(CharSequence styleKey);
}
