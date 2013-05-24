package no.kantega.lab.limber.dom.implementation.jsoup;

import no.kantega.lab.limber.page.WebPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DomSelection extends AbstractDomSelection {

    public DomSelection(WebPage webPage, Document document, Elements elements) {
        super(webPage, document, elements);
    }
}
