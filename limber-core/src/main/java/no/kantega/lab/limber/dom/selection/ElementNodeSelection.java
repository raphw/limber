package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.abstraction.IDomElementNodeRepresentable;
import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.TextNode;
import no.kantega.lab.limber.dom.filter.*;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ElementNodeSelection extends NodeSelection<ElementNode, ElementNodeSelection> implements IDomElementNodeRepresentable {

    public ElementNodeSelection(@Nonnull List<ElementNode> selected) {
        super(selected);
    }

    public ElementNodeSelection(@Nonnull NodeSelection<ElementNode, ?> that) {
        super(that);
    }

    public ElementNodeSelection(@Nonnull LinkedHashSet<ElementNode> selected) {
        super(selected);
    }

    @Nonnull
    @Override
    public ElementNodeSelection setTagName(@Nonnull CharSequence tagName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setTagName(tagName);
        }
        return this;
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>> NodeSelection<N2, ?> addChild(int index, @Nonnull N2 prototype) {
        List<N2> addedChildren = new ArrayList<N2>();
        for (ElementNode elementNode : getSelected()) {
            addedChildren.add(elementNode.addChild(index, prototype.clone()));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(addedChildren);
    }

    @Nonnull
    @Override
    public ElementNodeSelection addChildAndStay(int index, @Nonnull AbstractNode<?> prototype) {
        addChild(index, prototype);
        return this;
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>> NodeSelection<N2, ?> appendChild(@Nonnull N2 prototype) {
        List<N2> addedChildren = new ArrayList<N2>();
        for (ElementNode elementNode : getSelected()) {
            addedChildren.add(elementNode.appendChild(prototype.clone()));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(addedChildren);
    }

    @Nonnull
    @Override
    public ElementNodeSelection appendChildAndStay(@Nonnull AbstractNode<?> prototype) {
        appendChild(prototype);
        return this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <N2 extends AbstractNode<? extends N2>> NodeSelection<N2, ?> prependChild(@Nonnull N2 prototype) {
        List<N2> addedChildren = new ArrayList<N2>();
        for (ElementNode elementNode : getSelected()) {
            addedChildren.add(elementNode.prependChild(prototype.clone()));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(addedChildren);
    }

    @Nonnull
    @Override
    public ElementNodeSelection prependChildAndStay(@Nonnull AbstractNode<?> prototype) {
        prependChild(prototype);
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection addChild(int index, @Nonnull CharSequence tagName) {
        return new ElementNodeSelection(addChild(index, new ElementNode(tagName)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection addChildAndStay(int index, @Nonnull CharSequence tagName) {
        addChild(index, tagName);
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection appendChild(@Nonnull CharSequence tagName) {
        return new ElementNodeSelection(appendChild(new ElementNode(tagName)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection appendChildAndStay(@Nonnull CharSequence tagName) {
        appendChild(tagName);
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection prependChild(@Nonnull CharSequence tagName) {
        return new ElementNodeSelection(appendChild(new ElementNode(tagName)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection prependChildAndStay(@Nonnull CharSequence tagName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.prependChildAndStay(tagName);
        }
        return this;
    }

    @Nonnull
    @Override
    public TextNodeSelection addText(int index, @Nonnull CharSequence text) {
        return new TextNodeSelection(addChild(index, new TextNode(text)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection addTextAndStay(int index, @Nonnull CharSequence text) {
        addText(index, text);
        return this;
    }

    @Nonnull
    @Override
    public TextNodeSelection addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        return new TextNodeSelection(addChild(index, new TextNode(text, contentEscapeMode)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        addText(index, text, contentEscapeMode);
        return this;
    }

    @Nonnull
    @Override
    public TextNodeSelection appendText(@Nonnull CharSequence text) {
        return new TextNodeSelection(appendChild(new TextNode(text)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection appendTextAndStay(@Nonnull CharSequence text) {
        appendText(text);
        return this;
    }

    @Nonnull
    @Override
    public TextNodeSelection appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        return new TextNodeSelection(appendChild(new TextNode(text, contentEscapeMode)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        appendText(text, contentEscapeMode);
        return this;
    }

    @Nonnull
    @Override
    public Object prependText(@Nonnull CharSequence text) {
        return new TextNodeSelection(prependChild(new TextNode(text)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection prependTextAndStay(@Nonnull CharSequence text) {
        prependText(text);
        return this;
    }

    @Nonnull
    @Override
    public Object prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        return new TextNodeSelection(prependChild(new TextNode(text, contentEscapeMode)));
    }

    @Nonnull
    @Override
    public ElementNodeSelection prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        prependText(text, contentEscapeMode);
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection wrap(@Nonnull ElementNode prototype) {
        List<ElementNode> nodes = new ArrayList<ElementNode>();
        for (ElementNode element : getSelected()) {
            nodes.add(element.wrap(prototype));
        }
        return new ElementNodeSelection(nodes);
    }

    @Nonnull
    @Override
    public ElementNodeSelection wrapAndStay(@Nonnull ElementNode prototype) {
        wrap(prototype);
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection wrap(@Nonnull CharSequence tagName) {
        return wrap(new ElementNode(tagName));
    }

    @Nonnull
    @Override
    public ElementNodeSelection wrapAndStay(@Nonnull CharSequence tagName) {
        wrap(tagName);
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection putAttr(@Nonnull CharSequence key, CharSequence value) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.putAttr(key, value);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection setId(@Nonnull CharSequence id) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setId(id);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection setRandomId() {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setRandomId();
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection setRandomIdIfNone() {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setRandomIdIfNone();
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection removeId() {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeId();
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection removeAttr(@Nonnull CharSequence key) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeAttr(key);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection addCssClass(@Nonnull CharSequence cssClassName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addCssClass(cssClassName);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setCssClasses(cssClassNames);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection removeCssClass(@Nonnull CharSequence cssClassName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeCssClass(cssClassName);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addCssStyle(styleKey, styleValue);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setCssStyles(cssStyles);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection removeCssStyle(@Nonnull CharSequence styleKey) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeCssStyle(styleKey);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection get(int from, int to) {
        return new ElementNodeSelection(super.get(from, to));
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByTag(@Nonnull CharSequence tagName) {
        return findByTag(tagName, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByTag(@Nonnull CharSequence tagName, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new TagNameFilter(tagName), ElementNode.class, maxDepth));
    }

    @Override
    public ElementNode findById(@Nonnull CharSequence id) {
        return findById(id, Integer.MAX_VALUE);
    }

    @Override
    public ElementNode findById(@Nonnull CharSequence id, int maxDepth) {
        ElementNodeSelection elementNodeSelection = findByAttr("id", id, QueryMatchMode.FULL_MATCH, maxDepth);
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
    public ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName) {
        return findByCssClass(cssClassName, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new CssClassNameFilter(cssClassName), ElementNode.class, maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key) {
        return findByAttr(key, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyExistenceFilter(key), ElementNode.class, maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode) {
        return findByAttr(key, value, queryMatchMode, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyValueFilter(key, value, queryMatchMode), ElementNode.class, maxDepth));
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
    public <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> getChildren(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N3> filterBoundary) {
        LinkedHashSet<N2> resultSelection = new LinkedHashSet<N2>();
        for (ElementNode elementNode : getSelected()) {
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
        return findByFilter(nodeFilter, AbstractNode.class, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<? extends N3> filterBoundary) {
        return findByFilter(nodeFilter, filterBoundary, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>, N3 extends N2> NodeSelection<N2, ?> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<? extends N3> filterBoundary, int maxDepth) {
        LinkedHashSet<N2> resultSelection = new LinkedHashSet<N2>();
        for (ElementNode elementNode : getSelected()) {
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
        return new TextNodeSelection(findByFilter(new TextNodeFilter(), TextNode.class, maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection findElements() {
        return findElements(Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection findElements(int maxDepth) {
        return new ElementNodeSelection(findByFilter(new ElementNodeFilter(), ElementNode.class, maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection addAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger, @Nonnull IAjaxCallback<? super ElementNode> ajaxCallback) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addAjaxEvent(ajaxEventTrigger, ajaxCallback);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection removeAjaxEvent(@Nonnull IAjaxCallback<?> ajaxCallback) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeAjaxEvent(ajaxCallback);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection removeAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeAjaxEvent(ajaxEventTrigger);
        }
        return this;
    }
}
