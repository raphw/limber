package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.selection.INodeSelection;

import javax.annotation.Nonnull;

public interface IDomSelectable<N extends AbstractNode<? extends N>> {

    @Nonnull
    INodeSelection<N> dom();
}
