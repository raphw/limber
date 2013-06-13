package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomNodeRepresentable;
import no.kantega.lab.limber.dom.abstraction.IDomSelectionQueryable;
import no.kantega.lab.limber.dom.abstraction.IDomSelectionReduceable;
import no.kantega.lab.limber.dom.element.*;
import no.kantega.lab.limber.dom.filter.*;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;

import javax.annotation.Nonnull;
import java.util.*;

public class NodeSelection<N extends AbstractNode<? extends N>, S extends NodeSelection<N, ?>> implements
        IDomNodeRepresentable<N>, IDomSelectionQueryable<N>, IDomSelectionReduceable<N>, Iterable<N> {

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
    public S clear() {
        for (N node : getSelected()) {
            node.clear();
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setRendered(boolean render) {
        for (N node : getSelected()) {
            node.setRendered(render);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S remove() {
        for (N node : getSelected()) {
            node.remove();
        }
        return (S) this;
    }

    @Override
    public int size() {
        return getSelected().size();
    }


    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setContent(CharSequence content) {
        for (N node : getSelected()) {
            node.setContent(content);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setContent(CharSequence content, @Nonnull ContentEscapeMode escapeMode) {
        for (N node : getSelected()) {
            node.setContent(content, escapeMode);
        }
        return (S) this;
    }

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
    public NodeSelection<?, ?> reduceByFilter(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter) {
        return reduceByFilter(nodeFilter, AbstractNode.class);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> reduceByFilter(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N3> filterBoundary) {
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
        for (N element : getSelected()) {
            ElementNode parent = element.getParent();
            if (parent != null) parents.add(parent);
        }
        return new ElementNodeSelection(parents);
    }

    @Nonnull
    @Override
    public ElementNodeSelection getRoot() {
        LinkedHashSet<ElementNode> roots = new LinkedHashSet<ElementNode>();
        for (N element : getSelected()) {
            ElementNode root = element.getRoot();
            if (root != null) roots.add(root);
        }
        return new ElementNodeSelection(roots);
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> getSiblings() {
        return getSiblings(false);
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> getSiblings(boolean includeMe) {
        return getSiblings(new BooleanRepeaterFilter<AbstractNode<?>>(true), includeMe);
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> getSiblings(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter) {
        return getSiblings(nodeFilter, false);
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
    public <N2 extends AbstractNode<? extends N2>> NodeSelection<N2, ?> replaceBy(@Nonnull N2 prototype) {
        List<N2> elements = new ArrayList<N2>();
        for (AbstractNode<?> element : getSelected()) {
            elements.add(element.replaceBy(prototype));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(elements);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S replaceByAndStay(@Nonnull AbstractNode<?> prototype) {
        replaceBy(prototype);
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S clone() {
        try {
            return (S) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeSelection that = (NodeSelection) o;
        return selected.equals(that.selected);
    }

    @Override
    public int hashCode() {
        return selected.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s[size=%d]", getClass().getSimpleName(), getSelected().size());
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S visit(@Nonnull IDomNodeVisitor<? super N> visitor) {
        for (N element : getSelected()) {
            element.visit(visitor);
        }
        return (S) this;
    }
}
