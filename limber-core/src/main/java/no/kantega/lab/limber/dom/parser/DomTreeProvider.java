package no.kantega.lab.limber.dom.parser;

import no.kantega.lab.limber.dom.selection.HtmlDocumentSelection;
import no.kantega.lab.limber.servlet.IRenderable;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.cyberneko.html.HTMLConfiguration;

import java.io.InputStream;

public class DomTreeProvider {

    private static final DomTreeProvider instance = new DomTreeProvider();

    public static DomTreeProvider getInstance() {
        return instance;
    }

    private final RenderableResourceLocator renderableResourceLocator;

    private DomTreeProvider() {
        renderableResourceLocator = new RenderableResourceLocator();
    }

    public HtmlDocumentSelection provideDocumentSelection(Class<? extends IRenderable> renderableClass) {
        InputStream resourceInputStream = renderableResourceLocator.locateResource(renderableClass);
        HTMLConfiguration htmlConfiguration = new HTMLConfiguration();
        htmlConfiguration.setFeature("http://cyberneko.org/html/features/balance-tags", true);
        AbstractSAXParser htmlSAXParser = new SaxParser(htmlConfiguration);
        DomGenerationContentHandler domContentHandler = new DomGenerationContentHandler();
        htmlSAXParser.setContentHandler(domContentHandler);
        return new HtmlDocumentSelection(domContentHandler.getRoot());
    }

    private class SaxParser extends AbstractSAXParser {
        private SaxParser(XMLParserConfiguration xmlParserConfiguration) {
            super(xmlParserConfiguration);
        }
    }

}
