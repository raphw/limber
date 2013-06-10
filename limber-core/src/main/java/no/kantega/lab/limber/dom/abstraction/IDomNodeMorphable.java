package no.kantega.lab.limber.dom.abstraction;

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

//    @Nonnull
//    M visit(@Nonnull IDomNodeVisitor<? super N> nodeVisitor);
//
//    @Nonnull
//    M addStickyNodeVisitor(@Nonnull IDomNodeVisitor<? super N> nodeVisitor, @Nonnull IDomNodeVisitor.VisitingStickyMode visitingStickyMode);
//
//    @Nonnull
//    M removeStickyNodeVisitor(@Nonnull IDomNodeVisitor<?> nodeVisitor);

    @Nonnull
    M clone();
}
