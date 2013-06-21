package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomLinkNodeRepresentable;
import no.kantega.lab.limber.dom.element.LinkNode;

public interface ILinkNodeSelection<N extends LinkNode<? extends N>>
        extends IElementNodeSelection<N>, IDomLinkNodeRepresentable<N> {
    /* summary interface */
}
