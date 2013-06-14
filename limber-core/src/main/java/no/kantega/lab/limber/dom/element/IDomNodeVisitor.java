package no.kantega.lab.limber.dom.element;

public interface IDomNodeVisitor<N extends AbstractNode<? extends N>> {

    public enum VisitingStickyMode {
        BEFORE_RENDER,
        AFTER_RENDER,
        BOTH
    }

    void visit(N node);
}
