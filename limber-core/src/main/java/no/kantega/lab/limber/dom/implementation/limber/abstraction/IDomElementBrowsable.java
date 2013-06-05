package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;
import no.kantega.lab.limber.dom.implementation.limber.filter.INodeFilter;
import no.kantega.lab.limber.dom.implementation.limber.filter.QueryMatchMode;
import no.kantega.lab.limber.dom.implementation.limber.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.NodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.TextNodeSelection;

public interface IDomElementBrowsable {

    ElementNodeSelection findByTag(CharSequence tagName);

    ElementNode findById(CharSequence id);

    ElementNodeSelection findByAttr(CharSequence key);

    ElementNodeSelection findByAttr(CharSequence key, CharSequence value, QueryMatchMode filterMatchMode);

    <S extends AbstractNode<S>, T extends NodeSelection<T, S>> NodeSelection<T, S> findByFilter(INodeFilter<S> nodeFilter);

    TextNodeSelection findTextNodes();

    ElementNodeSelection findElements();
}
