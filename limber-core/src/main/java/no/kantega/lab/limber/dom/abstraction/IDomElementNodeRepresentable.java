package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.ElementNode;

public interface IDomElementNodeRepresentable<N extends ElementNode<? extends N>> extends IDomNodeRepresentable<N>,
        IDomElementNodeBrowsable, IDomElementNodeMorphable<N> {
    /* summary interface */
}
