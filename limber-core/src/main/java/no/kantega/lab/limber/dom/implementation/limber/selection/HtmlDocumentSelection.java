package no.kantega.lab.limber.dom.implementation.limber.selection;

import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomHtmlRootSteerable;
import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;

import java.util.Arrays;

public class HtmlDocumentSelection extends ElementNodeSelection implements IDomHtmlRootSteerable {

    private final ElementNode rootNode;

    public HtmlDocumentSelection(ElementNode rootNode) {
        super(Arrays.asList(rootNode));
        this.rootNode = rootNode;
    }

    @Override
    public ElementNode getRootNode() {
        return rootNode;
    }

    @Override
    public ElementNode getBodyNode() {
        ElementNodeSelection selection = this.findByTag("body");
        if (selection.size() != 1) {
            throw new IllegalStateException();
        }
        return selection.get(0);
    }

    @Override
    public ElementNode getHeadNode() {
        ElementNodeSelection selection = this.findByTag("head");
        if (selection.size() != 1) {
            throw new IllegalStateException();
        }
        return selection.get(0);
    }

}
