package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;

import javax.annotation.Nonnull;

public interface IDomElementFilterable<N extends AbstractNode<N>> extends Iterable<N> {

    @Nonnull
    N get(int index);

    @Nonnull
    NodeSelection<N, ?> get(int from, int to);

    @Nonnull
    ElementNodeSelection reduceByTag(@Nonnull CharSequence tagName);

    @Nonnull
    ElementNodeSelection reduceByAttr(@Nonnull CharSequence key);

    @Nonnull
    ElementNodeSelection reduceByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);

    @Nonnull
    <N2 extends AbstractNode, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> reduceByFilter(@Nonnull INodeFilter<N2> nodeFilter);

    @Nonnull
    TextNodeSelection reduceToText();

    @Nonnull
    ElementNodeSelection reduceToElement();
}
