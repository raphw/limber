package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;

import javax.annotation.Nonnull;

public interface IDomSelectionReduceable<N extends AbstractNode> extends Iterable<N> {

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
    NodeSelection<AbstractNode, ?> reduceByFilter(@Nonnull INodeFilter<AbstractNode> nodeFilter);

    @Nonnull
    <N2 extends AbstractNode> NodeSelection<N2, ?> reduceByFilter(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary);

    @Nonnull
    TextNodeSelection reduceToText();

    @Nonnull
    ElementNodeSelection reduceToElement();
}