package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.LinkNode;
import no.kantega.lab.limber.dom.target.ITargetable;
import no.kantega.lab.limber.servlet.AbstractRenderable;

import javax.annotation.Nonnull;
import java.net.URI;

public interface IDomLinkNodeMorphable<N extends LinkNode<? extends N>> extends IDomElementNodeMorphable<N> {

    @Nonnull
    IDomLinkNodeRepresentable<N> setTarget(@Nonnull CharSequence uri);

    @Nonnull
    IDomLinkNodeRepresentable<N> setTarget(@Nonnull URI uri);

    @Nonnull
    IDomLinkNodeRepresentable<N> setTarget(@Nonnull Class<? extends AbstractRenderable> clazz);

    @Nonnull
    IDomLinkNodeRepresentable<N> setTarget(@Nonnull ITargetable<?> targetable);

    @Nonnull
    IDomLinkNodeRepresentable<N> clearTarget();

}
