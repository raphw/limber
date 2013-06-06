package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomElementFilterable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeBrowsable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomSelectionQueryable;
import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.*;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;

import javax.annotation.Nonnull;
import java.util.*;

public class NodeSelection<N extends AbstractNode<?>, C extends NodeSelection<N, ?>>
        implements IDomNodeMorphable<N, C>, IDomElementFilterable<N>, IDomSelectionQueryable<N>, IDomNodeBrowsable<ElementNodeSelection> {

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
        return new ElementNodeSelection(this.reduceByFilter(new TagNameFilter(tagName)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection reduceByAttr(@Nonnull CharSequence key) {
        return new ElementNodeSelection(this.reduceByFilter(new AttributeKeyExistenceFilter(key)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection reduceByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode) {
        return new ElementNodeSelection(this.reduceByFilter(new AttributeKeyValueFilter(key, value, queryMatchMode)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> reduceByFilter(@Nonnull INodeFilter<N2> nodeFilter) {
        return new NodeSelection<N2, C2>(NodeFilterSupport.getInstance().filterNodeList(getSelected(), nodeFilter));
    }

    @Nonnull
    @Override
    public TextNodeSelection reduceToText() {
        return new TextNodeSelection(this.reduceByFilter(new TextNodeFilter()));
    }

    @Nonnull
    @Override
    public ElementNodeSelection reduceToElement() {
        return new ElementNodeSelection(this.reduceByFilter(new ElementNodeFilter()));
    }

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
    public NodeSelection<AbstractNode<?>, ?> getSiblings() {
        return getSiblings(false);
    }


    @Nonnull
    @Override
    public NodeSelection<AbstractNode<?>, ?> getSiblings(boolean includeMe) {
        LinkedHashSet<AbstractNode<?>> siblings = new LinkedHashSet<AbstractNode<?>>();
        for (AbstractNode<?> element : getSelected()) {
            siblings.addAll(element.getSiblings().getSelected());
        }
        return new NodeSelection<AbstractNode<?>, NodeSelection<AbstractNode<?>, ?>>(siblings);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter) {
        return getSiblings(nodeFilter, false);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> getSiblings(@Nonnull INodeFilter<N2> nodeFilter, boolean includeMe) {
        LinkedHashSet<N2> siblings = new LinkedHashSet<N2>();
        for (AbstractNode<?> element : getSelected()) {
            siblings.addAll(element.getSiblings(nodeFilter, includeMe).getSelected());
        }
        return new NodeSelection<N2, C2>(siblings);
    }

    @Nonnull
    @Override
    public List<N> nodeList() {
        return getSelected();
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
    @SuppressWarnings("unchecked")
    public C clone() {
        try {
            return (C) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException();
        }
    }
}
