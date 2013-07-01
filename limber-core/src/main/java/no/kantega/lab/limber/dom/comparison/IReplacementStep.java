package no.kantega.lab.limber.dom.comparison;

import no.kantega.lab.limber.dom.element.ElementNode;

public interface IReplacementStep {

    ReplacementStrategy getReplacementStrategy();

    ElementNode<?> getFromNode();

    ElementNode<?> getToNode();
}
