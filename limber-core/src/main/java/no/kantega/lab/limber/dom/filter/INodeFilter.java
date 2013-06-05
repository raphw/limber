package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;

import javax.annotation.Nonnull;

public interface INodeFilter<T extends AbstractNode<T>> {

    boolean filter(@Nonnull T element);
}
