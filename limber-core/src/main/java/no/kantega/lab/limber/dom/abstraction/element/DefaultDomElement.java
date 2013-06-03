package no.kantega.lab.limber.dom.abstraction.element;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DefaultDomElement implements IDomElement {

    private String tag;
    private Map<String, String> attributes;

    private IDomElement parent;
    private List<IDomElement> children;

    public DefaultDomElement(String tag, IDomElement parent) {
        this.tag = tag;
        this.parent = parent;
        if (parent != null) {
            parent.appendChild(this);
        }
    }

    private Map<String, String> getAttributeMap() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    private List<IDomElement> getChildrenList() {
        if (children == null) {
            children = new LinkedList<IDomElement>();
        }
        return children;
    }

    private int getChildrenListSize() {
        if (children == null) {
            return 0;
        } else {
            return children.size();
        }
    }

    @Override
    public String getTagName() {
        return tag;
    }

    @Override
    public void setTagName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Tag name must not be empty");
        }
        tag = name;
    }

    @Override
    public String getBestUniqueIdentifier() {
        // TODO: Not yet implemented
        return null;
    }

    @Override
    public String getAttribute(String key) {
        return getAttributeMap().get(key);
    }

    @Override
    public String setAttribute(String key, String value) {
        return getAttributeMap().put(key, value);
    }

    @Override
    public boolean isAttributeSet(String key) {
        return getAttributeMap().containsKey(key);
    }

    @Override
    public Set<String> getAttributeNames() {
        return getAttributeMap().keySet();
    }

    @Override
    public String getId() {
        return getAttributeMap().get("id");
    }

    @Override
    public String setId(String value) {
        return getAttributeMap().put("id", value);
    }

    @Override
    public String setRandomId(boolean ifNotExistent) {
        String id;
        if (!ifNotExistent || (id = getAttributeMap().get("id")) == null) {
            id = getAttributeMap().put("id", UUID.randomUUID().toString());
        }
        return id;
    }

    @Override
    public boolean addCssClass(String className) {
        testCssClassName(className);
        List<String> allClassNames = getCssClasses();
        if (allClassNames.contains(className)) {
            return false;
        } else {
            getAttributeMap().put("class", getAttributeMap().get("class") + " " + className);
            return true;
        }
    }

    @Override
    public boolean removeCssClass(String className) {
        testCssClassName(className);
        List<String> allClassNames = getCssClasses();
        if (allClassNames.remove(className)) {
            StringUtils.join(allClassNames, " ");
            return true;
        } else {
            getAttributeMap().put("class", getAttributeMap().get("class") + " " + className);
            return true;
        }
    }

    @Override
    public boolean isCssClassSet(String className) {
        return getCssClasses().contains(className);
    }

    @Override
    public List<String> getCssClasses() {
        String cssClassString = getAttributeMap().get("class");
        return Arrays.asList(cssClassString.split(" "));
    }

    private void testCssClassName(String className) {
        if (className.contains(" ")) {
            throw new IllegalArgumentException("Css class '" + className + "' contains white spaces");
        }
    }

    @Override
    public IDomElement getParent() {
        return parent;
    }

    @Override
    public void setParent(IDomElement domElement) {
        if (parent == domElement) {
            return;
        }
        if (parent != null) {
            parent.removeChild(this);
        }
        if (domElement != null) {
            domElement.appendChild(this);
        }
        parent = domElement;
    }

    @Override
    public List<IDomElement> getChildren() {
        return Collections.unmodifiableList(getChildrenList());
    }

    @Override
    public void appendChild(IDomElement domElement) {
        addChild(getChildrenList().size(), domElement);
    }

    @Override
    public void addChild(int index, IDomElement domElement) {
        if (index < 0 || index > getChildrenListSize()) {
            throw new IllegalArgumentException("Index does not fit size of children");
        }
        int currentIndex = getChildrenList().indexOf(domElement);
        if (currentIndex != -1 && currentIndex != index) {
            removeChild(domElement);
        }
        domElement.setParent(this);
    }

    @Override
    public void prependChild(IDomElement domElement) {
        addChild(0, domElement);
    }

    @Override
    public void removeChild(IDomElement domElement) {
        domElement.setParent(null);
        getChildrenList().remove(domElement);
    }

    @Override
    public List<IDomElement> getSiblings() {
        List<IDomElement> siblings = new ArrayList<IDomElement>(parent.getChildren());
        siblings.remove(this);
        return Collections.unmodifiableList(siblings);
    }

    @Override
    public void remove() {
        parent.removeChild(this);
        parent = null;
    }
}
