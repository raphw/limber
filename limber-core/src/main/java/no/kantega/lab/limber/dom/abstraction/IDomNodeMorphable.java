package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.NodeAttachment;

import javax.annotation.Nonnull;

public interface IDomNodeMorphable<T extends IDomNodeMorphable<?, S>, S extends AbstractNode<S>> {

    T clear();

    T setRendered(boolean render);

    T setContent(CharSequence content);

    T setContent(CharSequence content, @Nonnull ContentEscapeMode escapeMode);

    T remove();

    T addNodeAttachment(@Nonnull NodeAttachment<? extends S> nodeAttachment);

    T removeNodeAttachment(@Nonnull NodeAttachment<? extends S> nodeAttachment);
}
