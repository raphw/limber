package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomElementNodeRepresentable;
import no.kantega.lab.limber.dom.element.ElementNode;

public interface IElementNodeSelection<N extends ElementNode<? extends N>>
        extends INodeSelection<N>, IDomElementNodeRepresentable<N> {
    /* summary interface */

}
