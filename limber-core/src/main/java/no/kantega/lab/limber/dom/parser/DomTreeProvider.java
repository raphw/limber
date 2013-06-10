package no.kantega.lab.limber.dom.parser;

import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.servlet.IRenderable;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.cyberneko.html.HTMLConfiguration;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
import java.io.IOException;
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

    public HtmlDocumentRootSelection provideDocumentSelection(@Nonnull Class<? extends IRenderable> renderableClass) {
        InputStream resourceInputStream = renderableResourceLocator.locateResource(renderableClass);
        HTMLConfiguration htmlConfiguration = new HTMLConfiguration();
        htmlConfiguration.setFeature("http://cyberneko.org/html/features/balance-tags", true);
        AbstractSAXParser htmlSAXParser = new SaxParser(htmlConfiguration);
        DomGenerationContentHandler domContentHandler = new DomGenerationContentHandler();
        htmlSAXParser.setContentHandler(domContentHandler);
        try {
            htmlSAXParser.parse(new InputSource(resourceInputStream));
            return new HtmlDocumentRootSelection(domContentHandler.getRoot());
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class SaxParser extends AbstractSAXParser {
        private SaxParser(@Nonnull XMLParserConfiguration xmlParserConfiguration) {
            super(xmlParserConfiguration);
        }
    }

}
