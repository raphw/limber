package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomElementFilterable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomSelectionQueryable;
import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.NodeAttachment;
import no.kantega.lab.limber.dom.filter.*;

import javax.annotation.Nonnull;
import java.util.*;

public class NodeSelection<N extends AbstractNode<N>, C extends NodeSelection<N, ?>>
        implements IDomNodeMorphable<N, C>, IDomElementFilterable<N>, IDomSelectionQueryable {

    private final List<N> selected;

    public NodeSelection(@Nonnull List<N> selected) {
        if (selected.contains(null)) throw new IllegalArgumentException();
        this.selected = Collections.unmodifiableList(new ArrayList<N>(selected));
    }

    protected NodeSelection(@Nonnull NodeSelection<N, ?> that) {
        this.selected = that.getSelected();
    }

    protected final List<N> getSelected() {
        return selected;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C clear() {
        for (AbstractNode<N> node : getSelected()) {
            node.clear();
        }
        return (C) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C setRendered(boolean render) {
        for (AbstractNode<N> node : getSelected()) {
            node.setRendered(render);
        }
        return (C) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C remove() {
        for (AbstractNode<N> node : getSelected()) {
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
        for (AbstractNode<N> node : getSelected()) {
            node.setContent(content);
        }
        return (C) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C setContent(CharSequence content, @Nonnull ContentEscapeMode escapeMode) {
        for (AbstractNode<N> node : getSelected()) {
            node.setContent(content, escapeMode);
        }
        return (C) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C addNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment) {
        for (AbstractNode<N> node : getSelected()) {
            node.addNodeAttachment(nodeAttachment);
        }
        return (C) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public C removeNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment) {
        for (AbstractNode<N> node : getSelected()) {
            node.removeNodeAttachment(nodeAttachment);
        }
        return (C) this;
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
    public <N2 extends AbstractNode<N2>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> reduceByFilter(@Nonnull INodeFilter<N2> nodeFilter) {
        Class<? extends N2> filterArgumentClass = NodeFilterSupport.getInstance().findFilterParameterClass(nodeFilter);
        List<N2> foundNodes = new ArrayList<N2>();
        for (N node : getSelected()) {
            if (!filterArgumentClass.isAssignableFrom(filterArgumentClass)) {
                continue;
            }
            N2 castNode = filterArgumentClass.cast(node);
            if (nodeFilter.filter(castNode)) {
                foundNodes.add(castNode);
            }
        }
        return new NodeSelection<N2, C2>(foundNodes);
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
