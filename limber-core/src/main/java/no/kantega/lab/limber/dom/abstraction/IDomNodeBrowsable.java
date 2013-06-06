package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.selection.NodeSelection;

import javax.annotation.Nonnull;

public interface IDomNodeBrowsable<B extends IDomNodeBrowsable<B>> {

    B getParent();

    @Nonnull
    NodeSelection<AbstractNode<?>, ?> getSiblings();

    @Nonnull
    NodeSelection<AbstractNode<?>, ?> getSiblings(boolean includeMe);

    @Nonnull
    <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter);

    @Nonnull
    <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, boolean includeMe);
}
