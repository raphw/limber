package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomElementFilterable;
import no.kantega.lab.limber.dom.abstraction.IDomNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.ISizeable;
import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.NodeAttachment;
import no.kantega.lab.limber.dom.filter.*;

import javax.annotation.Nonnull;
import java.util.*;

public class NodeSelection<T extends NodeSelection<?, S>, S extends AbstractNode<S>>
        implements IDomNodeMorphable<T, S>, IDomElementFilterable<S>, ISizeable {

    private final List<S> selected;

    public NodeSelection(@Nonnull List<S> selected) {
        if(selected.contains(null)) throw new IllegalArgumentException();
        this.selected = Collections.unmodifiableList(new ArrayList<S>(selected));
    }

    protected NodeSelection(@Nonnull NodeSelection<?, S> that) {
        this.selected = that.getSelected();
    }

    protected final List<S> getSelected() {
        return selected;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T clear() {
        for (AbstractNode<S> node : getSelected()) {
            node.clear();
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setRendered(boolean render) {
        for (AbstractNode<S> node : getSelected()) {
            node.setRendered(render);
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove() {
        for (AbstractNode<S> node : getSelected()) {
            node.remove();
        }
        return (T) this;
    }

    @Override
    public int size() {
        return getSelected().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setContent(CharSequence content) {
        for (AbstractNode<S> node : getSelected()) {
            node.setContent(content);
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setContent(CharSequence content, @Nonnull ContentEscapeMode escapeMode) {
        for (AbstractNode<S> node : getSelected()) {
            node.setContent(content, escapeMode);
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T addNodeAttachment(@Nonnull NodeAttachment<? extends S> nodeAttachment) {
        for (AbstractNode<S> node : getSelected()) {
            node.addNodeAttachment(nodeAttachment);
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T removeNodeAttachment(@Nonnull NodeAttachment<? extends S> nodeAttachment) {
        for (AbstractNode<S> node : getSelected()) {
            node.removeNodeAttachment(nodeAttachment);
        }
        return (T) this;
    }

    @Override
    public S get(int index) {
        if (index < 0 || index >= selected.size()) {
            throw new IndexOutOfBoundsException();
        }
        return selected.get(index);
    }

    @Override
    public NodeSelection<?, S> get(int from, int to) {
        if (from < 0 || to < 0 || from >= selected.size() || to >= selected.size()) {
            throw new IndexOutOfBoundsException();
        }
        List<S> subSelection = new LinkedList<S>();
        for (int i = from; i < to; i++) {
            subSelection.add(getSelected().get(i));
        }
        return new NodeSelection<NodeSelection<?, S>, S>(subSelection);
    }

    @Override
    public ElementNodeSelection reduceByTag(@Nonnull CharSequence tagName) {
        return new ElementNodeSelection(this.reduceByFilter(new TagNameFilter(tagName)));
    }

    @Override
    public ElementNodeSelection reduceByAttr(@Nonnull CharSequence key) {
        return new ElementNodeSelection(this.reduceByFilter(new AttributeKeyExistenceFilter(key)));
    }

    @Override
    public ElementNodeSelection reduceByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode) {
        return new ElementNodeSelection(this.reduceByFilter(new AttributeKeyValueFilter(key, value, queryMatchMode)));
    }

    @Override
    public <V extends AbstractNode<V>, U extends NodeSelection<U, V>> NodeSelection<U, V> reduceByFilter(@Nonnull INodeFilter<V> nodeFilter) {
        Class<V> filterArgumentClass = NodeFilterSupport.getInstance().findFilterParameterClass(nodeFilter);
        List<V> foundNodes = new ArrayList<V>();
        for (S node : getSelected()) {
            if (!filterArgumentClass.isAssignableFrom(filterArgumentClass)) {
                continue;
            }
            V castNode = filterArgumentClass.cast(node);
            if (nodeFilter.filter(castNode)) {
                foundNodes.add(castNode);
            }
        }
        return new NodeSelection<U, V>(foundNodes);
    }

    @Override
    public TextNodeSelection reduceToText() {
        return new TextNodeSelection(this.reduceByFilter(new TextNodeFilter()));
    }

    @Override
    public ElementNodeSelection reduceToElement() {
        return new ElementNodeSelection(this.reduceByFilter(new ElementNodeFilter()));
    }

    @Override
    public Iterator<S> iterator() {
        return new Iterator<S>() {
            private int iterationCount = 0;

            @Override
            public boolean hasNext() {
                return iterationCount < getSelected().size();
            }

            @Override
            public S next() {
                return getSelected().get(iterationCount++);
            }

            @Override
            public void remove() {
                throw new IllegalStateException();
            }
        };
    }
}
