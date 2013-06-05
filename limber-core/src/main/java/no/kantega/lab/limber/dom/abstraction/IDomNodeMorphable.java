package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.NodeAttachment;

public interface IDomNodeMorphable<T extends IDomNodeMorphable<?, S>, S extends AbstractNode<S>> {

    T clear();

    T setRendered(boolean render);

    T remove();

    T addNodeAttachment(NodeAttachment<? extends S> nodeAttachment);

    T removeNodeAttachment(NodeAttachment<? extends S> nodeAttachment);
}