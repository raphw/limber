package no.kantega.lab.limber.dom.parser;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.TextNode;
import no.kantega.lab.limber.util.IStack;
import no.kantega.lab.limber.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class DomGenerationContentHandler implements ContentHandler {

    private ElementNode root;

    private final IStack<ElementNode> elementNodeStack;

    public DomGenerationContentHandler() {
        elementNodeStack = new Stack<ElementNode>();
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        System.out.println("setDocumentLocator " + locator);
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("startDocument");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("endDocument");
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        System.out.println("startPrefixMapping " + prefix + uri);
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        System.out.println("endPrefixMapping " + prefix);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        ElementNode elementNode = new ElementNode(qName);
        for (int i = 0; i < atts.getLength(); i++) {
            elementNode.putAttr(atts.getQName(i), atts.getValue(i));
        }
        if (root == null) {
            root = elementNode;
        } else {
            ElementNode parentElementNode = elementNodeStack.peek();
            parentElementNode.appendChildAndStay(elementNode);
        }
        elementNodeStack.push(elementNode);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        elementNodeStack.pop();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        whitespace(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        whitespace(ch, start, length);
    }

    private void whitespace(char[] ch, int start, int length) throws SAXException {
        TextNode textNode = new TextNode(new String(ch, start, length));
        elementNodeStack.peek().appendChildAndStay(textNode);
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        System.out.println("processingInstruction " + target + ", " + data);
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        System.out.println("skippedEntity " + name);
    }

    public ElementNode getRoot() {
        return root;
    }
}
