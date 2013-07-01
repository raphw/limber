package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;

public interface IDomNodeCastable {

    <N extends AbstractNode<?>> N to(Class<N> clazz);
}
