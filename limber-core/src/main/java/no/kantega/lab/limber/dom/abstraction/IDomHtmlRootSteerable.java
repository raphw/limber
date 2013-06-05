package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

public interface IDomHtmlRootSteerable<R extends IDomHtmlRootSteerable<R>> {

    R setTile(@Nonnull CharSequence charSequence);

    String getTitle();

    R clearTitle();

    ElementNode getTitleNode();

    ElementNode getRootNode();

    ElementNode getBodyNode();

    ElementNode getHeadNode();
}
