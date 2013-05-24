package no.kantega.lab.limber.dom.abstraction.selection;

public interface IDomElementSelection extends IDomSelection<IDomElementSelection> {

    IDomSelection remove();

    IDomElementSelection setContent(String content);

    IDomElementSelection addCssClass(String className);

    IDomElementSelection removeCssClass(String className);

}
