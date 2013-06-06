package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;

import javax.annotation.Nonnull;

public interface INodeFilter<N extends AbstractNode<?>> {

    boolean filter(@Nonnull N element);
}
