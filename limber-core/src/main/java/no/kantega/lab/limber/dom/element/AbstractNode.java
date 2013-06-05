package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeQueryable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode<N extends AbstractNode<N>> implements IDomNodeMorphable<N, N>, IDomNodeQueryable {

    private boolean rendered;
    private ElementNode parent;
    private List<NodeAttachment<? extends N>> nodeAttachments;

    protected AbstractNode() {
        this.rendered = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public N addNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment) {
        if (nodeAttachments == null) nodeAttachments = new ArrayList<NodeAttachment<? extends N>>();
        nodeAttachments.add(nodeAttachment);
        return (N) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public N removeNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment) {
        if (nodeAttachments == null) return (N) this;
        nodeAttachments.remove(nodeAttachment);
        if (nodeAttachments.size() == 0) nodeAttachments = null;
        return (N) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public N remove() {
        if (parent == null) return (N) this;
        parent.removeChild(this);
        parent = null;
        return (N) this;
    }

    @Override
    public boolean isRendered() {
        return rendered;
    }

    @Override
    @SuppressWarnings("unchecked")
    public N setRendered(boolean rendered) {
        this.rendered = rendered;
        return (N) this;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public N clone() {
        try {
            return (N) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        return String.format("%s[%b]", getClass().getName(), rendered);
    }
}
