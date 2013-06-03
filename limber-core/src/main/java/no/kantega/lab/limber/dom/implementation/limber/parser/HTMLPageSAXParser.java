package no.kantega.lab.limber.dom.implementation.limber.parser;

import no.kantega.lab.limber.dom.abstraction.selection.IDomDocumentSelection;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.cyberneko.html.HTMLConfiguration;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

public class HtmlPageSAXParser extends AbstractSAXParser {

    public static IDomDocumentSelection parse(InputStream inputStream) {
        HtmlPageSAXParser parser = new HtmlPageSAXParser(new HtmlContentHandler());
        try {
            parser.parse(new InputSource(inputStream));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected HtmlPageSAXParser(ContentHandler contentHandler) {
        super(new HTMLConfiguration());
        this.setContentHandler(contentHandler);
    }

}