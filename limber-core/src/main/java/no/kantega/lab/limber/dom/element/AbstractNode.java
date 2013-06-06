package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomNodeBrowsable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeQueryable;
import no.kantega.lab.limber.dom.filter.BooleanRepeaterFilter;
import no.kantega.lab.limber.dom.filter.ConjunctionFilter;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.NodeExclusionFilter;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractNode<N extends AbstractNode<N>> implements IDomNodeMorphable<N, N>,
        IDomNodeQueryable, IDomNodeBrowsable {

    private boolean rendered;
    private ElementNode parent;
    private List<NodeAttachment<? extends N>> nodeAttachments;

    protected AbstractNode() {
        this.rendered = true;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N addNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment) {
        if (nodeAttachments == null) nodeAttachments = new ArrayList<NodeAttachment<? extends N>>();
        nodeAttachments.add(nodeAttachment);
        return (N) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N removeNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment) {
        if (nodeAttachments == null) return (N) this;
        nodeAttachments.remove(nodeAttachment);
        if (nodeAttachments.size() == 0) nodeAttachments = null;
        return (N) this;
    }

    @Nonnull
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

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N setRendered(boolean rendered) {
        this.rendered = rendered;
        return (N) this;
    }

    @Override
    public ElementNode getParent() {
        return parent;
    }

    @Nonnull
    @Override
    public List<AbstractNode<?>> getSiblings() {
        return getSiblings(false);
    }

    @Nonnull
    @Override
    public List<AbstractNode<?>> getSiblings(boolean includeMe) {
        return getSiblings(new BooleanRepeaterFilter(true), includeMe);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<N2>> List<N2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter) {
        return getSiblings(nodeFilter, false);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<N2>> List<N2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, boolean includeMe) {
        if (getParent() != null) {
            if (!includeMe) nodeFilter = new ConjunctionFilter<N2>(nodeFilter, new NodeExclusionFilter<N2>(this));
            return getParent().getChildren(nodeFilter);
        } else {
            if (!includeMe) {
                return Collections.emptyList();
            } else {
                N2 filterResultNode = NodeFilterSupport.getInstance().filterNode(this, nodeFilter);
                return filterResultNode == null ? Collections.<N2>emptyList() : Arrays.asList(filterResultNode);
            }
        }
    }

    @Nonnull
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
