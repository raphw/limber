package no.kantega.lab.limber.dom.implementation.limber.selection;

import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomElementBrowsable;
import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomElementMorphable;
import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;
import no.kantega.lab.limber.dom.implementation.limber.filter.*;

import java.util.LinkedList;
import java.util.List;

public class ElementNodeSelection extends NodeSelection<ElementNodeSelection, ElementNode>
        implements IDomElementMorphable<ElementNodeSelection>, IDomElementBrowsable {

    public ElementNodeSelection(List<ElementNode> selected) {
        super(selected);
    }

    public ElementNodeSelection(NodeSelection<?, ElementNode> that) {
        super(that);
    }

    @Override
    public ElementNodeSelection setTagName(CharSequence tagName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setTagName(tagName);
        }
        return this;
    }

    @Override
    public ElementNodeSelection addChild(int index, AbstractNode<?> node) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addChild(index, node);
        }
        return this;
    }

    @Override
    public ElementNodeSelection appendChild(AbstractNode<?> node) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.appendChild(node);
        }
        return this;
    }

    @Override
    public ElementNodeSelection prependChild(AbstractNode<?> node) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.prependChild(node);
        }
        return this;
    }

    @Override
    public ElementNodeSelection putAttr(CharSequence key, CharSequence value) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.putAttr(key, value);
        }
        return this;
    }

    @Override
    public ElementNodeSelection setId(CharSequence id) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setId(id);
        }
        return this;
    }

    @Override
    public ElementNodeSelection setRandomId() {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setRandomId();
        }
        return this;
    }

    @Override
    public ElementNodeSelection removeId() {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeId();
        }
        return this;
    }

    @Override
    public ElementNodeSelection removeAttr(CharSequence key) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeAttr(key);
        }
        return this;
    }

    @Override
    public ElementNodeSelection addCssClass(CharSequence cssClassName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addCssClass(cssClassName);
        }
        return this;
    }

    @Override
    public ElementNodeSelection removeCssClass(CharSequence cssClassName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeCssClass(cssClassName);
        }
        return this;
    }

    @Override
    public ElementNodeSelection addCssStyle(CharSequence styleKey, CharSequence styleValue) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addCssStyle(styleKey, styleValue);
        }
        return this;
    }

    @Override
    public ElementNodeSelection removeCssStyle(CharSequence styleKey) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeCssStyle(styleKey);
        }
        return this;
    }

    @Override
    public ElementNodeSelection get(int from, int to) {
        return new ElementNodeSelection(super.get(from, to));
    }

    @Override
    public ElementNodeSelection findByTag(CharSequence tagName) {
        return findByTag(tagName, Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findByTag(CharSequence tagName, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new TagNameFilter(tagName), maxDepth));
    }

    @Override
    public ElementNode findById(CharSequence id) {
        return findById(id, Integer.MAX_VALUE);
    }

    @Override
    public ElementNode findById(CharSequence id, int maxDepth) {
        ElementNodeSelection elementNodeSelection = findByAttr("id", id, QueryMatchMode.FULL_MATCH, maxDepth);
        if (elementNodeSelection.size() == 0) {
            return null;
        } else if (elementNodeSelection.size() > 1) {
            throw new IllegalStateException();
        } else {
            return elementNodeSelection.get(0);
        }
    }

    @Override
    public ElementNodeSelection findByCssClass(CharSequence cssClassName) {
        return findByCssClass(cssClassName, Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findByCssClass(CharSequence cssClassName, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new CssClassNameFilter(cssClassName), maxDepth));
    }

    @Override
    public ElementNodeSelection findByAttr(CharSequence key) {
        return findByAttr(key, Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findByAttr(CharSequence key, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyExistenceFilter(key), maxDepth));
    }

    @Override
    public ElementNodeSelection findByAttr(CharSequence key, CharSequence value, QueryMatchMode filterMatchMode) {
        return findByAttr(key, value, filterMatchMode, Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findByAttr(CharSequence key, CharSequence value, QueryMatchMode filterMatchMode, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyValueFilter(key, value, filterMatchMode), maxDepth));
    }

    @Override
    public <S extends AbstractNode<S>, U extends NodeSelection<U, S>> NodeSelection<U, S> findByFilter(INodeFilter<S> nodeFilter) {
        return findByFilter(nodeFilter, Integer.MAX_VALUE);
    }

    @Override
    public <S extends AbstractNode<S>, U extends NodeSelection<U, S>> NodeSelection<U, S> findByFilter(INodeFilter<S> nodeFilter, int maxDepth) {
        List<S> resultSelection = new LinkedList<S>();
        for (ElementNode elementNode : getSelected()) {
            resultSelection.addAll(NodeFilterSupport.getInstance().filter(elementNode, nodeFilter, maxDepth));
        }
        return new NodeSelection<U, S>(resultSelection);
    }

    @Override
    public TextNodeSelection findTextNodes() {
        return findTextNodes(Integer.MAX_VALUE);
    }

    @Override
    public TextNodeSelection findTextNodes(int maxDepth) {
        return new TextNodeSelection(findByFilter(new TextNodeFilter(), maxDepth));
    }

    @Override
    public ElementNodeSelection findElements() {
        return findElements(Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findElements(int maxDepth) {
        return new ElementNodeSelection(findByFilter(new ElementNodeFilter(), maxDepth));
    }
}
