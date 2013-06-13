package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;

import javax.annotation.Nonnull;

public abstract class AbstractBinaryLogicalOperationFilter<N extends AbstractNode<? extends N>> implements INodeFilter<N> {

    private final INodeFilter<? super N> first;
    private final INodeFilter<? super N> second;

    public AbstractBinaryLogicalOperationFilter(@Nonnull INodeFilter<? super N> first, @Nonnull INodeFilter<? super N> second) {
        this.first = first;
        this.second = second;
    }

    @Nonnull
    protected INodeFilter<? super N> getFirst() {
        return first;
    }

    @Nonnull
    protected INodeFilter<? super N> getSecond() {
        return second;
    }
}

