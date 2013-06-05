package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomHtmlRootSteerable;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.TextNode;

import java.util.Arrays;

public class HtmlDocumentSelection extends ElementNodeSelection implements IDomHtmlRootSteerable<HtmlDocumentSelection> {

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

    @Override
    public HtmlDocumentSelection setTile(CharSequence charSequence) {
        return this;
    }

    @Override
    public String getTitle() {
        ElementNode titleNode = getTitleNode();
        StringBuilder stringBuilder = new StringBuilder();
        for(TextNode textNode : titleNode.findTextNodes()) {
            stringBuilder.append(textNode.getContent());
        }
        return stringBuilder.toString();
    }

    @Override
    public ElementNode getTitleNode() {
        ElementNodeSelection titleNodeSelection = getHeadNode().findByTag("title");
        if(titleNodeSelection.size() != 1) {
            throw new IllegalStateException();
        }
        return titleNodeSelection.get(0);
    }
}
