package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;

public interface IDomElementBrowsable<U extends AbstractNode<?>> extends Iterable<U> {

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
