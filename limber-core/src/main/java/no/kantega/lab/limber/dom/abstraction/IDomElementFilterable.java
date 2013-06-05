package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;

import javax.annotation.Nonnull;

public interface IDomElementFilterable<T extends AbstractNode<T>> extends Iterable<T> {

    T get(int index);

    NodeSelection<?, T> get(int from, int to);

    ElementNodeSelection reduceByTag(@Nonnull CharSequence tagName);

    ElementNodeSelection reduceByAttr(@Nonnull CharSequence key);

    ElementNodeSelection reduceByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode);

    <U extends AbstractNode<U>, V extends NodeSelection<V, U>> NodeSelection<V, U> reduceByFilter(@Nonnull INodeFilter<U> nodeFilter);

    TextNodeSelection reduceToText();

    ElementNodeSelection reduceToElement();
}
