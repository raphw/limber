package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomHtmlRootSteerable;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.TextNode;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class HtmlDocumentRootSelection extends ElementNodeSelection implements IDomHtmlRootSteerable<HtmlDocumentRootSelection> {

    private final ElementNode rootNode;

    public HtmlDocumentRootSelection(@Nonnull ElementNode rootNode) {
        super(Arrays.asList(rootNode));
        this.rootNode = rootNode;
    }

    @Nonnull
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

    @Nonnull
    @Override
    public HtmlDocumentRootSelection setTile(@Nonnull CharSequence charSequence) {
        getTitleNode().clear().appendTextAndStay(charSequence);
        return this;
    }

    @Nonnull
    @Override
    public String getTitle() {
        ElementNode titleNode = getTitleNode();
        StringBuilder stringBuilder = new StringBuilder();
        for (TextNode textNode : titleNode.findTextNodes()) {
            stringBuilder.append(textNode.getContent());
        }
        return stringBuilder.toString();
    }

    @Nonnull
    @Override
    public HtmlDocumentRootSelection clearTitle() {
        getTitleNode().clear();
        return this;
    }

    @Override
    public ElementNode getTitleNode() {
        ElementNodeSelection titleNodeSelection = getHeadNode().findByTag("title");
        if (titleNodeSelection.size() != 1) {
            throw new IllegalStateException();
        }
        return titleNodeSelection.get(0);
    }
}
