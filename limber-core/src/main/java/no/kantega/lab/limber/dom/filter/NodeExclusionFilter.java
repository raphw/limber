package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;

import javax.annotation.Nonnull;

public class NodeExclusionFilter<N extends AbstractNode<?>> implements INodeFilter<N> {

    private final AbstractNode<?> filterNode;

    public NodeExclusionFilter(@Nonnull AbstractNode<?> filterNode) {
        this.filterNode = filterNode;
    }

    @Override
    public boolean filter(@Nonnull N element) {
        return element != filterNode;
    }
}
