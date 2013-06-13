package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.doctype.DoctypeDeclaration;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

public interface IDomHtmlRootSteerable<R extends IDomHtmlRootSteerable<R>> {

    @Nonnull
    R setTile(@Nonnull CharSequence charSequence);

    @Nonnull
    String getTitle();

    @Nonnull
    R clearTitle();

    ElementNode getTitleNode();

    @Nonnull
    ElementNode getRootNode();

    ElementNode getBodyNode();

    ElementNode getHeadNode();

    DoctypeDeclaration getDoctypeDeclaration();

    R setDoctypeDeclaration(DoctypeDeclaration doctypeDeclaration);
}
