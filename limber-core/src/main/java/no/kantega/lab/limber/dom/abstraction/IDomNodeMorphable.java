package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.NodeAttachment;

import javax.annotation.Nonnull;

public interface IDomNodeMorphable<N extends AbstractNode<N>, M extends IDomNodeMorphable<N, ?>> extends Cloneable {

    M clear();

    M setRendered(boolean render);

    M setContent(CharSequence content);

    M setContent(CharSequence content, @Nonnull ContentEscapeMode escapeMode);

    M remove();

    M addNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment);

    M removeNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment);

    M clone();
}
