package no.kantega.lab.limber.dom.implementation.limber.element;

import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomNodeMorphable;
import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomNodeQueryable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode<T extends AbstractNode<T>> implements IDomNodeMorphable<T, T>,
        IDomNodeQueryable, Cloneable {

    private boolean rendered;

    private ElementNode parent;

    private List<NodeAttachment<? extends T>> nodeAttachments;

    protected AbstractNode() {
        this.rendered = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T addNodeAttachment(NodeAttachment<? extends T> nodeAttachment) {
        if (nodeAttachments == null) nodeAttachments = new ArrayList<NodeAttachment<? extends T>>();
        nodeAttachments.add(nodeAttachment);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T removeNodeAttachment(NodeAttachment<? extends T> nodeAttachment) {
        if (nodeAttachments == null) return (T) this;
        nodeAttachments.remove(nodeAttachment);
        if (nodeAttachments.size() == 0) nodeAttachments = null;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove() {
        if (parent == null) return (T) this;
        parent.removeChild(this);
        parent = null;
        return (T) this;
    }

    @Override
    public boolean isRendered() {
        return rendered;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setRendered(boolean rendered) {
        this.rendered = rendered;
        return (T) this;
    }

    @Override
    public abstract T clear();

    @Override
    @SuppressWarnings("unchecked")
    protected T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        return String.format("%s[%b]", getClass().getName(), rendered);
    }
}
