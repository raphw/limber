package no.kantega.lab.limber.dom.implementation.limber.renderer;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;
import no.kantega.lab.limber.dom.implementation.limber.element.TextNode;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class DomTreeRenderer {

    public void render(AbstractNode<?> parent, OutputStream outputStream) {
        Writer writer = new OutputStreamWriter(outputStream);
        try {
            render(parent, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void render(AbstractNode<?> parent, Writer writer) throws IOException {
        if (parent instanceof ElementNode) {
            renderElement((ElementNode) parent, writer);
        } else if (parent instanceof TextNode) {
            renderElement((TextNode) parent, writer);
        } else {
            renderElement(parent, writer);
        }
    }

    private void renderElement(ElementNode elementNode, Writer writer) throws IOException {
        writer.append('<');
        writer.append(elementNode.getTagName());
        for (CharSequence charSequence : elementNode.attrs()) {
            writer.append(' ');
            writer.append(charSequence);
            writer.append("=\"");
            writer.append(elementNode.getAttr(charSequence));
            writer.append("\"");
        }
        writer.append('>');

        for (AbstractNode<?> child : elementNode.children()) {
            render(child, writer);
        }

        writer.append("</");
        writer.append(elementNode.getTagName());
        writer.append(">");
    }

    private void renderElement(TextNode textNode, Writer writer) throws IOException {
        writer.append(textNode.getContent());
    }

    private void renderElement(AbstractNode<?> value, Writer writer) throws IOException {
        writer.append(value.toString());
    }
}
