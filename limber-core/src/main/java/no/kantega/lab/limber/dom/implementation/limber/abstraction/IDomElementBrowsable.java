package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.filter.AbstractNodeFilter;
import no.kantega.lab.limber.dom.implementation.limber.filter.FilterMatchMode;
import no.kantega.lab.limber.dom.implementation.limber.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.NodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.TextNodeSelection;

public interface IDomElementBrowsable {

    ElementNodeSelection findByTag(CharSequence tagName);

    ElementNodeSelection findByAttr(CharSequence key);

    ElementNodeSelection findByAttr(CharSequence key, CharSequence value, FilterMatchMode filterMatchMode);

    <S extends AbstractNode<S>, T extends NodeSelection<T, S>> NodeSelection<? extends T, S> findByFilter(AbstractNodeFilter<S> nodeFilter);

    TextNodeSelection findText();

    ElementNodeSelection findElement();
}
