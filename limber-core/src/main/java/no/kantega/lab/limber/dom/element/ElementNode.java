package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomElementBrowsable;
import no.kantega.lab.limber.dom.abstraction.IDomElementMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomElementQueryable;
import no.kantega.lab.limber.dom.filter.*;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.*;

public class ElementNode extends AbstractNode<ElementNode>
        implements IDomElementMorphable<ElementNode>, IDomElementBrowsable<AbstractNode<?>>, IDomElementQueryable {

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

    public ElementNode(@Nonnull CharSequence tag) {
        setTagName(tag);
    }

    @Override
    public String getTagName() {
        return tagName;
    }

    private static String normalizeTagName(CharSequence tagName) {
        if (StringUtils.isBlank(tagName)) throw new IllegalArgumentException();
        return tagName.toString().toUpperCase(Locale.US);
    }

    @Override
    public ElementNode setTagName(@Nonnull CharSequence tagName) {
        this.tagName = normalizeTagName(tagName);
        return this;
    }

    @Override
    public boolean isTag(CharSequence tagName) {
        return StringUtils.equalsIgnoreCase(this.tagName, tagName);
    }

    @Override
    public ElementNode addChild(int index, @Nonnull AbstractNode<?> node) {
        if (index < 0 || index > (children == null ? 0 : children.size())) {
            throw new IllegalArgumentException();
        }
        if (children == null) children = new ArrayList<AbstractNode<?>>();
        children.add(index, node);
        return this;
    }

    @Override
    public ElementNode prependChild(@Nonnull AbstractNode<?> node) {
        return addChild(0, node);
    }

    @Override
    public ElementNode appendChild(@Nonnull AbstractNode<?> node) {
        return addChild(children == null ? 0 : children.size(), node);
    }

    protected void removeChild(AbstractNode<?> node) {
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
    public ElementNode addChild(int index, @Nonnull CharSequence tagName) {
        return addChild(index, new ElementNode(tagName));
    }

    @Override
    public ElementNode addText(int index, @Nonnull CharSequence text) {
        return addChild(index, new TextNode(text));
    }

    @Override
    public ElementNode addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        return addChild(index, new TextNode(text, contentEscapeMode));
    }

    @Override
    public ElementNode appendChild(@Nonnull CharSequence tagName) {
        return appendChild(new ElementNode(tagName));
    }

    @Override
    public ElementNode appendText(@Nonnull CharSequence text) {
        return appendChild(new TextNode(text));
    }

    @Override
    public ElementNode appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        return appendChild(new TextNode(text, contentEscapeMode));
    }

    @Override
    public ElementNode prependChild(@Nonnull CharSequence tagName) {
        return prependChild(new ElementNode(tagName));
    }

    @Override
    public ElementNode prependText(@Nonnull CharSequence text) {
        return prependChild(new TextNode(text));
    }

    @Override
    public ElementNode prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        return prependChild(new TextNode(text, contentEscapeMode));
    }

    @Override
    public ElementNode setContent(CharSequence content) {
        return setContent(content, ContentEscapeMode.getDefault());
    }

    @Override
    public ElementNode setContent(CharSequence content, @Nonnull ContentEscapeMode contentEscapeMode) {
        clear();
        appendText(content, contentEscapeMode);
        return this;
    }

    @Override
    public int size() {
        if (children == null) {
            return 0;
        } else {
            return children.size();
        }
    }

    private static String normalizeAttributeKey(CharSequence key) {
        if (StringUtils.isBlank(key)) throw new IllegalArgumentException();
        return key.toString().toLowerCase(Locale.US);
    }

    @Override
    public String getAttr(@Nonnull CharSequence key) {
        if (attr == null) return null;
        return attr.get(normalizeAttributeKey(key));
    }

    @Override
    public ElementNode putAttr(@Nonnull CharSequence key, CharSequence value) {
        if (value == null) {
            removeAttr(key);
            return this;
        }
        if (attr == null) attr = new HashMap<String, String>();
        attr.put(normalizeAttributeKey(key), value.toString());
        return this;
    }

    @Override
    public ElementNode removeAttr(@Nonnull CharSequence key) {
        if (attr == null) return this;
        attr.remove(normalizeAttributeKey(key));
        if (attr.size() == 0) attr = null;
        return this;
    }

    @Override
    public Map<String, String> getAttrs() {
        if (attr == null) {
            return Collections.emptyMap();
        } else {
            return Collections.unmodifiableMap(attr);
        }
    }

    @Override
    public boolean isAttribute(CharSequence key) {
        return attr != null && attr.containsKey(normalizeAttributeKey(key));
    }

    @Override
    public boolean isAttribute(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode) {
        String actualValue = getAttr(key);
        return actualValue != null && queryMatchMode.compare(actualValue, value);
    }

    @Override
    public boolean isIdSet() {
        return getAttr("id") != null;
    }

    @Override
    public ElementNode setId(@Nonnull CharSequence id) {
        return putAttr("id", id);
    }

    @Override
    public String getId() {
        return getAttr("id");
    }

    @Override
    public ElementNode setRandomId() {
        return putAttr("id", UUID.randomUUID().toString());
    }

    @Override
    public ElementNode setRandomIdIfNone() {
        if (!isIdSet()) setRandomId();
        return this;
    }

    @Override
    public ElementNode removeId() {
        return removeAttr("id");
    }

    private static String normalizeCssClassName(CharSequence cssClassName) {
        if (StringUtils.isBlank(cssClassName)) throw new IllegalArgumentException();
        return cssClassName.toString().toLowerCase(Locale.US);
    }

    @Override
    public boolean isCssClass(CharSequence cssClassName) {
        return getCssClasses().contains(normalizeCssClassName(cssClassName));
    }

    @Override
    public ElementNode addCssClass(@Nonnull CharSequence cssClassName) {
        List<String> cssClassList = getCssClasses();
        String normalizedCssClassName = normalizeCssClassName(cssClassName);
        if (!cssClassList.contains(normalizedCssClassName)) {
            cssClassList.add(normalizedCssClassName);
            setCssClasses(cssClassList);
        }
        return this;
    }

    @Override
    public ElementNode removeCssClass(@Nonnull CharSequence cssClassName) {
        List<String> cssClassList = getCssClasses();
        if (cssClassList.remove(normalizeCssClassName(cssClassName))) {
            setCssClasses(cssClassList);
        }
        return this;
    }

    @Override
    public List<String> getCssClasses() {
        String cssClassAttributeValue = getAttr(HTML_ATTR_CLASS);
        if (cssClassAttributeValue == null) return Collections.emptyList();
        List<String> cssClassNameList = Arrays.asList(cssClassAttributeValue.split(HTML_SEPARATOR_CLASS_REGEX));
        for (int i = 0; i < cssClassNameList.size(); i++) {
            cssClassNameList.set(i, normalizeCssClassName(cssClassNameList.get(i)));
        }
        return cssClassNameList;
    }

    @Override
    public ElementNode setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames) {
        String cssClassString = StringUtils.join(cssClassNames, HTML_SEPARATOR_CLASS);
        putAttr(HTML_ATTR_CLASS, cssClassString);
        return this;
    }

    @Override
    public boolean isCssStyle(CharSequence key) {
        return getCssStyles().containsKey(key.toString());
    }

    @Override
    public boolean isCssStyle(@Nonnull CharSequence key, CharSequence value, @Nonnull QueryMatchMode queryMatchMode) {
        Map<String, String> cssStyleMap = getCssStyles();
        String actualValue = cssStyleMap.get(key.toString());
        return actualValue != null && queryMatchMode.compare(actualValue, value);
    }

    @Override
    public ElementNode addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue) {
        Map<String, String> cssStyleMap = getCssStyles();
        if (!StringUtils.equals(cssStyleMap.get(styleKey.toString()), styleKey)) {
            cssStyleMap.put(styleKey.toString(), styleValue.toString());
            setCssStyles(cssStyleMap);
        }
        return this;
    }

    @Override
    public ElementNode removeCssStyle(@Nonnull CharSequence styleKey) {
        Map<String, String> cssStyleMap = getCssStyles();
        if (cssStyleMap.remove(styleKey.toString()) != null) {
            setCssStyles(cssStyleMap);
        }
        return this;
    }

    @Override
    public Map<String, String> getCssStyles() {
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

    @Override
    public ElementNode setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<? extends CharSequence, ? extends CharSequence> entry : cssStyles.entrySet()) {
            stringBuilder.append(entry.getKey()).append(HTML_SEPARATOR_STYLE_VALUE).append(entry.getValue()).append(';');
        }
        putAttr(HTML_ATTR_STYLE, stringBuilder.toString());
        return this;
    }

    @Override
    public ElementNode clear() {
        if (children == null) return this;
        children.clear();
        children = null;
        return this;
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
    public ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName) {
        return findByCssClass(cssClassName, Integer.MAX_VALUE);
    }

    @Override
    public ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new CssClassNameFilter(cssClassName), maxDepth));
    }

    @Override
    public <S extends AbstractNode<S>, T extends NodeSelection<T, S>> NodeSelection<T, S> findByFilter(@Nonnull INodeFilter<S> nodeFilter) {
        return findByFilter(nodeFilter, Integer.MAX_VALUE);
    }

    @Override
    public <S extends AbstractNode<S>, T extends NodeSelection<T, S>> NodeSelection<T, S> findByFilter(@Nonnull INodeFilter<S> nodeFilter, int maxDepth) {
        return new NodeSelection<T, S>(NodeFilterSupport.getInstance().filter(this, nodeFilter, maxDepth));
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

    @Override
    public Iterator<AbstractNode<?>> iterator() {
        return new Iterator<AbstractNode<?>>() {
            private int iterationCount = 0;

            @Override
            public boolean hasNext() {
                return iterationCount < children().size();
            }

            @Override
            public AbstractNode<?> next() {
                return children().get(iterationCount++);
            }

            @Override
            public void remove() {
                throw new IllegalStateException();
            }
        };
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
