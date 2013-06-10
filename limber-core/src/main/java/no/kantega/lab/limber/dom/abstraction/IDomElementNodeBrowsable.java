package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;

import javax.annotation.Nonnull;

public interface IDomElementNodeBrowsable<N extends AbstractNode, B extends IDomNodeBrowsable<B>>
        extends IDomNodeBrowsable<B>, Iterable<N> {

    @Nonnull
    ElementNodeSelection findByTag(@Nonnull CharSequence tagName);

    @Nonnull
    ElementNodeSelection findByTag(@Nonnull CharSequence tagName, int maxDepth);

    ElementNode findById(@Nonnull CharSequence id);

    ElementNode findById(@Nonnull CharSequence id, int maxDepth);

    @Nonnull
    ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName, int maxDepth);

    @Nonnull
    ElementNodeSelection findByAttr(@Nonnull CharSequence key);

    @Nonnull
    ElementNodeSelection findByAttr(@Nonnull CharSequence key, int maxDepth);

    @Nonnull
    ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);

    @Nonnull
    ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode, int maxDepth);

    @Nonnull
    NodeSelection<AbstractNode, ?> findByFilter(@Nonnull INodeFilter<AbstractNode> nodeFilter);

    @Nonnull
    NodeSelection<AbstractNode, ?> findByFilter(@Nonnull INodeFilter<AbstractNode> nodeFilter, int maxDepth);

    @Nonnull
    <N2 extends AbstractNode> NodeSelection<N2, ?> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<? extends N2> filterBoundary);

    @Nonnull
    <N2 extends AbstractNode> NodeSelection<N2, ?> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<? extends N2> filterBoundary, int maxDepth);

    @Nonnull
    NodeSelection<AbstractNode, ?> getChildren();

    @Nonnull
    NodeSelection<AbstractNode, ?> getChildren(@Nonnull INodeFilter<AbstractNode> nodeFilter);

    @Nonnull
    <N2 extends AbstractNode> NodeSelection<N2, ?> getChildren(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary);

    @Nonnull
    TextNodeSelection findTextNodes();

    @Nonnull
    TextNodeSelection findTextNodes(int maxDepth);

    @Nonnull
    ElementNodeSelection findElements();

    @Nonnull
    ElementNodeSelection findElements(int maxDepth);
}
