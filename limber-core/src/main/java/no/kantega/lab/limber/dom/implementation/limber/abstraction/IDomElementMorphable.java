package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;

public interface IDomElementMorphable<T extends IDomElementMorphable<T>> extends IDomNodeMorphable<T, ElementNode> {

    T setTagName(CharSequence tagName);

    T addChild(int index, AbstractNode<?> node);

    T appendChild(AbstractNode<?> node);

    T prependChild(AbstractNode<?> node);

    T putAttr(CharSequence key, CharSequence value);

    T removeAttr(CharSequence key);
}
