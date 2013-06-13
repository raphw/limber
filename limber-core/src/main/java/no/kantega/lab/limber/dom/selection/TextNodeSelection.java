package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.abstraction.IDomTextNodeMorphable;
import no.kantega.lab.limber.dom.element.TextNode;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.List;

public class TextNodeSelection extends NodeSelection<TextNode, TextNodeSelection> implements IDomTextNodeMorphable<TextNode, TextNodeSelection> {

    public TextNodeSelection(@Nonnull List<TextNode> selected) {
        super(selected);
    }

    public TextNodeSelection(@Nonnull NodeSelection<TextNode, ?> that) {
        super(that);
    }

    public TextNodeSelection(@Nonnull LinkedHashSet<TextNode> selected) {
        super(selected);
    }

    @Nonnull
    @Override
    public TextNodeSelection get(int from, int to) {
        return new TextNodeSelection(super.get(from, to));
    }
}
