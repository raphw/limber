package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.IElementNodeSelection;
import no.kantega.lab.limber.dom.selection.INodeSelection;
import no.kantega.lab.limber.dom.selection.ITextNodeSelection;

import javax.annotation.Nonnull;

public interface IDomElementNodeBrowsable extends IDomNodeBrowsable {

    @Nonnull
    IElementNodeSelection<?> findByTag(@Nonnull CharSequence tagName);

    @Nonnull
    IElementNodeSelection<?> findByTag(@Nonnull CharSequence tagName, int maxDepth);

    ElementNode<?> findById(@Nonnull CharSequence id);

    ElementNode<?> findById(@Nonnull CharSequence id, int maxDepth);

    @Nonnull
    IElementNodeSelection<?> findByCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    IElementNodeSelection<?> findByCssClass(@Nonnull CharSequence cssClassName, int maxDepth);

    @Nonnull
    IElementNodeSelection<?> findByAttr(@Nonnull CharSequence key);

    @Nonnull
    IElementNodeSelection<?> findByAttr(@Nonnull CharSequence key, int maxDepth);

    @Nonnull
    IElementNodeSelection<?> findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);

    @Nonnull
    IElementNodeSelection<?> findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode, int maxDepth);

    @Nonnull
    INodeSelection<?> findByFilter(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter);

    @Nonnull
    INodeSelection<?> findByFilter(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter, int maxDepth);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>, N3 extends N2> INodeSelection<N2> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<N3> filterBoundary);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>, N3 extends N2> INodeSelection<N2> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<N3> filterBoundary, int maxDepth);

    @Nonnull
    INodeSelection<?> getChildren();

    @Nonnull
    INodeSelection<?> getChildren(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>, N3 extends N2> INodeSelection<N2> getChildren(@Nonnull INodeFilter<N2> nodeFilter, Class<N3> filterBoundary);

    @Nonnull
    ITextNodeSelection findTextNodes();

    @Nonnull
    ITextNodeSelection findTextNodes(int maxDepth);

    @Nonnull
    IElementNodeSelection<?> findElements();

    @Nonnull
    IElementNodeSelection<?> findElements(int maxDepth);
}
