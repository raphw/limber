package no.kantega.lab.limber.dom.parser;

import no.kantega.lab.limber.doctype.DoctypeDeclaration;
import no.kantega.lab.limber.doctype.DoctypeVisibility;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.ElementNodeFactory;
import no.kantega.lab.limber.dom.element.TextNode;
import no.kantega.lab.limber.exception.LimberParsingException;
import no.kantega.lab.limber.util.IStack;
import no.kantega.lab.limber.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DefaultHandler2;

import javax.annotation.Nonnull;

public class DomGenerationDelegate extends DefaultHandler2 {

    private final IDomRootElementNodeContainer rootNodeContainer;

    private final IStack<ElementNode<?>> elementNodeStack;

    private ElementNode<?> root;

    public DomGenerationDelegate(@Nonnull IDomRootElementNodeContainer rootNodeContainer) {
        elementNodeStack = new Stack<ElementNode<?>>();
        this.rootNodeContainer = rootNodeContainer;
    }

    @Override
    public void startElement(String uri, String localName, @Nonnull String qName, @Nonnull Attributes atts) throws SAXException {
        ElementNode<?> elementNode = ElementNodeFactory.make(qName);
        for (int i = 0; i < atts.getLength(); i++) {
            elementNode.putAttr(atts.getQName(i), atts.getValue(i));
        }
        if (root == null) {
            root = elementNode;
        } else {
            ElementNode<?> parentElementNode = elementNodeStack.peek();
            parentElementNode.appendChildAndStay(elementNode);
        }
        elementNodeStack.push(elementNode);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        elementNodeStack.pop();
    }

    @Override
    public void characters(@Nonnull char[] ch, int start, int length) throws SAXException {
        whitespace(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(@Nonnull char[] ch, int start, int length) throws SAXException {
        whitespace(ch, start, length);
    }

    private void whitespace(@Nonnull char[] ch, int start, int length) throws SAXException {
        TextNode textNode = new TextNode(new String(ch, start, length));
        elementNodeStack.peek().appendChildAndStay(textNode);
    }

    @Override
    public void startDTD(@Nonnull String name, String publicId, String systemId) throws SAXException {
        DoctypeVisibility doctypeVisibility = null;
        if (publicId != null) {
            doctypeVisibility = DoctypeVisibility.PUBLIC;
        } else if (systemId != null) {
            doctypeVisibility = DoctypeVisibility.SYSTEM;
        }
        rootNodeContainer.setDoctype(new DoctypeDeclaration(name, doctypeVisibility, publicId, systemId));
    }

    @Override
    public void endDocument() throws SAXException {
        rootNodeContainer.setRoot(root);
    }

    @Override
    public void fatalError(@Nonnull SAXParseException e) throws SAXException {
        throw new LimberParsingException(e);
    }
}
