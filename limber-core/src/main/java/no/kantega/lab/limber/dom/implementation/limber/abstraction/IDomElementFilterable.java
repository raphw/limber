package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.filter.INodeFilter;
import no.kantega.lab.limber.dom.implementation.limber.filter.QueryMatchMode;
import no.kantega.lab.limber.dom.implementation.limber.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.NodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.TextNodeSelection;

public interface IDomElementFilterable<T extends AbstractNode<T>, S extends NodeSelection<?, T>> {

    T get(int index);

    NodeSelection<?, T> get(int from, int to);

    ElementNodeSelection reduceByTag(CharSequence tagName);

    ElementNodeSelection reduceByAttr(CharSequence key);

    ElementNodeSelection reduceByAttr(CharSequence key, CharSequence value, QueryMatchMode queryMatchMode);

    <U extends AbstractNode<U>, V extends NodeSelection<V, U>> NodeSelection<V, U> reduceByFilter(INodeFilter<U> nodeFilter);

    TextNodeSelection reduceToText();

    ElementNodeSelection reduceToElement();
}
