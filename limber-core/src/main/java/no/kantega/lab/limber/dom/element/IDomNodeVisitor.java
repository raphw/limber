package no.kantega.lab.limber.dom.element;

public interface IDomNodeVisitor<N extends AbstractNode<?>> {

    public enum VisitingStickyMode {
        BEFORE_RENDER,
        AFTER_RENDER,
        BOTH
    }

    void visit(N node);
}
