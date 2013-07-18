package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;

import javax.annotation.Nonnull;

public class VisibilityFilter<N extends AbstractNode<? extends N>> implements INodeFilter<N> {

    private final boolean visibility;

    public VisibilityFilter(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public boolean filter(@Nonnull N element) {
        return element.isRendered() == visibility;
    }
}