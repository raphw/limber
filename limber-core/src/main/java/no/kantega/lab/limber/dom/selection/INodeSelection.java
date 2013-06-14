package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomNodeRepresentable;
import no.kantega.lab.limber.dom.abstraction.IDomSelectionQueryable;
import no.kantega.lab.limber.dom.abstraction.IDomSelectionReduceable;
import no.kantega.lab.limber.dom.element.AbstractNode;

public interface INodeSelection<N extends AbstractNode<? extends N>>
        extends IDomNodeRepresentable<N>, IDomSelectionQueryable<N>, IDomSelectionReduceable<N>, Iterable<N> {
    /* summary interface */
}
