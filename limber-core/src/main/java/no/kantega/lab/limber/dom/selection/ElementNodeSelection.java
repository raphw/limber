package no.kantega.lab.limber.dom.selection;


import no.kantega.lab.limber.dom.abstraction.IDomElementNodeRepresentable;
import no.kantega.lab.limber.dom.ajax.AjaxEventTrigger;
import no.kantega.lab.limber.dom.ajax.IAjaxCallback;
import no.kantega.lab.limber.dom.element.*;
import no.kantega.lab.limber.dom.filter.*;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ElementNodeSelection<N extends ElementNode<? extends N>, S extends ElementNodeSelection<N, ?>>
        extends NodeSelection<N, S>
        implements IElementNodeSelection<N> {

    public ElementNodeSelection(@Nonnull List<? extends N> selected) {
        super(selected);
    }

    public ElementNodeSelection(@Nonnull INodeSelection<? extends N> that) {
        super(that);
    }

    public ElementNodeSelection(@Nonnull LinkedHashSet<? extends N> selected) {
        super(selected);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setTagName(@Nonnull CharSequence tagName) {
        for (N elementNode : getSelected()) {
            elementNode.setTagName(tagName);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>> NodeSelection<N2, ?> addChild(int index, @Nonnull N2 prototype) {
        List<N2> addedChildren = new ArrayList<N2>();
        for (N elementNode : getSelected()) {
            addedChildren.add(elementNode.addChild(index, prototype.clone()));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(addedChildren);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S addChildAndStay(int index, @Nonnull AbstractNode<?> prototype) {
        addChild(index, prototype);
        return (S) this;
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>> NodeSelection<N2, ?> appendChild(@Nonnull N2 prototype) {
        List<N2> addedChildren = new ArrayList<N2>();
        for (N elementNode : getSelected()) {
            addedChildren.add(elementNode.appendChild(prototype.clone()));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(addedChildren);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S appendChildAndStay(@Nonnull AbstractNode<?> prototype) {
        appendChild(prototype);
        return (S) this;
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>> NodeSelection<N2, ?> prependChild(@Nonnull N2 prototype) {
        List<N2> addedChildren = new ArrayList<N2>();
        for (N elementNode : getSelected()) {
            addedChildren.add(elementNode.prependChild(prototype.clone()));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(addedChildren);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S prependChildAndStay(@Nonnull AbstractNode<?> prototype) {
        prependChild(prototype);
        return (S) this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection<ElementNode<?>, ?> addChild(int index, @Nonnull CharSequence tagName) {
        return new ElementNodeSelection<ElementNode<?>, ElementNodeSelection<ElementNode<?>, ?>>(addChild(index, ElementNodeFactory.getInstance().make(tagName)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S addChildAndStay(int index, @Nonnull CharSequence tagName) {
        addChild(index, tagName);
        return (S) this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection<ElementNode<?>, ?> appendChild(@Nonnull CharSequence tagName) {
        return new ElementNodeSelection<ElementNode<?>, ElementNodeSelection<ElementNode<?>, ?>>(appendChild(ElementNodeFactory.getInstance().make(tagName)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S appendChildAndStay(@Nonnull CharSequence tagName) {
        appendChild(tagName);
        return (S) this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection<ElementNode<?>, ?> prependChild(@Nonnull CharSequence tagName) {
        return new ElementNodeSelection<ElementNode<?>, ElementNodeSelection<ElementNode<?>, ?>>(appendChild(ElementNodeFactory.getInstance().make(tagName)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S prependChildAndStay(@Nonnull CharSequence tagName) {
        for (N elementNode : getSelected()) {
            elementNode.prependChildAndStay(tagName);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    public TextNodeSelection addText(int index, @Nonnull CharSequence text) {
        return new TextNodeSelection(addChild(index, new TextNode(text)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S addTextAndStay(int index, @Nonnull CharSequence text) {
        addText(index, text);
        return (S) this;
    }

    @Nonnull
    @Override
    public TextNodeSelection addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        return new TextNodeSelection(addChild(index, new TextNode(text, contentEscapeStrategy)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        addText(index, text, contentEscapeStrategy);
        return (S) this;
    }

    @Nonnull
    @Override
    public TextNodeSelection appendText(@Nonnull CharSequence text) {
        return new TextNodeSelection(appendChild(new TextNode(text)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S appendTextAndStay(@Nonnull CharSequence text) {
        appendText(text);
        return (S) this;
    }

    @Nonnull
    @Override
    public TextNodeSelection appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        return new TextNodeSelection(appendChild(new TextNode(text, contentEscapeStrategy)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        appendText(text, contentEscapeStrategy);
        return (S) this;
    }

    @Nonnull
    @Override
    public TextNodeSelection prependText(@Nonnull CharSequence text) {
        return new TextNodeSelection(prependChild(new TextNode(text)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S prependTextAndStay(@Nonnull CharSequence text) {
        prependText(text);
        return (S) this;
    }

    @Nonnull
    @Override
    public TextNodeSelection prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        return new TextNodeSelection(prependChild(new TextNode(text, contentEscapeStrategy)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        prependText(text, contentEscapeStrategy);
        return (S) this;
    }

    @Nonnull
    @Override
    public <N2 extends ElementNode<? extends N2>> ElementNodeSelection<N2, ?> wrap(@Nonnull N2 prototype) {
        List<N2> nodes = new ArrayList<N2>();
        for (N element : getSelected()) {
            nodes.add(element.wrap(prototype.clone()));
        }
        return new ElementNodeSelection<N2, ElementNodeSelection<N2, ?>>(nodes);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S wrapAndStay(@Nonnull ElementNode prototype) {
        wrap(prototype);
        return (S) this;
    }

    @Nonnull
    @Override
    public IDomElementNodeRepresentable<?> wrap(@Nonnull CharSequence tagName) {
        return wrap(ElementNodeFactory.getInstance().make(tagName));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S wrapAndStay(@Nonnull CharSequence tagName) {
        wrap(tagName);
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S putAttr(@Nonnull CharSequence key, CharSequence value) {
        for (N elementNode : getSelected()) {
            elementNode.putAttr(key, value);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setId(@Nonnull CharSequence id) {
        for (N elementNode : getSelected()) {
            elementNode.setId(id);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setRandomId() {
        for (N elementNode : getSelected()) {
            elementNode.setRandomId();
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setRandomIdIfNone() {
        for (N elementNode : getSelected()) {
            elementNode.setRandomIdIfNone();
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S removeId() {
        for (N elementNode : getSelected()) {
            elementNode.removeId();
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S removeAttr(@Nonnull CharSequence key) {
        for (N elementNode : getSelected()) {
            elementNode.removeAttr(key);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S addCssClass(@Nonnull CharSequence cssClassName) {
        for (N elementNode : getSelected()) {
            elementNode.addCssClass(cssClassName);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames) {
        for (N elementNode : getSelected()) {
            elementNode.setCssClasses(cssClassNames);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S removeCssClass(@Nonnull CharSequence cssClassName) {
        for (N elementNode : getSelected()) {
            elementNode.removeCssClass(cssClassName);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue) {
        for (N elementNode : getSelected()) {
            elementNode.addCssStyle(styleKey, styleValue);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles) {
        for (N elementNode : getSelected()) {
            elementNode.setCssStyles(cssStyles);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S removeCssStyle(@Nonnull CharSequence styleKey) {
        for (N elementNode : getSelected()) {
            elementNode.removeCssStyle(styleKey);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection<N, S> get(int from, int to) {
        return new ElementNodeSelection<N, S>(super.get(from, to));
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findByTag(@Nonnull CharSequence tagName) {
        return findByTag(tagName, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findByTag(@Nonnull CharSequence tagName, int maxDepth) {
        return new ElementNodeSelection<ElementNode<?>, ElementNodeSelection<ElementNode<?>, ?>>(findByFilter(new TagNameFilter<ElementNode<?>>(tagName), ElementNode.class, maxDepth));
    }

    @Override
    public ElementNode<?> findById(@Nonnull CharSequence id) {
        return findById(id, Integer.MAX_VALUE);
    }

    @Override
    public ElementNode<?> findById(@Nonnull CharSequence id, int maxDepth) {
        ElementNodeSelection<?, ?> elementNodeSelection = findByAttr("id", id, QueryMatchMode.FULL_MATCH, maxDepth);
        if (elementNodeSelection.size() == 0) {
            return null;
        } else if (elementNodeSelection.size() > 1) {
            throw new IllegalStateException();
        } else {
            return elementNodeSelection.get(0);
        }
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findByCssClass(@Nonnull CharSequence cssClassName) {
        return findByCssClass(cssClassName, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findByCssClass(@Nonnull CharSequence cssClassName, int maxDepth) {
        return new ElementNodeSelection<ElementNode<?>, ElementNodeSelection<ElementNode<?>, ?>>(findByFilter(new CssClassNameFilter(cssClassName), ElementNode.class, maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findByAttr(@Nonnull CharSequence key) {
        return findByAttr(key, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findByAttr(@Nonnull CharSequence key, int maxDepth) {
        return new ElementNodeSelection<ElementNode<?>, ElementNodeSelection<ElementNode<?>, ?>>(findByFilter(new AttributeKeyExistenceFilter(key), ElementNode.class, maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode) {
        return findByAttr(key, value, queryMatchMode, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode, int maxDepth) {
        return new ElementNodeSelection<ElementNode<?>, ElementNodeSelection<ElementNode<?>, ?>>(findByFilter(new AttributeKeyValueFilter(key, value, queryMatchMode), ElementNode.class, maxDepth));
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> getChildren() {
        return getChildren(new BooleanRepeaterFilter<AbstractNode<?>>(true));
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> getChildren(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter) {
        return getChildren(nodeFilter, AbstractNode.class);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> getChildren(@Nonnull INodeFilter<N2> nodeFilter, Class<N3> filterBoundary) {
        LinkedHashSet<N2> resultSelection = new LinkedHashSet<N2>();
        for (N elementNode : getSelected()) {
            resultSelection.addAll(NodeFilterSupport.getInstance().filterNodeList(elementNode.getChildren().getSelected(), nodeFilter, filterBoundary));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(resultSelection);
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> findByFilter(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter) {
        return findByFilter(nodeFilter, AbstractNode.class);
    }

    @Nonnull
    @Override
    public NodeSelection<?, ?> findByFilter(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter, int maxDepth) {
        return findByFilter(nodeFilter, AbstractNode.class, maxDepth);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<N3> filterBoundary) {
        return findByFilter(nodeFilter, filterBoundary, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<N3> filterBoundary, int maxDepth) {
        LinkedHashSet<N2> resultSelection = new LinkedHashSet<N2>();
        for (N elementNode : getSelected()) {
            resultSelection.addAll(NodeFilterSupport.getInstance().filterNodeTree(elementNode, nodeFilter, filterBoundary, maxDepth));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(resultSelection);
    }

    @Nonnull
    @Override
    public TextNodeSelection findTextNodes() {
        return findTextNodes(Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public TextNodeSelection findTextNodes(int maxDepth) {
        return new TextNodeSelection(findByFilter(new BooleanRepeaterFilter<TextNode>(true), TextNode.class, maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findElements() {
        return findElements(Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection<?, ?> findElements(int maxDepth) {
        return new ElementNodeSelection<ElementNode<?>, ElementNodeSelection<ElementNode<?>, ?>>(findByFilter(new BooleanRepeaterFilter<ElementNode<?>>(true), ElementNode.class, maxDepth));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S addAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger, @Nonnull IAjaxCallback<? super N> ajaxCallback) {
        for (N elementNode : getSelected()) {
            elementNode.addAjaxEvent(ajaxEventTrigger, ajaxCallback);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S removeAjaxEvent(@Nonnull IAjaxCallback<? super N> ajaxCallback) {
        for (N elementNode : getSelected()) {
            elementNode.removeAjaxEvent(ajaxCallback);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S removeAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger) {
        for (N elementNode : getSelected()) {
            elementNode.removeAjaxEvent(ajaxEventTrigger);
        }
        return (S) this;
    }
}
