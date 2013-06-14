package no.kantega.lab.limber.dom.parser;

import no.kantega.lab.limber.doctype.DoctypeDeclaration;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.selection.HtmlDocumentRootSelection;
import no.kantega.lab.limber.dom.selection.IHtmlDocumentRootSelection;
import no.kantega.lab.limber.exception.LimberParsingException;
import no.kantega.lab.limber.servlet.IRenderable;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.cyberneko.html.HTMLConfiguration;
import org.xml.sax.*;

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

    public IHtmlDocumentRootSelection provideDocumentSelection(@Nonnull Class<? extends IRenderable> renderableClass) {
        InputStream resourceInputStream = renderableResourceLocator.locateResource(renderableClass);
        RetrievableDomRootElementNodeContainer rootElementNodeContainer = new RetrievableDomRootElementNodeContainer();
        XMLReader xmlReader = createParser(rootElementNodeContainer);
        try {
            xmlReader.parse(new InputSource(resourceInputStream));
            return new HtmlDocumentRootSelection(
                    rootElementNodeContainer.getRoot(),
                    rootElementNodeContainer.getDoctypeDeclaration());
        } catch (SAXException e) {
            throw new LimberParsingException(e);
        } catch (IOException e) {
            throw new LimberParsingException(e);
        }
    }

    private XMLReader createParser(IDomRootElementNodeContainer rootElementNodeContainer) {

        HTMLConfiguration htmlConfiguration = new HTMLConfiguration();
        htmlConfiguration.setFeature("http://cyberneko.org/html/features/balance-tags", true);

        AbstractSAXParser htmlSAXParser = new SaxParser(htmlConfiguration);

        DomGenerationDelegate domGenerationDelegate = new DomGenerationDelegate(rootElementNodeContainer);
        htmlSAXParser.setContentHandler(domGenerationDelegate);
        try {
            htmlSAXParser.setProperty("http://xml.org/sax/properties/lexical-handler", domGenerationDelegate);
        } catch (SAXNotRecognizedException e) {
            throw new LimberParsingException(e);
        } catch (SAXNotSupportedException e) {
            throw new LimberParsingException(e);
        }

        return htmlSAXParser;
    }

    private class SaxParser extends AbstractSAXParser {
        private SaxParser(@Nonnull XMLParserConfiguration xmlParserConfiguration) {
            super(xmlParserConfiguration);
        }
    }

    private class RetrievableDomRootElementNodeContainer implements IDomRootElementNodeContainer {

        private ElementNode<?> root;

        private DoctypeDeclaration doctypeDeclaration;

        @Override
        public void setRoot(@Nonnull ElementNode<?> root) {
            this.root = root;
        }

        private ElementNode<?> getRoot() {
            return root;
        }

        @Override
        public void setDoctype(@Nonnull DoctypeDeclaration doctypeDeclaration) {
            this.doctypeDeclaration = doctypeDeclaration;
        }

        public DoctypeDeclaration getDoctypeDeclaration() {
            return doctypeDeclaration;
        }
    }
}
