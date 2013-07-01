package no.kantega.lab.limber.dom.comparison;

import no.kantega.lab.limber.dom.element.ElementNode;

public class DefaultReplacementStep implements IReplacementStep {

    private final ReplacementStrategy replacementStrategy;

    private final ElementNode<?> from, to;

    public DefaultReplacementStep(ReplacementStrategy replacementStrategy, ElementNode<?> from, ElementNode<?> to) {
        this.replacementStrategy = replacementStrategy;
        this.from = from;
        this.to = to;
    }

    @Override
    public ReplacementStrategy getReplacementStrategy() {
        return replacementStrategy;
    }

    public ElementNode<?> getFromNode() {
        return from;
    }

    @Override
    public ElementNode<?> getToNode() {
        return to;
    }
}
