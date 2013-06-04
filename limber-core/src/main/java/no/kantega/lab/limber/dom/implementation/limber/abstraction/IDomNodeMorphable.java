package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.element.NodeAttachment;

public interface IDomNodeMorphable<T extends IDomNodeMorphable<?, S>, S extends AbstractNode<S>> {

    T clear();

    T setRendered(boolean render);

    T remove();

    T addNodeAttachment(NodeAttachment<? extends S> nodeAttachment);

    T removeNodeAttachment(NodeAttachment<? extends S> nodeAttachment);
}
