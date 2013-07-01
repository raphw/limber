package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomElementNodeQueryable;
import no.kantega.lab.limber.dom.abstraction.IDomElementNodeRepresentable;
import no.kantega.lab.limber.dom.ajax.AjaxBoundEventTupel;
import no.kantega.lab.limber.dom.ajax.AjaxEventTrigger;
import no.kantega.lab.limber.dom.ajax.IAjaxCallback;
import no.kantega.lab.limber.dom.filter.*;
import no.kantega.lab.limber.dom.filter.util.NodeFilterSupport;
import no.kantega.lab.limber.dom.filter.util.QueryMatchMode;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.dom.selection.NodeSelection;
import no.kantega.lab.limber.dom.selection.TextNodeSelection;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class ElementNode<N extends ElementNode<N>> extends AbstractNode<N>
        implements IDomElementNodeRepresentable<N>, IDomElementNodeQueryable<N>, Iterable<AbstractNode<?>> {

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

    private List<AjaxBoundEventTupel<N>> ajaxEvents;

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
        return tagName.toString().trim().toUpperCase(Locale.US);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N setTagName(@Nonnull CharSequence tagName) {
        this.tagName = normalizeTagName(tagName);
        return (N) this;
    }

    @Override
    public boolean isTag(CharSequence tagName) {
        return StringUtils.equalsIgnoreCase(this.tagName, tagName);
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>> N2 addChild(int index, @Nonnull N2 node) {
        if (index < 0 || index > (children == null ? 0 : children.size())) {
            throw new IllegalArgumentException();
        }
        if (children == null) children = new ArrayList<AbstractNode<?>>();
        children.add(index, node);
        node.setParent(this);
        return node;
    }


    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N addChildAndStay(int index, @Nonnull AbstractNode<?> node) {
        addChild(index, node);
        return (N) this;
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>> N2 prependChild(@Nonnull N2 node) {
        return addChild(0, node);
    }

    @Nonnull
    @Override
    public N prependChildAndStay(@Nonnull AbstractNode<?> node) {
        return addChildAndStay(0, node);
    }

    @Nonnull
    @Override
    public String getXPath() {
        StringBuilder stringBuilder = new StringBuilder();
        getXPath(stringBuilder);
        return stringBuilder.toString();
    }


    protected void getXPath(StringBuilder stringBuilder) {
        if (getParent() == null) {
            stringBuilder.append("/").append(getTagName().toLowerCase());
        } else {
            getParent().getXPath(stringBuilder);
            stringBuilder.append("/*[").append(getParent().getChildIndex(this, ElementNode.class) + 1).append(']');
        }
    }

    @Nonnull
    @Override
    public <N2 extends AbstractNode<? extends N2>> N2 appendChild(@Nonnull N2 node) {
        return addChild(children == null ? 0 : children.size(), node);
    }

    @Nonnull
    @Override
    public N appendChildAndStay(@Nonnull AbstractNode<?> node) {
        return addChildAndStay(children == null ? 0 : children.size(), node);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    protected N removeChild(@Nonnull AbstractNode<?> node) {
        if (children != null) {
            children.remove(node);
            if (children.size() == 0) children = null;
        }
        return (N) this;
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
        if (children == null) {
            return new NodeSelection<N2, NodeSelection<N2, ?>>(Collections.<N2>emptyList());
        } else {
            return new NodeSelection<N2, NodeSelection<N2, ?>>(NodeFilterSupport.getInstance().filterNodeList(children, nodeFilter, filterBoundary));
        }
    }

    @Nonnull
    @Override
    public ElementNode<?> addChild(int index, @Nonnull CharSequence tagName) {
        return addChild(index, ElementNodeFactory.getInstance().make(tagName));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N addChildAndStay(int index, @Nonnull CharSequence tagName) {
        addChild(index, tagName);
        return (N) this;
    }

    @Nonnull
    @Override
    public TextNode addText(int index, @Nonnull CharSequence text) {
        TextNode textNode = new TextNode(text);
        addChild(index, textNode);
        return textNode;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N addTextAndStay(int index, @Nonnull CharSequence text) {
        addText(index, text);
        return (N) this;
    }

    @Nonnull
    @Override
    public TextNode addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        return addChild(index, new TextNode(text, contentEscapeStrategy));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        addText(index, text, contentEscapeStrategy);
        return (N) this;
    }

    @Nonnull
    @Override
    public ElementNode<?> appendChild(@Nonnull CharSequence tagName) {
        return appendChild(ElementNodeFactory.getInstance().make(tagName));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N appendChildAndStay(@Nonnull CharSequence tagName) {
        appendChild(tagName);
        return (N) this;
    }

    @Nonnull
    @Override
    public TextNode appendText(@Nonnull CharSequence text) {
        return appendChild(new TextNode(text));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N appendTextAndStay(@Nonnull CharSequence text) {
        appendText(text);
        return (N) this;
    }

    @Nonnull
    @Override
    public TextNode appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        return appendChild(new TextNode(text, contentEscapeStrategy));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        appendText(text, contentEscapeStrategy);
        return (N) this;
    }

    @Nonnull
    @Override
    public ElementNode<?> prependChild(@Nonnull CharSequence tagName) {
        return prependChild(ElementNodeFactory.getInstance().make(tagName));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N prependChildAndStay(@Nonnull CharSequence tagName) {
        prependChild(tagName);
        return (N) this;
    }

    @Nonnull
    @Override
    public TextNode prependText(@Nonnull CharSequence text) {
        return prependChild(new TextNode(text));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N prependTextAndStay(@Nonnull CharSequence text) {
        prependText(text);
        return (N) this;
    }

    @Nonnull
    @Override
    public TextNode prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        return prependChild(new TextNode(text, contentEscapeStrategy));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        prependText(text, contentEscapeStrategy);
        return (N) this;
    }

    @Nonnull
    @Override
    public <T2 extends ElementNode<? extends T2>> T2 wrap(@Nonnull T2 elementNode) {
        replaceBy(elementNode);
        elementNode.appendChild(this);
        return elementNode;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N wrapAndStay(@Nonnull ElementNode<?> elementNode) {
        wrap(elementNode);
        return (N) this;
    }

    @Nonnull
    @Override
    public ElementNode<?> wrap(@Nonnull CharSequence tagName) {
        return wrap(ElementNodeFactory.getInstance().make(tagName));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N wrapAndStay(@Nonnull CharSequence tagName) {
        wrap(tagName);
        return (N) this;
    }

    @Nonnull
    @Override
    public N setContent(CharSequence content) {
        return setContent(content, ContentEscapeStrategy.getDefault());
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N setContent(CharSequence content, @Nonnull ContentEscapeStrategy contentEscapeStrategy) {
        clear();
        if (content != null) appendTextAndStay(content, contentEscapeStrategy);
        return (N) this;
    }

    @Override
    public int getChildIndex(@Nonnull AbstractNode<?> node) {
        int i = 0;
        for (AbstractNode<?> child : getChildren()) {
            if (child == node) {
                return i;
            }
            i++;
        }
        throw new IllegalArgumentException();
    }

    public int getChildIndex(@Nonnull AbstractNode<?> node, @Nonnull Class<? extends AbstractNode> nodeClass) {
        int i = 0;
        for (AbstractNode<?> child : getChildren()) {
            if (child == node) {
                return i;
            }
            if (nodeClass.isAssignableFrom(child.getClass())) {
                i++;
            }
        }
        throw new IllegalArgumentException();
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
    @SuppressWarnings("unchecked")
    public N putAttr(@Nonnull CharSequence key, CharSequence value) {
        if (value == null) {
            removeAttr(key);
        } else {
            if (attr == null) attr = new HashMap<String, String>();
            attr.put(normalizeAttributeKey(key), value.toString());
        }
        return (N) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N removeAttr(@Nonnull CharSequence key) {
        if (attr != null) {
            attr.remove(normalizeAttributeKey(key));
            if (attr.size() == 0) attr = null;
        }
        return (N) this;
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
    public N setId(@Nonnull CharSequence id) {
        return putAttr("id", id);
    }

    @Override
    public String getId() {
        return getAttr("id");
    }

    @Nonnull
    @Override
    public N setRandomId() {
        return putAttr("id", UUID.randomUUID().toString());
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N setRandomIdIfNone() {
        if (!isIdSet()) setRandomId();
        return (N) this;
    }

    @Nonnull
    @Override
    public N removeId() {
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
    @SuppressWarnings("unchecked")
    public N addCssClass(@Nonnull CharSequence cssClassName) {
        List<String> cssClassList = getCssClasses();
        String normalizedCssClassName = normalizeCssClassName(cssClassName);
        if (!cssClassList.contains(normalizedCssClassName)) {
            cssClassList.add(normalizedCssClassName);
            setCssClasses(cssClassList);
        }
        return (N) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N removeCssClass(@Nonnull CharSequence cssClassName) {
        List<String> cssClassList = getCssClasses();
        if (cssClassList.remove(normalizeCssClassName(cssClassName))) {
            setCssClasses(cssClassList);
        }
        return (N) this;
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
    @SuppressWarnings("unchecked")
    public N setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames) {
        String cssClassString = StringUtils.join(cssClassNames, HTML_SEPARATOR_CLASS);
        putAttr(HTML_ATTR_CLASS, cssClassString);
        return (N) this;
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
    @SuppressWarnings("unchecked")
    public N addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue) {
        Map<String, String> cssStyleMap = getCssStyles();
        if (!StringUtils.equals(cssStyleMap.get(styleKey.toString()), styleKey)) {
            cssStyleMap.put(styleKey.toString(), styleValue.toString());
            setCssStyles(cssStyleMap);
        }
        return (N) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N removeCssStyle(@Nonnull CharSequence styleKey) {
        Map<String, String> cssStyleMap = getCssStyles();
        if (cssStyleMap.remove(styleKey.toString()) != null) {
            setCssStyles(cssStyleMap);
        }
        return (N) this;
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
    @SuppressWarnings("unchecked")
    public N setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<? extends CharSequence, ? extends CharSequence> entry : cssStyles.entrySet()) {
            stringBuilder.append(entry.getKey()).append(HTML_SEPARATOR_STYLE_VALUE).append(entry.getValue()).append(';');
        }
        putAttr(HTML_ATTR_STYLE, stringBuilder.toString());
        return (N) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N clear() {
        if (children != null) {
            children.clear();
            children = null;
        }
        return (N) this;
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
    public NodeSelection<?, ?> findByFilter(@Nonnull INodeFilter<AbstractNode<?>> nodeFilter) {
        return findByFilter(nodeFilter, AbstractNode.class, Integer.MAX_VALUE);
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
        return String.format("%s[tag=%s,rendered=%b,children=%d]", getClass().getSimpleName(), getTagName(), isRendered(), getChildren().size());
    }

    @Override
    @Nonnull
    @SuppressWarnings("unchecked")
    public N addAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger, @Nonnull IAjaxCallback<? super N> ajaxCallback) {
        N cast = (N) this;
        if (ajaxEvents == null) ajaxEvents = new ArrayList<AjaxBoundEventTupel<N>>();
        AjaxBoundEventTupel<N> tupel = new AjaxBoundEventTupel<N>(cast, ajaxEventTrigger, ajaxCallback);
        if (!ajaxEvents.contains(tupel)) ajaxEvents.add(tupel);
        return cast;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N removeAjaxEvent(@Nonnull IAjaxCallback<? super N> ajaxCallback) {
        if (ajaxEvents != null) {
            Iterator<AjaxBoundEventTupel<N>> iterator = ajaxEvents.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getAjaxCallback().equals(ajaxCallback)) iterator.remove();
            }
        }
        return (N) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N removeAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger) {
        if (ajaxEvents != null) {
            Iterator<AjaxBoundEventTupel<N>> iterator = ajaxEvents.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getAjaxEventTrigger().equals(ajaxEventTrigger)) iterator.remove();
            }
        }
        return (N) this;
    }

    @Nonnull
    @Override
    public List<AjaxBoundEventTupel<N>> getAjaxEvents() {
        if (ajaxEvents == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(ajaxEvents);
        }
    }

    @Override
    protected boolean onRender(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext) throws IOException {

        onRegisterSubroutines(htmlRenderContext);

        writer.append("<").append(getTagName());
        onRenderAttrs(writer, htmlRenderContext);
        writer.append(">");

        onRenderChildren(writer, htmlRenderContext);

        writer.append("</");
        writer.append(getTagName());
        writer.append(">");

        writer.append("\n");

        return true;
    }

    protected void onRegisterSubroutines(@Nonnull IHtmlRenderContext htmlRenderContext) {
        for (AjaxBoundEventTupel<?> ajaxBoundEventTupel : getAjaxEvents()) {
            htmlRenderContext.getSubroutineRegister().registerSubroutine(ajaxBoundEventTupel);
        }
    }

    protected void onRenderChildren(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext) throws IOException {
        for (AbstractNode<?> child : getChildren()) {
            onRenderChild(child, writer, htmlRenderContext);
        }
    }

    protected boolean onRenderChild(@Nonnull AbstractNode<?> child, @Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext) throws IOException {
        if (child instanceof ElementNode) writer.append("\n");
        return child.render(writer, htmlRenderContext);
    }

    protected void onRenderAttrs(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext) throws IOException {
        for (Map.Entry<String, String> attr : getAttrs().entrySet()) {
            writer.append(" ");
            onRenderAttr(attr.getKey(), attr.getValue(), writer, htmlRenderContext);
        }
    }

    protected void onRenderAttr(@Nonnull String key, @Nonnull String value, @Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext) throws IOException {
        writer.append(key);
        writer.append("=\"");
        writer.append(value);
        writer.append("\"");
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public long localHashCode() {
        long result = super.localHashCode();
        result *= getTagName().hashCode();
        for (Map.Entry<String, String> attr : new TreeMap<String, String>(getAttrs()).entrySet()) {
            result *= (attr.getKey().hashCode() ^ attr.getValue().hashCode());
        }
        return result;
    }
}
