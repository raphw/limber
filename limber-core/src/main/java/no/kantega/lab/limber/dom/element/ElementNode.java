package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomElementBrowsable;
import no.kantega.lab.limber.dom.abstraction.IDomElementMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomElementQueryable;
import no.kantega.lab.limber.dom.filter.*;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.*;

public class ElementNode extends AbstractNode<ElementNode> implements IDomElementMorphable<ElementNode>,
        IDomElementBrowsable<AbstractNode<?>, ElementNode>, IDomElementQueryable {

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

    @Nonnull
    @Override
    public String getTagName() {
        return tagName;
    }

    private static String normalizeTagName(CharSequence tagName) {
        if (StringUtils.isBlank(tagName)) throw new IllegalArgumentException();
        return tagName.toString().toUpperCase(Locale.US);
    }

    @Nonnull
    @Override
    public ElementNode setTagName(@Nonnull CharSequence tagName) {
        this.tagName = normalizeTagName(tagName);
        return this;
    }

    @Override
    public boolean isTag(CharSequence tagName) {
        return StringUtils.equalsIgnoreCase(this.tagName, tagName);
    }

    @Nonnull
    @Override
    public ElementNode addChildAndStay(int index, @Nonnull AbstractNode<?> node) {
        if (index < 0 || index > (children == null ? 0 : children.size())) {
            throw new IllegalArgumentException();
        }
        if (children == null) children = new ArrayList<AbstractNode<?>>();
        children.add(index, node);
        return this;
    }

    @Nonnull
    @Override
    public ElementNode prependChildAndStay(@Nonnull AbstractNode<?> node) {
        return addChildAndStay(0, node);
    }

    @Nonnull
    @Override
    public ElementNode appendChildAndStay(@Nonnull AbstractNode<?> node) {
        return addChildAndStay(children == null ? 0 : children.size(), node);
    }

    protected void removeChild(AbstractNode<?> node) {
        if (children == null) return;
        children.remove(node);
        if (children.size() == 0) children = null;
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> getChildren() {
        return getChildren(new BooleanRepeaterFilter<AbstractNode>(true));
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> getChildren(@Nonnull INodeFilter<AbstractNode> nodeFilter) {
        return getChildren(nodeFilter, AbstractNode.class);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode> NodeSelection<N2, ?> getChildren(@Nonnull INodeFilter<N2> nodeFilter, Class<? extends N2> filterBoundary) {
        if (children == null) {
            return new NodeSelection<N2, NodeSelection<N2, ?>>(Collections.<N2>emptyList());
        } else {
            return new NodeSelection<N2, NodeSelection<N2, ?>>(NodeFilterSupport.getInstance().filterNodeList(children, nodeFilter, filterBoundary));
        }
    }

    @Nonnull
    @Override
    public ElementNode addChildAndStay(int index, @Nonnull CharSequence tagName) {
        return addChildAndStay(index, new ElementNode(tagName));
    }

    @Nonnull
    @Override
    public ElementNode addTextAndStay(int index, @Nonnull CharSequence text) {
        return addChildAndStay(index, new TextNode(text));
    }

    @Nonnull
    @Override
    public ElementNode addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        return addChildAndStay(index, new TextNode(text, contentEscapeMode));
    }

    @Nonnull
    @Override
    public ElementNode appendChildAndStay(@Nonnull CharSequence tagName) {
        return appendChildAndStay(new ElementNode(tagName));
    }

    @Nonnull
    @Override
    public ElementNode appendTextAndStay(@Nonnull CharSequence text) {
        return appendChildAndStay(new TextNode(text));
    }

    @Nonnull
    @Override
    public ElementNode appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        return appendChildAndStay(new TextNode(text, contentEscapeMode));
    }

    @Nonnull
    @Override
    public ElementNode prependChildAndStay(@Nonnull CharSequence tagName) {
        return prependChildAndStay(new ElementNode(tagName));
    }

    @Nonnull
    @Override
    public ElementNode prependTextAndStay(@Nonnull CharSequence text) {
        return prependChildAndStay(new TextNode(text));
    }

    @Nonnull
    @Override
    public ElementNode prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode) {
        return prependChildAndStay(new TextNode(text, contentEscapeMode));
    }

    @Nonnull
    @Override
    public ElementNode setContent(CharSequence content) {
        return setContent(content, ContentEscapeMode.getDefault());
    }

    @Nonnull
    @Override
    public ElementNode setContent(CharSequence content, @Nonnull ContentEscapeMode contentEscapeMode) {
        clear();
        appendTextAndStay(content, contentEscapeMode);
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

    @Override
    public boolean isRoot() {
        return getParent() == null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
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

    @Nonnull
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

    @Nonnull
    @Override
    public ElementNode removeAttr(@Nonnull CharSequence key) {
        if (attr == null) return this;
        attr.remove(normalizeAttributeKey(key));
        if (attr.size() == 0) attr = null;
        return this;
    }

    @Nonnull
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

    @Nonnull
    @Override
    public ElementNode setId(@Nonnull CharSequence id) {
        return putAttr("id", id);
    }

    @Override
    public String getId() {
        return getAttr("id");
    }

    @Nonnull
    @Override
    public ElementNode setRandomId() {
        return putAttr("id", UUID.randomUUID().toString());
    }

    @Nonnull
    @Override
    public ElementNode setRandomIdIfNone() {
        if (!isIdSet()) setRandomId();
        return this;
    }

    @Nonnull
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

    @Nonnull
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

    @Nonnull
    @Override
    public ElementNode removeCssClass(@Nonnull CharSequence cssClassName) {
        List<String> cssClassList = getCssClasses();
        if (cssClassList.remove(normalizeCssClassName(cssClassName))) {
            setCssClasses(cssClassList);
        }
        return this;
    }

    @Nonnull
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

    @Nonnull
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

    @Nonnull
    @Override
    public ElementNode addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue) {
        Map<String, String> cssStyleMap = getCssStyles();
        if (!StringUtils.equals(cssStyleMap.get(styleKey.toString()), styleKey)) {
            cssStyleMap.put(styleKey.toString(), styleValue.toString());
            setCssStyles(cssStyleMap);
        }
        return this;
    }

    @Nonnull
    @Override
    public ElementNode removeCssStyle(@Nonnull CharSequence styleKey) {
        Map<String, String> cssStyleMap = getCssStyles();
        if (cssStyleMap.remove(styleKey.toString()) != null) {
            setCssStyles(cssStyleMap);
        }
        return this;
    }

    @Nonnull
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

    @Nonnull
    @Override
    public ElementNode setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<? extends CharSequence, ? extends CharSequence> entry : cssStyles.entrySet()) {
            stringBuilder.append(entry.getKey()).append(HTML_SEPARATOR_STYLE_VALUE).append(entry.getValue()).append(';');
        }
        putAttr(HTML_ATTR_STYLE, stringBuilder.toString());
        return this;
    }

    @Nonnull
    @Override
    public ElementNode clear() {
        if (children == null) return this;
        children.clear();
        children = null;
        return this;
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
    public ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName) {
        return findByCssClass(cssClassName, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public ElementNodeSelection findByCssClass(@Nonnull CharSequence cssClassName, int maxDepth) {
        return new ElementNodeSelection(findByFilter(new CssClassNameFilter(cssClassName), ElementNode.class, maxDepth));
    }

//    @Nonnull
//    @Override
//    public <N2 extends AbstractNode<?>, C2 extends NodeSelection<N2, C2>> NodeSelection<N2, C2> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, int maxDepth) {
//        return new NodeSelection<N2, C2>(NodeFilterSupport.getInstance().filterNodeTree(this, nodeFilter, maxDepth));
//    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> findByFilter(@Nonnull INodeFilter<AbstractNode> nodeFilter) {
        return findByFilter(nodeFilter, AbstractNode.class);
    }

    @Nonnull
    @Override
    public NodeSelection<AbstractNode, ?> findByFilter(@Nonnull INodeFilter<AbstractNode> nodeFilter, int maxDepth) {
        return findByFilter(nodeFilter, AbstractNode.class, maxDepth);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode> NodeSelection<N2, ?> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<? extends N2> filterBoundary) {
        return findByFilter(nodeFilter, filterBoundary, Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode> NodeSelection<N2, ?> findByFilter(@Nonnull INodeFilter<N2> nodeFilter, @Nonnull Class<? extends N2> filterBoundary, int maxDepth) {
        return new NodeSelection<N2, NodeSelection<N2, ?>>(NodeFilterSupport.getInstance().filterNodeTree(this, nodeFilter, filterBoundary, maxDepth));
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

    @Override
    public Iterator<AbstractNode<?>> iterator() {
        return new Iterator<AbstractNode<?>>() {
            private int iterationCount = 0;

            @Override
            public boolean hasNext() {
                return iterationCount < getChildren().size();
            }

            @Override
            public AbstractNode<?> next() {
                return getChildren().get(iterationCount++);
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
        for (AbstractNode<?> node : getChildren()) {
            stringBuilder.append('\n');
            stringBuilder.append(node.toString());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
