package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;

public interface IDomElementFilterable<T extends AbstractNode<T>, S extends NodeSelection<?, T>> extends Iterable<T> {

    T get(int index);

    NodeSelection<?, T> get(int from, int to);

    ElementNodeSelection reduceByTag(CharSequence tagName);

    ElementNodeSelection reduceByAttr(CharSequence key);

    ElementNodeSelection reduceByAttr(CharSequence key, CharSequence value, QueryMatchMode queryMatchMode);

    <U extends AbstractNode<U>, V extends NodeSelection<V, U>> NodeSelection<V, U> reduceByFilter(INodeFilter<U> nodeFilter);

    TextNodeSelection reduceToText();

    ElementNodeSelection reduceToElement();
}
