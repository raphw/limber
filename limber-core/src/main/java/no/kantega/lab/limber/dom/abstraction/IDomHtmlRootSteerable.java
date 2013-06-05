package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.ElementNode;

public interface IDomHtmlRootSteerable<T extends IDomHtmlRootSteerable<T>> {

    T setTile(CharSequence charSequence);

    String getTitle();

    ElementNode getTitleNode();

    ElementNode getRootNode();

    ElementNode getBodyNode();

    ElementNode getHeadNode();
}
