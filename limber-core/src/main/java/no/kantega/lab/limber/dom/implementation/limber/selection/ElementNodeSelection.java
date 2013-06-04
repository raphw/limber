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
        return new ElementNodeSelection(findByFilter(new TagNameFilter(tagName)));
    }

    @Override
    public ElementNode findById(CharSequence id) {
        ElementNode result = null;
        for (ElementNode elementNode : getSelected()) {
            ElementNode found = elementNode.findById(id);
            if (found != null) {
                if (result == null) {
                    result = found;
                } else {
                    throw new IllegalStateException();
                }
            }
        }
        return result;
    }

    @Override
    public ElementNodeSelection findByAttr(CharSequence key) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyExistenceFilter(key)));
    }

    @Override
    public ElementNodeSelection findByAttr(CharSequence key, CharSequence value, FilterMatchMode filterMatchMode) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyValueFilter(key, value, filterMatchMode)));
    }

    @Override
    public <S extends AbstractNode<S>, U extends NodeSelection<U, S>> NodeSelection<U, S> findByFilter(AbstractNodeFilter<S> nodeFilter) {
        List<S> resultSelection = new LinkedList<S>();
        for (ElementNode elementNode : getSelected()) {
            resultSelection.addAll(DomTreeBrowserHelper.getInstance().filter(elementNode, nodeFilter, Integer.MAX_VALUE));
        }
        return new NodeSelection<U, S>(resultSelection);
    }

    @Override
    public TextNodeSelection findText() {
        return new TextNodeSelection(findByFilter(new TextNodeFilter()));
    }

    @Override
    public ElementNodeSelection findElement() {
        return new ElementNodeSelection(findByFilter(new ElementNodeFilter()));
    }

}
