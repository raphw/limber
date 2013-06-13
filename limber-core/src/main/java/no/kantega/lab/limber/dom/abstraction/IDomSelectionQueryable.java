package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.dom.element.AbstractNode;

import javax.annotation.Nonnull;
import java.util.List;

public interface IDomSelectionQueryable<N extends AbstractNode<? extends N>> {

    int size();

    @Nonnull
    List<N> nodeList();

    boolean isSingleRoot();
}
