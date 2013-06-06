package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.NodeAttachment;

import javax.annotation.Nonnull;

public interface IDomNodeMorphable<N extends AbstractNode<N>, M extends IDomNodeMorphable<N, ?>> extends Cloneable {

    @Nonnull
    M clear();

    @Nonnull
    M setRendered(boolean render);

    @Nonnull
    M setContent(CharSequence content);

    @Nonnull
    M setContent(CharSequence content, @Nonnull ContentEscapeMode escapeMode);

    @Nonnull
    M remove();

    @Nonnull
    M addNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment);

    @Nonnull
    M removeNodeAttachment(@Nonnull NodeAttachment<? extends N> nodeAttachment);

    @Nonnull
    M clone();
}
