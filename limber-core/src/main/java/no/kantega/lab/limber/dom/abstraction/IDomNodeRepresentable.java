package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;

import java.io.Serializable;

public interface IDomNodeRepresentable<N extends AbstractNode<? extends N>>
        extends IDomNodeBrowsable, IDomNodeMorphable<N>, IEffortlessCloneable, Serializable {
    /* summary interface */
}
