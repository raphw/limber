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

public abstract class AbstractNode<N extends AbstractNode> implements IDomNodeMorphable<N>,
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

    @Override
    public ElementNode getRoot() {
        ElementNode root = getParent();
        while (root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> getSiblings() {
        return getSiblings(false);
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> getSiblings(boolean includeMe) {
        return getSiblings(new BooleanRepeaterFilter<AbstractNode>(true), includeMe);
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> getSiblings(@Nonnull INodeFilter<AbstractNode> nodeFilter) {
        return getSiblings(nodeFilter, AbstractNode.class);
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> getSiblings(@Nonnull INodeFilter<AbstractNode> nodeFilter, boolean includeMe) {
        return getSiblings(nodeFilter, AbstractNode.class, false);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary) {
        return getSiblings(nodeFilter, filterBoundary, false);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary, boolean includeMe) {
        NodeSelection<N2, ?> siblingSelection;
        // Parent existent, find children of parent and exclude myself, if not required.
        if (getParent() != null) {
            if (!includeMe) nodeFilter = new ConjunctionFilter<N2>(nodeFilter, new NodeExclusionFilter<N2>(this));
            siblingSelection = getParent().getChildren(nodeFilter, filterBoundary);
        }
        // No parent exists, if node should not be included, this yields an empty list.
        else if (!includeMe) {
            siblingSelection = new NodeSelection<N2, NodeSelection<N2, ?>>(Collections.<N2>emptyList());
        }
        // No parent exists, this node should be included if filter matches it.
        else {
            N2 filterResultNode = NodeFilterSupport.getInstance().filterNode(this, nodeFilter, filterBoundary);
            siblingSelection = new NodeSelection<N2, NodeSelection<N2, ?>>(
                    filterResultNode == null
                            ? Collections.<N2>emptyList()
                            : Collections.singletonList(filterResultNode));

        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(siblingSelection);
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
