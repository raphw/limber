package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomNodeQueryable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeRepresentable;
import no.kantega.lab.limber.dom.filter.BooleanRepeaterFilter;
import no.kantega.lab.limber.dom.filter.ConjunctionFilter;
import no.kantega.lab.limber.dom.filter.INodeFilter;
import no.kantega.lab.limber.dom.filter.NodeExclusionFilter;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.page.IHtmlRenderable;
import no.kantega.lab.limber.page.context.IHtmlRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;

public abstract class AbstractNode<N extends AbstractNode<N>> 
        implements IDomNodeRepresentable<N>, IDomNodeQueryable, IHtmlRenderable {

    private boolean rendered;
    private ElementNode<?> parent;

    protected AbstractNode() {
        this.rendered = true;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N visit(@Nonnull IDomNodeVisitor<? super N> visitor) {
        N castThis = (N) this;
        visitor.visit(castThis);
        return castThis;
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
    public ElementNode<?> getParent() {
        return parent;
    }

    @Override
    public ElementNode<?> getRoot() {
        ElementNode<?> root = getParent();
        while (root != null) {
            root = root.getParent();
        }
        return root;
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> getSiblings() {
        return getSiblings(false);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public NodeSelection<?, ?> getSiblings(boolean includeMe) {
        new BooleanRepeaterFilter<AbstractNode<?>>(true);
        return getSiblings(new BooleanRepeaterFilter<AbstractNode<?>>(true), AbstractNode.class, includeMe);
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> getSiblings(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter) {
        return getSiblings(nodeFilter, AbstractNode.class, false);
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> getSiblings(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter, boolean includeMe) {
        return getSiblings(nodeFilter, AbstractNode.class, includeMe);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N3> filterBoundary) {
        return getSiblings(nodeFilter, filterBoundary, false);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N3> filterBoundary, boolean includeMe) {
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
    public <N2 extends AbstractNode<? extends N2>> N2 replaceBy(@Nonnull N2 node) {
        if (getParent() == null) {
            throw new IllegalStateException();
        }
        int currentNodeIndex = getParent().getChildIndex(this);
        getParent().removeChild(this);
        getParent().addChild(currentNodeIndex, node);
        return node;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N replaceByAndStay(@Nonnull AbstractNode<?> node) {
        replaceBy(node);
        return (N) this;
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
        return String.format("%s[rendered=%b]", getClass().getName(), rendered);
    }

    @Override
    public final boolean render(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext) throws IOException {
        return isRendered() && onRender(writer, htmlRenderContext);
    }

    protected abstract boolean onRender(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContexte) throws IOException;
}
