package no.kantega.lab.limber.dom.parser;

import no.kantega.lab.limber.dom.element.ElementNode;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.cyberneko.html.HTMLConfiguration;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

public class HtmlSAXParser extends AbstractSAXParser {

    private final DomGenerationContentHandler domGenerationContentHandler;

    public static HtmlSAXParser make() {
        HTMLConfiguration htmlConfiguration = new HTMLConfiguration();
        htmlConfiguration.setFeature("http://cyberneko.org/html/features/balance-tags", true);
        return new HtmlSAXParser(htmlConfiguration);
    }

    private HtmlSAXParser(HTMLConfiguration htmlConfiguration) {
        super(htmlConfiguration);
        domGenerationContentHandler = new DomGenerationContentHandler();
        this.setContentHandler(domGenerationContentHandler);
    }

    public ElementNode translateToDomTree(InputStream inputStream) {
        try {
            this.parse(new InputSource(inputStream));
            return domGenerationContentHandler.getRoot();
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
