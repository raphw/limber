package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;

import javax.annotation.Nonnull;

public interface IDomElementBrowsable<N extends AbstractNode<?>> extends Iterable<N> {

    ElementNodeSelection findByTag(@Nonnull CharSequence tagName);

    ElementNodeSelection findByTag(@Nonnull CharSequence tagName, int maxDepth);

    ElementNode findById(@Nonnull CharSequence id);

    ElementNode findById(@Nonnull CharSequence id, int maxDepth);

    ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName);

    ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName, int maxDepth);

    ElementNodeSelection findByAttr(@Nonnull CharSequence key);

    ElementNodeSelection findByAttr(@Nonnull CharSequence key, int maxDepth);

    ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);

    ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode, int maxDepth);

    <N2 extends AbstractNode<N2>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> findByFilter(@Nonnull INodeFilter<N2> nodeFilter);

    <N2 extends AbstractNode<N2>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, int maxDepth);

    TextNodeSelection findTextNodes();

    TextNodeSelection findTextNodes(int maxDepth);

    ElementNodeSelection findElements();

    ElementNodeSelection findElements(int maxDepth);
}
