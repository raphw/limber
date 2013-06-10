package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;

import javax.annotation.Nonnull;

public interface IDomNodeMorphable<M extends IDomNodeMorphable> extends Cloneable {

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
    <N extends AbstractNode> Object replaceBy(@Nonnull N node);

    @Nonnull
    M replaceByAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    M clone();
}
