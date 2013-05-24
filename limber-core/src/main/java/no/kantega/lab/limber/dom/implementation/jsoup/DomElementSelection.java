package no.kantega.lab.limber.dom.implementation.jsoup;

import no.kantega.lab.limber.dom.abstraction.selection.IDomElementSelection;
import no.kantega.lab.limber.dom.abstraction.selection.IDomSelection;
import no.kantega.lab.limber.page.WebPage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DomElementSelection extends AbstractDomSelection<IDomElementSelection>
        implements IDomElementSelection {

    protected DomElementSelection(WebPage webPage, Document document, Elements selectedElements) {
        super(webPage, document, selectedElements);
    }

    @Override
    public IDomSelection remove() {
        Elements parents = new Elements();
        for (Element e : getSelectedElements()) {
            parents.add(e.parent());
            e.remove();
        }
        return new DomSelection(getWebPage(), getDocument(), parents);
    }
}
