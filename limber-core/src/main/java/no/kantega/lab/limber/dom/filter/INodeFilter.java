package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;

public interface INodeFilter<T extends AbstractNode<T>> {

    boolean filter(T element);
}
