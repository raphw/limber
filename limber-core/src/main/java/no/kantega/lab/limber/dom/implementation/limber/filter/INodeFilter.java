package no.kantega.lab.limber.dom.implementation.limber.filter;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;

public interface INodeFilter<T extends AbstractNode<T>> {

    boolean filter(T element);
}
