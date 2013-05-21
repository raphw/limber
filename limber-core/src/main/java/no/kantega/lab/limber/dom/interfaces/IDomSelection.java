package no.kantega.lab.limber.dom.interfaces;

public interface IDomSelection {

    IDomSelection tag(String identifier);

    IDomSelection id(String identifier);

    IDomSelection trail(String identifier);

    void setContent(String content);

    void addClass(String className);

    void removeClass(String className);
}
