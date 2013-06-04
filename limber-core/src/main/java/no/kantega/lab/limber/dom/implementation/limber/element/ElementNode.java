package no.kantega.lab.limber.dom.implementation.limber.element;

import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomElementBrowsable;
import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomElementMorphable;
import no.kantega.lab.limber.dom.implementation.limber.filter.*;
import no.kantega.lab.limber.dom.implementation.limber.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.NodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.TextNodeSelection;

import java.util.*;

public class ElementNode extends AbstractNode<ElementNode>
        implements IDomElementMorphable<ElementNode>, IDomElementBrowsable {

    private String tagName;

    private Map<CharSequence, String> attr;

    private List<AbstractNode<?>> children;

    public ElementNode(CharSequence tag) {
        setTagName(tag);
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public ElementNode setTagName(CharSequence tagName) {
        this.tagName = tagName.toString();
        return this;
    }

    @Override
    public ElementNode addChild(int index, AbstractNode<?> node) {
        if (index < 0 || index > (children == null ? 0 : children.size())) {
            throw new IllegalArgumentException();
        }
        if (children == null) children = new ArrayList<AbstractNode<?>>();
        children.add(index, node);
        return this;
    }

    @Override
    public ElementNode prependChild(AbstractNode<?> node) {
        return addChild(0, node);
    }

    @Override
    public ElementNode appendChild(AbstractNode<?> node) {
        return addChild(children == null ? 0 : children.size(), node);
    }

    protected void removeChild(AbstractNode node) {
        if (children == null) return;
        children.remove(node);
        if (children.size() == 0) children = null;
    }

    public List<AbstractNode<?>> children() {
        if (children == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(children);
        }
    }

    public int size() {
        if (children == null) {
            return 0;
        } else {
            return children.size();
        }
    }

    public String getAttr(CharSequence key) {
        if (attr == null) return null;
        return attr.get(key);
    }

    @Override
    public ElementNode putAttr(CharSequence key, CharSequence value) {
        if (value == null) {
            removeAttr(key);
            return this;
        }
        if (attr == null) attr = new HashMap<CharSequence, String>();
        attr.put(key, value.toString());
        return this;
    }

    @Override
    public ElementNode removeAttr(CharSequence key) {
        if (attr == null) return this;
        attr.remove(key);
        if (attr.size() == 0) attr = null;
        return this;
    }

    public Set<CharSequence> attrs() {
        if (attr == null) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(attr.keySet());
        }
    }

    @Override
    public ElementNode clear() {
        if (children == null) return this;
        children.clear();
        children = null;
        return this;
    }

    @Override
    public ElementNodeSelection findByTag(CharSequence tagName) {
        return new ElementNodeSelection(findByFilter(new TagNameFilter(tagName)));

    }

    @Override
    public ElementNodeSelection findByAttr(CharSequence key) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyExistenceFilter(key)));
    }

    @Override
    public ElementNodeSelection findByAttr(CharSequence key, CharSequence value, FilterMatchMode filterMatchMode) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyValueFilter(key, value, filterMatchMode)));
    }

    @Override
    public <S extends AbstractNode<S>, U extends NodeSelection<U, S>> NodeSelection<? extends U, S> findByFilter(AbstractNodeFilter<S> nodeFilter) {
        return new NodeSelection<U, S>(DomTreeBrowserHelper.getInstance().filter(this, nodeFilter, Integer.MAX_VALUE));
    }

    @Override
    public TextNodeSelection findText() {
        return new TextNodeSelection(findByFilter(new TextNodeFilter()));
    }

    @Override
    public ElementNodeSelection findElement() {
        return new ElementNodeSelection(findByFilter(new ElementNodeFilter()));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ElementNode[");
        stringBuilder.append(isRender());
        stringBuilder.append(',');
        stringBuilder.append(getTagName());
        if (attr != null) {
            for (Map.Entry<CharSequence, String> entry : attr.entrySet()) {
                stringBuilder.append(',');
                stringBuilder.append(entry.getKey()).append('=').append(entry.getValue());
            }
        }
        for (AbstractNode<?> node : children()) {
            stringBuilder.append('\n');
            stringBuilder.append(node.toString());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
