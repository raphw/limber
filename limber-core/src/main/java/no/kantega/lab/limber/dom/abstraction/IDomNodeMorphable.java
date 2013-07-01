package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeStrategy;
import no.kantega.lab.limber.dom.element.IDomNodeVisitor;

import javax.annotation.Nonnull;

public interface IDomNodeMorphable<N extends AbstractNode<? extends N>> {

    @Nonnull
    IDomNodeRepresentable<N> clear();

    @Nonnull
    IDomNodeRepresentable<N> setRendered(boolean render);

    @Nonnull
    IDomNodeRepresentable<N> setContent(CharSequence content);

    @Nonnull
    IDomNodeRepresentable<N> setContent(CharSequence content, @Nonnull ContentEscapeStrategy contentEscapeStrategy);

    @Nonnull
    IDomNodeRepresentable<N> remove();

    @Nonnull
    <N2 extends AbstractNode<? extends N2>> IDomNodeRepresentable<? extends N2> replaceBy(@Nonnull N2 node);

    @Nonnull
    IDomNodeRepresentable<N> replaceByAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    IDomNodeRepresentable<N> clone();

    @Nonnull
    IDomNodeRepresentable<N> visit(@Nonnull IDomNodeVisitor<? super N> visitor);
}
