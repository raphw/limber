package no.kantega.lab.limber.page.context;

import no.kantega.lab.limber.dom.doctype.DoctypeDeclaration;

public interface IHtmlRenderOptions {

    DoctypeDeclaration getDoctypeDeclaration();

    int getCurrentTreeDepth();

    boolean isWhiteSpaceCompressed();

    boolean isWhiteSpaceImmaterial();
}
