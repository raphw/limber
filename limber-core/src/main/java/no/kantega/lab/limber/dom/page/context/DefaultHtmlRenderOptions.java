package no.kantega.lab.limber.dom.page.context;

import no.kantega.lab.limber.dom.doctype.DoctypeDeclaration;
import no.kantega.lab.limber.exception.NotYetImplementedException;

public class DefaultHtmlRenderOptions implements IHtmlRenderOptions {

    @Override
    public DoctypeDeclaration getDoctypeDeclaration() {
        throw new NotYetImplementedException();
    }

    @Override
    public int getCurrentTreeDepth() {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean isWhiteSpaceCompressed() {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean isWhiteSpaceImmaterial() {
        throw new NotYetImplementedException();
    }
}
