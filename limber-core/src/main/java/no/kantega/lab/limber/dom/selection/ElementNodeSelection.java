package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomElementBrowsable;
import no.kantega.lab.limber.dom.abstraction.IDomElementMorphable;
import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.*;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ElementNodeSelection extends NodeSelection<ElementNode, ElementNodeSelection>
        implements IDomElementMorphable<ElementNodeSelection>, IDomElementBrowsable<ElementNode, ElementNodeSelection> {

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
    public ElementNodeSelection addChildAndStay(int index, @Nonnull AbstractNode<?> prototype) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addChildAndStay(index, prototype.clone());
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection appendChildAndStay(@Nonnull AbstractNode<?> prototype) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.appendChildAndStay(prototype.clone());
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection prependChildAndStay(@Nonnull AbstractNode<?> prototype) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.prependChildAndStay(prototype.clone());
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection addChildAndStay(int index, @Nonnull CharSequence tagName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addChildAndStay(index, tagName);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection appendChildAndStay(@Nonnull CharSequence tagName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.appendChildAndStay(tagName);
        }
        return this;
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
    public ElementNodeSelection addTextAndStay(int index, @Nonnull CharSequence text) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addTextAndStay(index, text);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addTextAndStay(index, text, contentEscapeMode);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection appendTextAndStay(@Nonnull CharSequence text) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.appendTextAndStay(text);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.appendTextAndStay(text, contentEscapeMode);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection prependTextAndStay(@Nonnull CharSequence text) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.prependTextAndStay(text);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNodeSelection prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.prependTextAndStay(text, contentEscapeMode);
        }
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
        return new ElementNodeSelection(findByFilter(new TagNameFilter(tagName), maxDepth));
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
        return new ElementNodeSelection(findByFilter(new CssClassNameFilter(cssClassName), maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key) {
        return findByAttr(key, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyExistenceFilter(key), maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode) {
        return findByAttr(key, value, queryMatchMode, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyValueFilter(key, value, queryMatchMode), maxDepth));
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<?>> NodeSelection<N2, ?> getChildren() {
        return getChildren(new BooleanRepeaterFilter<N2>(true));
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<?>> NodeSelection<N2, ?> getChildren(@Nonnull INodeFilter<N2> nodeFilter) {
        LinkedHashSet<N2> resultSelection = new LinkedHashSet<N2>();
        for (ElementNode elementNode : getSelected()) {
            resultSelection.addAll(NodeFilterSupport.getInstance().filterNodeList(elementNode.getChildren().getSelected(), nodeFilter));
        }
        return new NodeSelection<N2, NodeSelection<N2, ?>>(resultSelection);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> findByFilter(@Nonnull INodeFilter<N2> nodeFilter) {
        return findByFilter(nodeFilter, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, int maxDepth) {
        LinkedHashSet<N2> resultSelection = new LinkedHashSet<N2>();
        for (ElementNode elementNode : getSelected()) {
            resultSelection.addAll(NodeFilterSupport.getInstance().filterBreadthFirst(elementNode, nodeFilter, maxDepth));
        }
        return new NodeSelection<N2, C2>(resultSelection);
    }

    @Nonnull
    @Override
    public TextNodeSelection findTextNodes() {
        return findTextNodes(Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public TextNodeSelection findTextNodes(int maxDepth) {
        return new TextNodeSelection(findByFilter(new TextNodeFilter(), maxDepth));
    }

    @Nonnull
    @Override
    public ElementNodeSelection findElements() {
        return findElements(Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection findElements(int maxDepth) {
        return new ElementNodeSelection(findByFilter(new ElementNodeFilter(), maxDepth));
    }
}
