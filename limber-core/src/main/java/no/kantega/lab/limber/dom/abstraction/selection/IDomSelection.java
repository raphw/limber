package no.kantega.lab.limber.dom.abstraction.selection;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import org.jsoup.nodes.Element;

public interface IDomSelection<T extends IDomSelection> {

    IDomElementSelection tag(String identifier);

    IDomElementSelection id(String identifier);

    IDomElementSelection cssClass(String identifier);

    IDomElementSelection cssClass(String[] identifier);

    int size();

    T setContent(String content);

    T addCssClass(String className);

    T removeCssClass(String className);

    IDomElementSelection prependChild(Element domElementPrototype);

    IDomElementSelection appendChild(Element domElementPrototype);

    IDomElementSelection addChild(Element domElementPrototype, int index);

    T removeAllChildren();

    T ajax(AjaxEventTrigger ajaxEvent, IAjaxCallback ajaxCallback);

}
