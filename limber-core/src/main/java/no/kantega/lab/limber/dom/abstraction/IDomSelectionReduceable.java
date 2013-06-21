package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.IElementNodeSelection;
import no.kantega.lab.limber.dom.selection.ILinkNodeSelection;
import no.kantega.lab.limber.dom.selection.INodeSelection;
import no.kantega.lab.limber.dom.selection.ITextNodeSelection;

import javax.annotation.Nonnull;

public interface IDomSelectionReduceable<N extends AbstractNode<? extends N>> extends Iterable<N> {

    @Nonnull
    N get(int index);

    @Nonnull
    INodeSelection<N> get(int from, int to);

    @Nonnull
    IElementNodeSelection<?> reduceByTag(@Nonnull CharSequence tagName);

    @Nonnull
    IElementNodeSelection<?> reduceByAttr(@Nonnull CharSequence key);

    @Nonnull
    IElementNodeSelection<?> reduceByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);

    @Nonnull
    INodeSelection<?> reduceByFilter(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>, N3 extends N2> INodeSelection<N2> reduceByFilter(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N3> filterBoundary);

    @Nonnull
    ITextNodeSelection reduceToText();

    @Nonnull
    IElementNodeSelection<?> reduceToElement();

    @Nonnull
    IElementNodeSelection<?> reduceToElement(CharSequence tagName);

    @Nonnull
    ILinkNodeSelection<?> reduceToLink();

    @Nonnull
    ILinkNodeSelection<?> reduceToLink(CharSequence tagName);
}
