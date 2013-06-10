package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.selection.NodeSelection;

import javax.annotation.Nonnull;

public interface IDomNodeBrowsable<B extends IDomNodeBrowsable<B>> {

    B getParent();

    B getRoot();

    @Nonnull
    NodeSelection<AbstractNode, ?> getSiblings();

    @Nonnull
    NodeSelection<AbstractNode, ?> getSiblings(boolean includeMe);

    @Nonnull
    NodeSelection<AbstractNode, ?> getSiblings(@Nonnull INodeFilter<AbstractNode> nodeFilter);

    @Nonnull
    NodeSelection<AbstractNode, ?> getSiblings(@Nonnull INodeFilter<AbstractNode> nodeFilter, boolean includeMe);

    @Nonnull
    <N2 extends AbstractNode> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary);

    @Nonnull
    <N2 extends AbstractNode> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary, boolean includeMe);
}
