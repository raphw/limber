package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomTextNodeMorphable;
import no.kantega.lab.limber.dom.element.TextNode;

import java.util.List;

public class TextNodeSelection extends NodeSelection<TextNodeSelection, TextNode> implements IDomTextNodeMorphable<TextNodeSelection> {

    public TextNodeSelection(List<TextNode> selected) {
        super(selected);
    }

    public TextNodeSelection(NodeSelection<?, TextNode> that) {
        super(that);
    }

    @Override
    public TextNodeSelection setContent(CharSequence content) {
        for (TextNode textNode : getSelected()) {
            textNode.setContent(content);
        }
        return this;
    }

    @Override
    public TextNodeSelection get(int from, int to) {
        return new TextNodeSelection(super.get(from, to));
    }
}
