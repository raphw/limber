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

    private Map<String, String> attr;

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
        return attr.get(key.toString());
    }

    @Override
    public ElementNode putAttr(CharSequence key, CharSequence value) {
        if (value == null) {
            removeAttr(key);
            return this;
        }
        if (attr == null) attr = new HashMap<String, String>();
        attr.put(key.toString(), value.toString());
        return this;
    }

    @Override
    public ElementNode removeAttr(CharSequence key) {
        if (attr == null) return this;
        attr.remove(key.toString());
        if (attr.size() == 0) attr = null;
        return this;
    }

    @Override
    public Set<String> attrs() {
        if (attr == null) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(attr.keySet());
        }
    }

    @Override
    public boolean isAttribute(CharSequence key) {
        return attr != null && attr.containsKey(key.toString());
    }

    @Override
    public boolean isAttribute(CharSequence key, CharSequence value, QueryMatchMode queryMatchMode) {
        String actualValue = getAttr(key);
        if (actualValue == null) return false;
        switch (queryMatchMode) {
            case FULL_MATCH:
                return StringUtils.equals(actualValue, value);
            case STARTS_WITH:
                return StringUtils.startsWith(actualValue, value);
            case ENDS_WITH:
                return StringUtils.endsWith(actualValue, value);
            case CONTAINS:
                return StringUtils.contains(actualValue, value);
            default:
                return false;
        }
    }

    @Override
    public boolean isCssClass(CharSequence cssClassName) {
        return makeCssClassList().contains(cssClassName.toString());
    }

    @Override
    public ElementNode addCssClass(CharSequence cssClassName) {
        List<String> cssClassList = makeCssClassList();
        if (!cssClassList.contains(cssClassName.toString())) {
            cssClassList.add(cssClassName.toString());
            updateCssClassAttr(cssClassList);
        }
        return this;
    }

    @Override
    public ElementNode removeCssClass(CharSequence cssClassName) {
        List<String> cssClassList = makeCssClassList();
        if (cssClassList.remove(cssClassName.toString())) {
            updateCssClassAttr(cssClassList);
        }
        return this;
    }

    private List<String> makeCssClassList() {
        String cssClassAttributeValue = getAttr(HTML_ATTR_CLASS);
        if (cssClassAttributeValue == null) return Collections.emptyList();
        return Arrays.asList(cssClassAttributeValue.split(HTML_SEPARATOR_CLASS_REGEX));
    }

    private void updateCssClassAttr(List<String> cssClassList) {
        String cssClassString = StringUtils.join(cssClassList, HTML_SEPARATOR_CLASS);
        putAttr(HTML_ATTR_CLASS, cssClassString);
    }

    @Override
    public boolean isCssStyle(CharSequence key) {
        return makeCssStyleMap().containsKey(key.toString());
    }

    @Override
    public boolean isCssStyle(CharSequence key, CharSequence value, QueryMatchMode queryMatchMode) {
        Map<String, String> cssStyleMap = makeCssStyleMap();
        String actualValue = cssStyleMap.get(key.toString());
        if (actualValue == null) return false;
        switch (queryMatchMode) {
            case FULL_MATCH:
                return StringUtils.equals(actualValue, value);
            case STARTS_WITH:
                return StringUtils.startsWith(actualValue, value);
            case ENDS_WITH:
                return StringUtils.endsWith(actualValue, value);
            case CONTAINS:
                return StringUtils.contains(actualValue, value);
            default:
                return false;
        }
    }

    @Override
    public ElementNode addCssStyle(CharSequence styleKey, CharSequence styleValue) {
        Map<String, String> cssStyleMap = makeCssStyleMap();
        if (!StringUtils.equals(cssStyleMap.get(styleKey.toString()), styleKey)) {
            cssStyleMap.put(styleKey.toString(), styleValue.toString());
            updateCssStyleAttr(cssStyleMap);
        }
        return this;
    }

    @Override
    public ElementNode removeCssStyle(CharSequence styleKey) {
        Map<String, String> cssStyleMap = makeCssStyleMap();
        if (cssStyleMap.remove(styleKey.toString()) != null) {
            updateCssStyleAttr(cssStyleMap);
        }
        return this;
    }

    private Map<String, String> makeCssStyleMap() {
        String cssClassAttributeValue = getAttr(HTML_ATTR_STYLE);
        if (cssClassAttributeValue == null) return Collections.emptyMap();
        List<String> currentCssStyles = Arrays.asList(cssClassAttributeValue.split(HTML_SEPARATOR_STYLE_ARRAY_REGEX));
        Map<String, String> cssStyleMap = new HashMap<String, String>();
        for (String combinedStyle : currentCssStyles) {
            int valueIndex = combinedStyle.indexOf(HTML_SEPARATOR_STYLE_VALUE);
            if (valueIndex == -1) {
                cssStyleMap.put(combinedStyle, null);
            } else {
                String styleKey = combinedStyle.substring(0, valueIndex).trim(),
                        styleValue = combinedStyle.substring(valueIndex).trim();
                cssStyleMap.put(styleKey, styleValue);
            }
        }
        return cssStyleMap;
    }

    private void updateCssStyleAttr(Map<String, String> cssStyleMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : cssStyleMap.entrySet()) {
            stringBuilder.append(entry.getKey()).append(HTML_SEPARATOR_STYLE_VALUE).append(entry.getValue()).append(';');
        }
        putAttr(HTML_ATTR_STYLE, stringBuilder.toString());

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
        ElementNodeSelection elementNodeSelection = findByAttr("id", id, QueryMatchMode.FULL_MATCH);
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
    public ElementNodeSelection findByAttr(CharSequence key, CharSequence value, QueryMatchMode filterMatchMode) {
        return new ElementNodeSelection(findByFilter(new AttributeKeyValueFilter(key, value, filterMatchMode)));
    }

    @Override
    public <S extends AbstractNode<S>, T extends NodeSelection<T, S>> NodeSelection<T, S> findByFilter(INodeFilter<S> nodeFilter) {
        return new NodeSelection<T, S>(NodeFilterSupport.getInstance().filter(this, nodeFilter, Integer.MAX_VALUE));
    }

    @Override
    public TextNodeSelection findTextNodes() {
        return new TextNodeSelection(findByFilter(new TextNodeFilter()));
    }

    @Override
    public ElementNodeSelection findElements() {
        return new ElementNodeSelection(findByFilter(new ElementNodeFilter()));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ElementNode[");
        stringBuilder.append(isRendered());
        stringBuilder.append(',');
        stringBuilder.append(getTagName());
        if (attr != null) {
            for (Map.Entry<String, String> entry : attr.entrySet()) {
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
