package no.kantega.lab.limber.dom.implementation.limber.element;

import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomNodeMorphable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode<T extends AbstractNode<T>> implements IDomNodeMorphable<T, T>, Cloneable {

    private boolean render;

    private ElementNode parent;

    private List<NodeAttachment<? extends T>> nodeAttachments;

    protected AbstractNode() {
        this.render = true;
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

    public boolean isRender() {
        return render;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setRender(boolean render) {
        this.render = render;
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
        return String.format("%s[%b]", getClass().getName(), render);
    }
}
