package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.TextNode;

public interface IDomTextNodeMorphable<T extends IDomTextNodeMorphable<T>> extends IDomNodeMorphable<T, TextNode> {

    T setContent(CharSequence content);
}
