package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;

import javax.annotation.Nonnull;

public class ExclusiveDisjunctionFilter<N extends AbstractNode<? extends N>> extends AbstractBinaryLogicalOperationFilter<N> {

    public ExclusiveDisjunctionFilter(@Nonnull INodeFilter<? super N> first, @Nonnull INodeFilter<? super N> second) {
        super(first, second);
    }

    @Override
    public boolean filter(@Nonnull N element) {
        return getFirst().filter(element) ^ getSecond().filter(element);
    }
}
