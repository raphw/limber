package no.kantega.lab.limber.dom.implementations;

import no.kantega.lab.limber.dom.interfaces.IDomElementSelection;
import no.kantega.lab.limber.dom.interfaces.IDomSelection;
import no.kantega.lab.limber.general.NotYetImplementedException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DomElementSelection implements IDomElementSelection {

    private final Elements elements;

    public DomElementSelection(Elements elements) {
        this.elements = elements;
    }

    @Override
    public IDomSelection tag(String identifier) {
        Elements elements = new Elements();
        for (Element e : elements) {
            elements.addAll(e.getElementsByTag(identifier));
        }
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
        for (Element e : elements) {
            e.html(content);
        }
    }

    @Override
    public void addClass(String className) {
        throw new NotYetImplementedException();
    }

    @Override
    public void removeClass(String className) {
        throw new NotYetImplementedException();
    }
}
