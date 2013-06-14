package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.selection.INodeSelection;

import javax.annotation.Nonnull;

public interface IDomNodeBrowsable {

    IDomElementNodeRepresentable<?> getParent();

    IDomElementNodeRepresentable<?> getRoot();

    @Nonnull
    INodeSelection<?> getSiblings();

    @Nonnull
    INodeSelection<?> getSiblings(boolean includeMe);

    @Nonnull
    INodeSelection<?> getSiblings(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter);

    @Nonnull
    INodeSelection<?> getSiblings(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter, boolean includeMe);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>, N3 extends N2> INodeSelection<N2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N3> filterBoundary);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>, N3 extends N2> INodeSelection<N2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N3> filterBoundary, boolean includeMe);
}
