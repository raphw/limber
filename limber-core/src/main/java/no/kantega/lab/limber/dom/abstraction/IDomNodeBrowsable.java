package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.selection.NodeSelection;

import javax.annotation.Nonnull;

public interface IDomNodeBrowsable {

    IDomElementNodeRepresentable<? extends ElementNode<?>> getParent();

    IDomElementNodeRepresentable<? extends ElementNode<?>> getRoot();

    @Nonnull
    NodeSelection<?, ?> getSiblings();

    @Nonnull
    NodeSelection<?, ?> getSiblings(boolean includeMe);

    @Nonnull
    NodeSelection<?, ?> getSiblings(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter);

    @Nonnull
    NodeSelection<?, ?> getSiblings(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter, boolean includeMe);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N3> filterBoundary);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N3> filterBoundary, boolean includeMe);
}
