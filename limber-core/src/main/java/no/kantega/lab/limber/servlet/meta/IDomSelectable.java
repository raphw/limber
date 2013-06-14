package no.kantega.lab.limber.servlet.meta;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.selection.INodeSelection;

public interface IDomSelectable<N extends AbstractNode<? extends N>> {

    INodeSelection<N> dom();
}
