package no.kantega.lab.limber.dom.implementations;

import no.kantega.lab.limber.dom.interfaces.IDomDocumentSelection;
import no.kantega.lab.limber.dom.interfaces.IDomSelection;
import no.kantega.lab.limber.general.NotYetImplementedException;
import no.kantega.lab.limber.page.WebPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DomDocumentSelection implements IDomDocumentSelection {

    private final WebPage webPage;
    private final Document doc;

    public DomDocumentSelection(WebPage webPage) {
        this.webPage = webPage;
        try {
            doc = Jsoup.parse(webPage.getDocumentResourceStream(), "UTF-8", "http://localhost:8080");
        } catch (IOException e) {
            throw new RuntimeException("Could not parse file.");
        }
    }

    @Override
    public IDomSelection tag(String identifier) {
        Elements elements = doc.getElementsByTag(identifier);
        return new DomElementSelection(elements);
    }

    @Override
    public IDomSelection id(String identifier) {
        throw new NotYetImplementedException();
    }

    @Override
    public IDomSelection trail(String identifier) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setContent(String content) {
        throw new NotYetImplementedException();
    }

    @Override
    public void addClass(String className) {
        throw new NotYetImplementedException();
    }

    @Override
    public void removeClass(String className) {
        throw new NotYetImplementedException();
    }

    @Override
    public String getOutput() {
        return doc.outerHtml();
    }
}
