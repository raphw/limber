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

    ElementNodeSelection findByTag(CharSequence tagName, int maxDepth);

    ElementNode findById(CharSequence id);

    ElementNode findById(CharSequence id, int maxDepth);

    ElementNodeSelection findByCssClass(CharSequence cssClassName);

    ElementNodeSelection findByCssClass(CharSequence cssClassName, int maxDepth);

    ElementNodeSelection findByAttr(CharSequence key);

    ElementNodeSelection findByAttr(CharSequence key, int maxDepth);

    ElementNodeSelection findByAttr(CharSequence key, CharSequence value, QueryMatchMode filterMatchMode);

    ElementNodeSelection findByAttr(CharSequence key, CharSequence value, QueryMatchMode filterMatchMode, int maxDepth);

    <S extends AbstractNode<S>, T extends NodeSelection<T, S>> NodeSelection<T, S> findByFilter(INodeFilter<S> nodeFilter);

    <S extends AbstractNode<S>, T extends NodeSelection<T, S>> NodeSelection<T, S> findByFilter(INodeFilter<S> nodeFilter, int maxDepth);

    TextNodeSelection findTextNodes();

    TextNodeSelection findTextNodes(int maxDepth);

    ElementNodeSelection findElements();

    ElementNodeSelection findElements(int maxDepth);
}
