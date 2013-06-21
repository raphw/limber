package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.LinkNode;

public interface IDomLinkNodeRepresentable<N extends LinkNode<? extends N>>
        extends IDomElementNodeRepresentable<N>, IDomLinkNodeMorphable<N> {
    /* summary interface */
}
