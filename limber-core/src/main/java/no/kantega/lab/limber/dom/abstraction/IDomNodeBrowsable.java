package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;

import javax.annotation.Nonnull;
import java.util.List;

public interface IDomNodeBrowsable {

    ElementNode getParent();

    @Nonnull
    List<AbstractNode<?>> getSiblings();

    @Nonnull
    List<AbstractNode<?>> getSiblings(boolean includeMe);

    @Nonnull
    <N2 extends AbstractNode<N2>> List<N2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter);

    @Nonnull
    <N2 extends AbstractNode<N2>> List<N2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, boolean includeMe);
}
