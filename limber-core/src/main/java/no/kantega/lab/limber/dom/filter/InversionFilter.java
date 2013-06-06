package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;

import javax.annotation.Nonnull;

public class InversionFilter<N extends AbstractNode> implements INodeFilter<N> {

    private final INodeFilter<N> invertedFilter;

    public InversionFilter(@Nonnull INodeFilter<N> invertedFilter) {
        this.invertedFilter = invertedFilter;
    }

    @Override
    public boolean filter(@Nonnull N element) {
        return !invertedFilter.filter(element);
    }
}
