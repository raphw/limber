package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomNodeBrowsable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomSelectionQueryable;
import no.kantega.lab.limber.dom.abstraction.IDomSelectionReduceable;
import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.TextNode;
import no.kantega.lab.limber.dom.filter.*;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;

import javax.annotation.Nonnull;
import java.util.*;

public class NodeSelection<N extends AbstractNode, C extends NodeSelection<N, ?>> implements
        IDomNodeMorphable<C>, IDomNodeBrowsable<ElementNodeSelection>,
        IDomSelectionQueryable<N>, IDomSelectionReduceable<N> {

    private final List<N> selected;

    public NodeSelection(@Nonnull List<N> selected) {
        if (selected.contains(null)) throw new IllegalArgumentException();
        this.selected = Collections.unmodifiableList(new ArrayList<N>(selected));
    }

    public NodeSelection(@Nonnull NodeSelection<N, ?> that) {
        this.selected = that.getSelected();
    }

    public NodeSelection(@Nonnull LinkedHashSet<N> selected) {
        if (selected.contains(null)) throw new IllegalArgumentException();
        this.selected = Collections.unmodifiableList(new ArrayList<N>(selected));
    }

    @Nonnull
    protected final List<N> getSelected() {
        return selected;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C clear() {
        for (AbstractNode<?> node : getSelected()) {
            node.clear();
        }
        return (C) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C setRendered(boolean render) {
        for (AbstractNode<?> node : getSelected()) {
            node.setRendered(render);
        }
        return (C) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C remove() {
        for (AbstractNode<?> node : getSelected()) {
            node.remove();
        }
        return (C) this;
    }

    @Override
    public int size() {
        return getSelected().size();
    }


    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C setContent(CharSequence content) {
        for (AbstractNode<?> node : getSelected()) {
            node.setContent(content);
        }
        return (C) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C setContent(CharSequence content, @Nonnull ContentEscapeMode escapeMode) {
        for (AbstractNode<?> node : getSelected()) {
            node.setContent(content, escapeMode);
        }
        return (C) this;
    }

//    @Nonnull
//    @Override
//    @SuppressWarnings("unchecked")
//    public C addStickyNodeVisitor(@Nonnull IDomNodeVisitor<? super N> nodeVisitor, @Nonnull IDomNodeVisitor.VisitingStickyMode visitingStickyMode) {
//        for (AbstractNode<?> node : getSelected()) {
//            node.addStickyNodeVisitor(nodeVisitor, visitingStickyMode);
//        }
//        return (C) this;
//    }
//
//    @Nonnull
//    @Override
//    @SuppressWarnings("unchecked")
//    public C visit(@Nonnull IDomNodeVisitor<? super N> nodeVisitor) {
//        for (AbstractNode<?> node : getSelected()) {
//            node.visit(nodeVisitor);
//        }
//        return (C) this;
//    }
//
//    @Nonnull
//    @Override
//    @SuppressWarnings("unchecked")
//    public C removeStickyNodeVisitor(@Nonnull IDomNodeVisitor<?> nodeVisitor) {
//        for (AbstractNode<?> node : getSelected()) {
//            node.removeStickyNodeVisitor(nodeVisitor);
//        }
//        return (C) this;
//    }

    @Nonnull
    @Override
    public N get(int index) {
        if (index < 0 || index >= selected.size()) {
            throw new IndexOutOfBoundsException();
        }
        return selected.get(index);
    }

    @Nonnull
    @Override
    public NodeSelection<N, ?> get(int from, int to) {
        if (from < 0 || to < 0 || from >= selected.size() || to >= selected.size()) {
            throw new IndexOutOfBoundsException();
        }
        List<N> subSelection = new LinkedList<N>();
        for (int i = from; i < to; i++) {
            subSelection.add(getSelected().get(i));
        }
        return new NodeSelection<N, NodeSelection<N, ?>>(subSelection);
    }

    @Nonnull
    @Override
    public ElementNodeSelection reduceByTag(@Nonnull CharSequence tagName) {
        return new ElementNodeSelection(this.reduceByFilter(new TagNameFilter(tagName), ElementNode.class));
    }

    @Nonnull
    @Override
    public ElementNodeSelection reduceByAttr(@Nonnull CharSequence key) {
        return new ElementNodeSelection(this.reduceByFilter(new AttributeKeyExistenceFilter(key), ElementNode.class));
    }

    @Nonnull
    @Override
    public ElementNodeSelection reduceByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode) {
        return new ElementNodeSelection(this.reduceByFilter(new AttributeKeyValueFilter(key, value, queryMatchMode), ElementNode.class));
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> reduceByFilter(@Nonnull INodeFilter<AbstractNode> nodeFilter) {
        return reduceByFilter(nodeFilter, AbstractNode.class);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode> NodeSelection<N2, ?> reduceByFilter(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary) {
        return new NodeSelection<N2, NodeSelection<N2, ?>>(NodeFilterSupport.getInstance().filterNodeList(getSelected(), nodeFilter, filterBoundary));
    }

    @Nonnull
    @Override
    public TextNodeSelection reduceToText() {
        return new TextNodeSelection(this.reduceByFilter(new TextNodeFilter(), TextNode.class));
    }

    @Nonnull
    @Override
    public ElementNodeSelection reduceToElement() {
        return new ElementNodeSelection(this.reduceByFilter(new ElementNodeFilter(), ElementNode.class));
    }

    @Nonnull
    @Override
    public ElementNodeSelection getParent() {
        LinkedHashSet<ElementNode> parents = new LinkedHashSet<ElementNode>();
        for (AbstractNode<?> element : getSelected()) {
            ElementNode parent = element.getParent();
            if (parent != null) parents.add(parent);
        }
        return new ElementNodeSelection(parents);
    }

    @Nonnull
    @Override
    public ElementNodeSelection getRoot() {
        LinkedHashSet<ElementNode> roots = new LinkedHashSet<ElementNode>();
        for (AbstractNode<?> element : getSelected()) {
            ElementNode root = element.getRoot();
            if (root != null) roots.add(root);
        }
        return new ElementNodeSelection(roots);
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
        return getSiblings(nodeFilter, false);
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> getSiblings(@Nonnull INodeFilter<AbstractNode> nodeFilter, boolean includeMe) {
        return getSiblings(nodeFilter, AbstractNode.class, includeMe);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary) {
        return getSiblings(nodeFilter, filterBoundary, false);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode> NodeSelection<N2, ?> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary, boolean includeMe) {
        LinkedHashSet<N2> siblings = new LinkedHashSet<N2>();
        for (AbstractNode<?> element : getSelected()) {
            siblings.addAll(element.getSiblings(nodeFilter, filterBoundary, includeMe).getSelected());
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(siblings);
    }

    @Nonnull
    @Override
    public List<N> nodeList() {
        return getSelected();
    }

    @Override
    public boolean isSingleRoot() {
        return getRoot().size() == 1;
    }

    @Nonnull
    @Override
    public Iterator<N> iterator() {
        return new Iterator<N>() {
            private int iterationCount = 0;

            @Override
            public boolean hasNext() {
                return iterationCount < getSelected().size();
            }

            @Override
            public N next() {
                return getSelected().get(iterationCount++);
            }

            @Override
            public void remove() {
                throw new IllegalStateException();
            }
        };
    }

    @Nonnull
    @Override
    public <N extends AbstractNode> NodeSelection<N, ?> replaceBy(@Nonnull N prototype) {
        List<N> elements = new ArrayList<N>();
        for (AbstractNode<?> element : getSelected()) {
            elements.add(element.replaceBy(prototype));
        }
        return new NodeSelection<N, NodeSelection<N, ?>>(elements);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C replaceByAndStay(@Nonnull AbstractNode<?> prototype) {
        replaceBy(prototype);
        return (C) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C clone() {
        try {
            return (C) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException();
        }
    }
}
