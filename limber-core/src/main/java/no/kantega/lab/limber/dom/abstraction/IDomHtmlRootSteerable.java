package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

public interface IDomHtmlRootSteerable<T extends IDomHtmlRootSteerable<T>> {

    T setTile(@Nonnull CharSequence charSequence);

    String getTitle();

    T clearTitle();

    ElementNode getTitleNode();

    ElementNode getRootNode();

    ElementNode getBodyNode();

    ElementNode getHeadNode();
}
