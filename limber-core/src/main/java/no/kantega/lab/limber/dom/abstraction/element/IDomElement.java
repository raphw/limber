package no.kantega.lab.limber.dom.abstraction.element;

import java.util.List;
import java.util.Set;

public interface IDomElement {

    String getTagName();

    void setTagName(String name);

    String getAttribute(String key);

    String setAttribute(String key, String value);

    boolean isAttributeSet(String key);

    Set<String> getAttributeNames();

    String getBestUniqueIdentifier();

    String getId();

    String setId(String value);

    String setRandomId(boolean ifNotExistent);

    boolean addCssClass(String className);

    boolean removeCssClass(String className);

    boolean isCssClassSet(String className);

    List<String> getCssClasses();

    IDomElement getParent();

    void setParent(IDomElement domElement);

    List<IDomElement> getChildren();

    void appendChild(IDomElement domElement);

    void addChild(int index, IDomElement domElement);

    void prependChild(IDomElement domElement);

    void removeChild(IDomElement domElement);

    List<IDomElement> getSiblings();

    void remove();
}
