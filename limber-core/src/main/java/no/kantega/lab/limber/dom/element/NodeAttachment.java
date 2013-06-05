package no.kantega.lab.limber.dom.element;

public interface NodeAttachment<N extends AbstractNode<N>> {

    void visit(N node);
}
