package no.kantega.lab.limber.dom.abstraction.selection;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.abstraction.element.IDomElement;

public interface IDomSelection<T extends IDomSelection> {

    IDomElementSelection tag(String identifier);

    IDomElementSelection id(String identifier);

    IDomElementSelection cssClass(String identifier);

    IDomElementSelection cssClass(String[] identifier);

    int size();

    T setContent(String content);

    T addCssClass(String className);

    T removeCssClass(String className);

    IDomElementSelection prependChild(IDomElement element);

    IDomElementSelection appendChild(IDomElement element);

    IDomElementSelection addChild(IDomElement element, int index);

    T removeAllChildren();

    T ajax(AjaxEventTrigger ajaxEvent, IAjaxCallback ajaxCallback);

}
