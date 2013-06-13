package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.IDomNodeVisitor;

import javax.annotation.Nonnull;

public interface IDomNodeMorphable<N extends AbstractNode, M extends IDomNodeMorphable> extends Cloneable {

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
    <N2 extends AbstractNode> Object replaceBy(@Nonnull N2 node);

    @Nonnull
    M replaceByAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    M clone();

    @Nonnull
    M visit(@Nonnull IDomNodeVisitor<? super N> visitor);
}
