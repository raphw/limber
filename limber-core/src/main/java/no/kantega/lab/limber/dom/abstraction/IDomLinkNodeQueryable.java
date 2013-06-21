package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.target.ITargetable;

import javax.annotation.Nonnull;

public interface IDomLinkNodeQueryable<N extends ElementNode<? extends N>> extends IDomElementNodeQueryable<N> {

    @Nonnull
    public ITargetable<?> getTarget();
}
