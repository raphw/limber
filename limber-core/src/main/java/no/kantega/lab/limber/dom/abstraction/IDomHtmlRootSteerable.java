package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.doctype.DoctypeDeclaration;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.selection.IHtmlDocumentRootSelection;

import javax.annotation.Nonnull;

public interface IDomHtmlRootSteerable {

    @Nonnull
    IHtmlDocumentRootSelection setTile(@Nonnull CharSequence charSequence);

    @Nonnull
    String getTitle();

    @Nonnull
    IHtmlDocumentRootSelection clearTitle();

    ElementNode<?> getTitleNode();

    @Nonnull
    ElementNode<?> getRootNode();

    ElementNode<?> getBodyNode();

    ElementNode<?> getHeadNode();

    DoctypeDeclaration getDoctypeDeclaration();

    IHtmlDocumentRootSelection setDoctypeDeclaration(DoctypeDeclaration doctypeDeclaration);
}
