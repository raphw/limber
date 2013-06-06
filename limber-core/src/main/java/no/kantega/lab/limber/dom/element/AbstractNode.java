package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomNodeBrowsable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeQueryable;
import no.kantega.lab.limber.dom.filter.BooleanRepeaterFilter;
import no.kantega.lab.limber.dom.filter.ConjunctionFilter;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.NodeExclusionFilter;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.selection.NodeSelection;

import javax.annotation.Nonnull;
import java.util.Collections;

public abstract class AbstractNode<N extends AbstractNode<N>> implements IDomNodeMorphable<N, N>,
        IDomNodeQueryable, IDomNodeBrowsable<ElementNode> {

    private boolean rendered;
    private ElementNode parent;

//    private Map<IDomNodeVisitor<? super N>, IDomNodeVisitor.VisitingStickyMode> nodeAttachments;

    protected AbstractNode() {
        this.rendered = true;
    }

//    @Nonnull
//    @Override
//    @SuppressWarnings("unchecked")
//    public N visit(@Nonnull IDomNodeVisitor<? super N> nodeVisitor) {
//        nodeVisitor.visit(this);
//        return (N) this;
//    }
//
//    @Nonnull
//    @Override
//    @SuppressWarnings("unchecked")
//    public N addStickyNodeVisitor(@Nonnull IDomNodeVisitor<? super N> nodeVisitor, @Nonnull IDomNodeVisitor.VisitingStickyMode visitingStickyMode) {
//        if (nodeAttachments == null)
//            nodeAttachments = new LinkedHashMap<IDomNodeVisitor<? super N>, IDomNodeVisitor.VisitingStickyMode>();
//        nodeAttachments.put(nodeVisitor, visitingStickyMode);
//        return (N) this;
//    }
//
//    @Nonnull
//    @Override
//    @SuppressWarnings("unchecked")
//    public N removeStickyNodeVisitor(@Nonnull IDomNodeVisitor<?> nodeVisitor) {
//        if (nodeAttachments == null) return (N) this;
//        nodeAttachments.remove(nodeVisitor);
//        if (nodeAttachments.size() == 0) nodeAttachments = null;
//        return (N) this;
//    }

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
    public NodeSelection<AbstractNode<?>, ?> getSiblings() {
        return getSiblings(false);
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode<?>, ?> getSiblings(boolean includeMe) {
        return getSiblings(new BooleanRepeaterFilter<AbstractNode<?>>(true), includeMe);
    }


    @Nonnull
    @Override
    public <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter) {
        return getSiblings(nodeFilter, false);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, boolean includeMe) {
        NodeSelection<N2, ?> siblingSelection;
        if (getParent() != null) {
            if (!includeMe) nodeFilter = new ConjunctionFilter<N2>(nodeFilter, new NodeExclusionFilter<N2>(this));
            siblingSelection = getParent().getChildren(nodeFilter);
        } else if (!includeMe) {
            siblingSelection = new NodeSelection<N2, C2>(Collections.<N2>emptyList());
        } else {
            N2 filterResultNode = NodeFilterSupport.getInstance().filterNode(this, nodeFilter);
            siblingSelection = new NodeSelection<N2, C2>(filterResultNode == null
                    ? Collections.<N2>emptyList()
                    : Collections.singletonList(filterResultNode));
        }
        return new NodeSelection<N2, C2>(siblingSelection);
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
