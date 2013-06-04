package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.filter.AbstractNodeFilter;
import no.kantega.lab.limber.dom.implementation.limber.filter.FilterMatchMode;
import no.kantega.lab.limber.dom.implementation.limber.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.NodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.TextNodeSelection;

public interface IDomElementFilterable {

    ElementNodeSelection reduceByTag(CharSequence tagName);

    ElementNodeSelection reduceByAttr(CharSequence key);

    ElementNodeSelection reduceByAttr(CharSequence key, CharSequence value, FilterMatchMode filterMatchMode);

    <S extends AbstractNode<S>, T extends NodeSelection<T, S>> NodeSelection<? extends T, S> reduceByFilter(AbstractNodeFilter<S> nodeFilter);

    TextNodeSelection reduceToText();

    ElementNodeSelection reduceToElement();
}
