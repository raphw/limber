package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomElementBrowsable;
import no.kantega.lab.limber.dom.abstraction.IDomElementMorphable;
import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.*;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ElementNodeSelection extends NodeSelection<ElementNode, ElementNodeSelection>
        implements IDomElementMorphable<ElementNodeSelection>, IDomElementBrowsable<ElementNode> {

    public ElementNodeSelection(@Nonnull List<ElementNode> selected) {
        super(selected);
    }

    public ElementNodeSelection(@Nonnull NodeSelection<ElementNode, ?> that) {
        super(that);
    }

    @Override
    public ElementNodeSelection setTagName(@Nonnull CharSequence tagName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setTagName(tagName);
        }
        return this;
    }

    @Override
    public ElementNodeSelection addChild(int index, @Nonnull AbstractNode<?> prototype) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addChild(index, prototype.clone());
        }
        return this;
    }

    @Override
    public ElementNodeSelection appendChild(@Nonnull AbstractNode<?> prototype) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.appendChild(prototype.clone());
        }
        return this;
    }

    @Override
    public ElementNodeSelection prependChild(@Nonnull AbstractNode<?> prototype) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.prependChild(prototype.clone());
        }
        return this;
    }

    @Override
    public ElementNodeSelection addChild(int index, @Nonnull CharSequence tagName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addChild(index, tagName);
        }
        return this;
    }

    @Override
    public ElementNodeSelection appendChild(@Nonnull CharSequence tagName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.appendChild(tagName);
        }
        return this;
    }

    @Override
    public ElementNodeSelection prependChild(@Nonnull CharSequence tagName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.prependChild(tagName);
        }
        return this;
    }

    @Override
    public ElementNodeSelection addText(int index, @Nonnull CharSequence text) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addText(index, text);
        }
        return this;
    }

    @Override
    public ElementNodeSelection addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addText(index, text, contentEscapeMode);
        }
        return this;
    }

    @Override
    public ElementNodeSelection appendText(@Nonnull CharSequence text) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.appendText(text);
        }
        return this;
    }

    @Override
    public ElementNodeSelection appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.appendText(text, contentEscapeMode);
        }
        return this;
    }

    @Override
    public ElementNodeSelection prependText(@Nonnull CharSequence text) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.prependText(text);
        }
        return this;
    }

    @Override
    public ElementNodeSelection prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.prependText(text, contentEscapeMode);
        }
        return this;
    }

    @Override
    public ElementNodeSelection putAttr(@Nonnull CharSequence key, CharSequence value) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.putAttr(key, value);
        }
        return this;
    }

    @Override
    public ElementNodeSelection setId(@Nonnull CharSequence id) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setId(id);
        }
        return this;
    }

    @Override
    public ElementNodeSelection setRandomId() {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setRandomId();
        }
        return this;
    }

    @Override
    public ElementNodeSelection setRandomIdIfNone() {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setRandomIdIfNone();
        }
        return this;
    }

    @Override
    public ElementNodeSelection removeId() {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeId();
        }
        return this;
    }

    @Override
    public ElementNodeSelection removeAttr(@Nonnull CharSequence key) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeAttr(key);
        }
        return this;
    }

    @Override
    public ElementNodeSelection addCssClass(@Nonnull CharSequence cssClassName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addCssClass(cssClassName);
        }
        return this;
    }

    @Override
    public ElementNodeSelection setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setCssClasses(cssClassNames);
        }
        return this;
    }

    @Override
    public ElementNodeSelection removeCssClass(@Nonnull CharSequence cssClassName) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeCssClass(cssClassName);
        }
        return this;
    }

    @Override
    public ElementNodeSelection addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.addCssStyle(styleKey, styleValue);
        }
        return this;
    }

    @Override
    public ElementNodeSelection setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.setCssStyles(cssStyles);
        }
        return this;
    }

    @Override
    public ElementNodeSelection removeCssStyle(@Nonnull CharSequence styleKey) {
        for (ElementNode elementNode : getSelected()) {
            elementNode.removeCssStyle(styleKey);
        }
        return this;
    }

    @Override
    public ElementNodeSelection get(int from, int to) {
        return new ElementNodeSelection(super.get(from, to));
    }

    @Override
    public ElementNodeSelection findByTag(@Nonnull CharSequence tagName) {
        return findByTag(tagName, Integer.MAX_VALUE);
    }

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

    @Override
    public ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName) {
        return findByCssClass(cssClassName, Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new CssClassNameFilter(cssClassName), maxDepth));
    }

    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key) {
        return findByAttr(key, Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyExistenceFilter(key), maxDepth));
    }

    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode) {
        return findByAttr(key, value, queryMatchMode, Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findByAttr(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyValueFilter(key, value, queryMatchMode), maxDepth));
    }

    @Override
    public <N2 extends AbstractNode<N2>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> findByFilter(@Nonnull INodeFilter<N2> nodeFilter) {
        return findByFilter(nodeFilter, Integer.MAX_VALUE);
    }

    @Override
    public <N2 extends AbstractNode<N2>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, int maxDepth) {
        List<N2> resultSelection = new LinkedList<N2>();
        for (ElementNode elementNode : getSelected()) {
            resultSelection.addAll(NodeFilterSupport.getInstance().filter(elementNode, nodeFilter, maxDepth));
        }
        return new NodeSelection<N2, C2>(resultSelection);
    }

    @Override
    public TextNodeSelection findTextNodes() {
        return findTextNodes(Integer.MAX_VALUE);
    }

    @Override
    public TextNodeSelection findTextNodes(int maxDepth) {
        return new TextNodeSelection(findByFilter(new TextNodeFilter(), maxDepth));
    }

    @Override
    public ElementNodeSelection findElements() {
        return findElements(Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findElements(int maxDepth) {
        return new ElementNodeSelection(findByFilter(new ElementNodeFilter(), maxDepth));
    }
}
