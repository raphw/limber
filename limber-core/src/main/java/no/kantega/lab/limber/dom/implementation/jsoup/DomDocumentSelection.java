package no.kantega.lab.limber.dom.implementation.jsoup;

import no.kantega.lab.limber.dom.abstraction.selection.HeadResource;
import no.kantega.lab.limber.dom.abstraction.selection.IDomDocumentSelection;
import no.kantega.lab.limber.dom.implementation.jsoup.util.ResourceNodeHelper;
import no.kantega.lab.limber.general.NotYetImplementedException;
import no.kantega.lab.limber.page.WebPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;

public class DomDocumentSelection extends AbstractDomSelection<IDomDocumentSelection>
        implements IDomDocumentSelection {

    public static DomDocumentSelection makeDomDocumentSelection(WebPage webPage) {
        Document document;
        try {
            document = Jsoup.parse(webPage.getDocumentResourceStream(), "UTF-8", "http://localhost:8080");
        } catch (IOException e) {
            throw new RuntimeException("Could not parse file.");
        }
        return new DomDocumentSelection(webPage, document);
    }

    private DomDocumentSelection(WebPage webPage, Document document) {
        super(webPage, document, new Elements(document.body()));
    }

    @Override
    public String getOutput() {
        return getDocument().outerHtml();
    }

    @Override
    public void setTitle(String title) {
        getDocument().title(title);
    }

    @Override
    public String getTitle() {
        return getDocument().title();
    }

    @Override
    public IDomDocumentSelection addExternalResource(HeadResource headerResource, URI resourceLocation) {
        switch (headerResource) {
            case CSS:
                getDocument().head().appendChild(ResourceNodeHelper.makeCssHeadExternalLink(resourceLocation));
                break;
            case JS:
                getDocument().head().appendChild(ResourceNodeHelper.makeJsHeadExternalLink(resourceLocation));
                break;
            default:
                throw new NotYetImplementedException("Cannot find HeadResource identifier: " + headerResource);
        }
        return this;
    }

    @Override
    public IDomDocumentSelection addEmbededResource(HeadResource headerResource, String value) {
        switch (headerResource) {
            case CSS:
                getDocument().head().appendChild(ResourceNodeHelper.makeCssHeadEmbededLink(value));
                break;
            case JS:
                getDocument().head().appendChild(ResourceNodeHelper.makeJsHeadEmbededlLink(value));
                break;
            default:
                throw new NotYetImplementedException("Cannot find HeadResource identifier: " + headerResource);
        }
        return this;
    }
}
