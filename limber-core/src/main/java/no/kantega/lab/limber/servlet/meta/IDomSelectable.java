package no.kantega.lab.limber.servlet.meta;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.selection.NodeSelection;

public interface IDomSelectable<T extends NodeSelection<?, S>, S extends AbstractNode<S>> {

    NodeSelection<?, S> dom();
}
