package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomTextNodeMorphable;
import no.kantega.lab.limber.dom.element.TextNode;

import javax.annotation.Nonnull;
import java.util.List;

public class TextNodeSelection extends NodeSelection<TextNode, TextNodeSelection> implements IDomTextNodeMorphable<TextNodeSelection> {

    public TextNodeSelection(@Nonnull List<TextNode> selected) {
        super(selected);
    }

    public TextNodeSelection(@Nonnull NodeSelection<TextNode, ?> that) {
        super(that);
    }

    @Override
    public TextNodeSelection get(int from, int to) {
        return new TextNodeSelection(super.get(from, to));
    }
}
