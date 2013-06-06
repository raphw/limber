package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;

import javax.annotation.Nonnull;

public class BooleanRepeaterFilter<N extends AbstractNode<?>> implements INodeFilter<N> {

    private final boolean answer;

    public BooleanRepeaterFilter(boolean answer) {
        this.answer = answer;
    }

    @Override
    public boolean filter(@Nonnull N element) {
        return answer;
    }
}
