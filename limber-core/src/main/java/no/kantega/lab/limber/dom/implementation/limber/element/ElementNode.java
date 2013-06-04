package no.kantega.lab.limber.dom.implementation.limber.element;

import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomElementBrowsable;
import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomElementMorphable;
import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomElementQueryable;
import no.kantega.lab.limber.dom.implementation.limber.filter.*;
import no.kantega.lab.limber.dom.implementation.limber.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.NodeSelection;
import no.kantega.lab.limber.dom.implementation.limber.selection.TextNodeSelection;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ElementNode extends AbstractNode<ElementNode>
        implements IDomElementMorphable<ElementNode>, IDomElementBrowsable, IDomElementQueryable {

    private static final String HTML_ATTR_CLASS = "class";
    private static final String HTML_ATTR_STYLE = "style";
    private static final String HTML_SEPARATOR_CLASS = "( )+";
    private static final String HTML_SEPARATOR_CLASS_REGEX = "(" + HTML_SEPARATOR_CLASS + ")+";
    private static final String HTML_SEPARATOR_STYLE_ARRAY = ":";
    private static final String HTML_SEPARATOR_STYLE_ARRAY_REGEX = "( )*[" + HTML_SEPARATOR_STYLE_ARRAY + "]+[" + HTML_SEPARATOR_STYLE_ARRAY + " ]*";
    private static final char HTML_SEPARATOR_STYLE_VALUE = ';';

    private String tagName;

    private Map<CharSequence, String> attr;

    private List<AbstractNode<?>> children;

    public ElementNode(CharSequence tag) {
        setTagName(tag);
    }

    @Override
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

    @Override
    public List<AbstractNode<?>> children() {
        if (children == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(children);
        }
    }

    @Override
    public int size() {
        if (children == null) {
            return 0;
        } else {
            return children.size();
        }
    }

    @Override
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

    @Override
    public Set<CharSequence> attrs() {
        if (attr == null) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(attr.keySet());
        }
    }

    @Override
    public ElementNode addCssClass(CharSequence cssClassName) {
        String cssClassAttributeValue = getAttr(HTML_ATTR_CLASS);
        List<String> currentCssClasses = Arrays.asList(cssClassAttributeValue.split(HTML_SEPARATOR_CLASS_REGEX));
        if (!currentCssClasses.contains(cssClassAttributeValue)) {
            currentCssClasses.add(cssClassName.toString());
            cssClassAttributeValue = StringUtils.join(currentCssClasses, HTML_SEPARATOR_CLASS);
            putAttr(HTML_ATTR_CLASS, cssClassAttributeValue);
        }
        return this;
    }

    @Override
    public ElementNode removeCssClass(CharSequence cssClassName) {
        String cssClassAttributeValue = getAttr(HTML_ATTR_CLASS);
        List<String> currentCssClasses = Arrays.asList(cssClassAttributeValue.split(HTML_SEPARATOR_CLASS_REGEX));
        if (currentCssClasses.remove(cssClassAttributeValue)) {
            cssClassAttributeValue = StringUtils.join(currentCssClasses, HTML_SEPARATOR_CLASS);
            putAttr(HTML_ATTR_CLASS, cssClassAttributeValue);
        }
        return this;
    }

    @Override
    public ElementNode addCssStyle(CharSequence styleKey, CharSequence styleValue) {
        List<String> currentCssStyles = removeCssStyleHelper(styleKey);
        currentCssStyles.add(String.format("%s%s%s", styleKey, HTML_SEPARATOR_STYLE_VALUE, styleValue));
        String cssClassAttributeValue = StringUtils.join(currentCssStyles, HTML_SEPARATOR_STYLE_ARRAY);
        putAttr(HTML_ATTR_STYLE, cssClassAttributeValue);
        return this;
    }

    @Override
    public ElementNode removeCssStyle(CharSequence styleKey) {
        List<String> currentCssStyles = removeCssStyleHelper(styleKey);
        String cssClassAttributeValue = StringUtils.join(currentCssStyles, HTML_SEPARATOR_STYLE_ARRAY);
        putAttr(HTML_ATTR_STYLE, cssClassAttributeValue);
        return this;
    }

    private List<String> removeCssStyleHelper(CharSequence styleKey) {
        String cssClassAttributeValue = getAttr(HTML_ATTR_STYLE);
        List<String> currentCssStyles = Arrays.asList(cssClassAttributeValue.split(HTML_SEPARATOR_STYLE_ARRAY_REGEX));
        Iterator<String> currentCssStylesIterator = currentCssStyles.iterator();
        while (currentCssStylesIterator.hasNext()) {
            String cssStyleArray = currentCssStylesIterator.next();
            int valueIndex = cssStyleArray.indexOf(HTML_SEPARATOR_STYLE_VALUE);
            if (valueIndex == -1) {
                currentCssStylesIterator.remove();
                continue;
            }
            String currentStyleKey = cssStyleArray.substring(0, valueIndex).trim();
            if (StringUtils.equalsIgnoreCase(currentStyleKey, styleKey)) {
                currentCssStylesIterator.remove();
            }
        }
        return currentCssStyles;
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
    public ElementNode findById(CharSequence id) {
        ElementNodeSelection elementNodeSelection = findByAttr("id", id, FilterMatchMode.FULL_MATCH);
        if (elementNodeSelection.size() == 0) {
            return null;
        } else if (elementNodeSelection.size() > 1) {
            throw new IllegalStateException();
        } else {
            return elementNodeSelection.get(0);
        }
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
    public <S extends AbstractNode<S>, T extends NodeSelection<T, S>> NodeSelection<T, S> findByFilter(AbstractNodeFilter<S> nodeFilter) {
        return new NodeSelection<T, S>(DomTreeBrowserHelper.getInstance().filter(this, nodeFilter, Integer.MAX_VALUE));
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
        stringBuilder.append(isRendered());
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
