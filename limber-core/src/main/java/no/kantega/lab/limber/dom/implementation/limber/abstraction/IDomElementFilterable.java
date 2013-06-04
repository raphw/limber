package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.filter.AbstractNodeFilter;
import no.kantega.lab.limber.dom.implementation.limber.filter.FilterMatchMode;
import no.kantega.lab.limber.dom.implementation.limber.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.NodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.TextNodeSelection;

public interface IDomElementFilterable<T extends AbstractNode<T>, S extends NodeSelection<?, T>> {

    T get(int index);

    NodeSelection<?, T> get(int from, int to);

    ElementNodeSelection reduceByTag(CharSequence tagName);

    ElementNodeSelection reduceByAttr(CharSequence key);

    ElementNodeSelection reduceByAttr(CharSequence key, CharSequence value, FilterMatchMode filterMatchMode);

    <U extends AbstractNode<U>, V extends NodeSelection<V, U>> NodeSelection<V, U> reduceByFilter(AbstractNodeFilter<U> nodeFilter);

    TextNodeSelection reduceToText();

    ElementNodeSelection reduceToElement();
}
