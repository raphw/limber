package no.kantega.lab.limber.dom.implementation.limber.element;

public interface NodeAttachment<T extends AbstractNode> {

    void visit(T node);
}
