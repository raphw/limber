package no.kantega.lab.limber.dom.element;

public interface NodeAttachment<T extends AbstractNode> {

    void visit(T node);
}
