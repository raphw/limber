package no.kantega.lab.limber.dom.implementation.limber.selection;

import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomElementFilterable;
import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomNodeMorphable;
import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.element.NodeAttachment;
import no.kantega.lab.limber.dom.implementation.limber.filter.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodeSelection<T extends NodeSelection<?, S>, S extends AbstractNode<S>>
        implements IDomNodeMorphable<T, S>, IDomElementFilterable {

    private final List<S> selected;

    public NodeSelection(List<S> selected) {
        this.selected = Collections.unmodifiableList(new ArrayList<S>(selected));
    }

    protected NodeSelection(NodeSelection<?, S> that) {
        this.selected = that.getSelected();
    }

    protected final List<S> getSelected() {
        return selected;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T clear() {
        for (AbstractNode<S> node : getSelected()) {
            node.clear();
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setRender(boolean render) {
        for (AbstractNode<S> node : getSelected()) {
            node.setRender(render);
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove() {
        for (AbstractNode<S> node : getSelected()) {
            node.remove();
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T addNodeAttachment(NodeAttachment<? extends S> nodeAttachment) {
        for (AbstractNode<S> node : getSelected()) {
            node.addNodeAttachment(nodeAttachment);
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T removeNodeAttachment(NodeAttachment<? extends S> nodeAttachment) {
        for (AbstractNode<S> node : getSelected()) {
            node.removeNodeAttachment(nodeAttachment);
        }
        return (T) this;
    }

    @Override
    public ElementNodeSelection reduceByTag(CharSequence tagName) {
        return new ElementNodeSelection(this.reduceByFilter(new TagNameFilter(tagName)));
    }

    @Override
    public ElementNodeSelection reduceByAttr(CharSequence key) {
        return new ElementNodeSelection(this.reduceByFilter(new AttributeKeyExistenceFilter(key)));
    }

    @Override
    public ElementNodeSelection reduceByAttr(CharSequence key, CharSequence value, FilterMatchMode filterMatchMode) {
        return new ElementNodeSelection(this.reduceByFilter(new AttributeKeyValueFilter(key, value, filterMatchMode)));
    }

    @Override
    public <V extends AbstractNode<V>, U extends NodeSelection<U, V>> NodeSelection<? extends U, V> reduceByFilter(AbstractNodeFilter<V> nodeFilter) {
        Class<V> filterArgumentClass = nodeFilter.getParameterClass();
        List<V> foundNodes = new ArrayList<V>();
        for (S node : getSelected()) {
            if (!filterArgumentClass.isAssignableFrom(filterArgumentClass)) {
                continue;
            }
            V castNode = filterArgumentClass.cast(node);
            if (nodeFilter.filter(castNode)) {
                foundNodes.add(castNode);
            }
        }
        return new NodeSelection<U, V>(foundNodes);
    }

    @Override
    public TextNodeSelection reduceToText() {
        return new TextNodeSelection(this.reduceByFilter(new TextNodeFilter()));
    }

    @Override
    public ElementNodeSelection reduceToElement() {
        return new ElementNodeSelection(this.reduceByFilter(new ElementNodeFilter()));
    }
}
