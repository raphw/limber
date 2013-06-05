package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;

import javax.annotation.Nonnull;

public interface IDomElementFilterable<N extends AbstractNode<N>> extends Iterable<N> {

    N get(int index);

    NodeSelection<N, ?> get(int from, int to);

    ElementNodeSelection reduceByTag(@Nonnull CharSequence tagName);

    ElementNodeSelection reduceByAttr(@Nonnull CharSequence key);

    ElementNodeSelection reduceByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);

    <N2 extends AbstractNode<N2>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> reduceByFilter(@Nonnull INodeFilter<N2> nodeFilter);

    TextNodeSelection reduceToText();

    ElementNodeSelection reduceToElement();
}
