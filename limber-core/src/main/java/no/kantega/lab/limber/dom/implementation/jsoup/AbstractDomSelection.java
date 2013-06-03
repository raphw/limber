package no.kantega.lab.limber.dom.implementation.jsoup;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.abstraction.selection.IDomElementSelection;
import no.kantega.lab.limber.dom.abstraction.selection.IDomSelection;
import no.kantega.lab.limber.page.WebPage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;

public abstract class AbstractDomSelection<T extends IDomSelection> implements IDomSelection<T> {

    private final Document document;
    private final Elements selectedElements;
    private final WebPage webPage;

    public AbstractDomSelection(WebPage webPage, Document document, Elements selectedElements) {
        this.document = document;
        this.selectedElements = selectedElements;
        this.webPage = webPage;
    }

    protected Elements getSelectedElements() {
        return selectedElements;
    }

    protected Document getDocument() {
        return document;
    }

    protected WebPage getWebPage() {
        return webPage;
    }

    @Override
    public IDomElementSelection tag(String identifier) {
        Elements elements = new Elements();
        for (Element e : getSelectedElements()) {
            elements.addAll(e.getElementsByTag(identifier));
        }
        return new DomElementSelection(getWebPage(), getDocument(), elements);
    }

    @Override
    public IDomElementSelection id(String identifier) {
        Elements elements = new Elements();
        for (Element e : getSelectedElements()) {
            elements.addAll(e.getElementsByAttributeValue("id", identifier));
        }
        return new DomElementSelection(getWebPage(), getDocument(), elements);
    }

    @Override
    public IDomElementSelection cssClass(String identifier) {
        Elements elements = new Elements();
        for (Element e : getSelectedElements()) {
            elements.addAll(e.getElementsByClass(identifier));
        }
        return new DomElementSelection(getWebPage(), getDocument(), elements);
    }

    @Override
    public IDomElementSelection cssClass(String[] identifier) {
        if (identifier.length == 0) {
            throw new IllegalArgumentException("You must specify at least one CSS class");
        }
        Elements elements = new Elements();
        for (Element e : getSelectedElements()) {
            Elements prefilteredElements = e.getElementsByClass(identifier[0]);
            for (int i = 1; i < identifier.length; i++) {
                prefilteredElements.retainAll(e.getElementsByClass(identifier[i]));
            }
            elements.addAll(prefilteredElements);
        }
        return new DomElementSelection(getWebPage(), getDocument(), elements);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setContent(String content) {
        for (Element e : getSelectedElements()) {
            e.html(content);
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T addCssClass(String className) {
        for (Element e : getSelectedElements()) {
            e.addClass(className);
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T removeCssClass(String className) {
        for (Element e : getSelectedElements()) {
            e.removeClass(className);
        }
        return (T) this;
    }

    @Override
    public IDomElementSelection prependChild(Element elementPrototype) {
        Elements elements = new Elements();
        for (Element e : getSelectedElements()) {
            Element child = elementPrototype.clone();
            e.prependChild(child);
            elements.add(child);
        }
        return new DomElementSelection(getWebPage(), getDocument(), elements);
    }

    @Override
    public IDomElementSelection appendChild(Element elementPrototype) {
        Elements elements = new Elements();
        for (Element e : getSelectedElements()) {
            Element child = elementPrototype.clone();
            e.appendChild(child);
            elements.add(child);
        }
        return new DomElementSelection(getWebPage(), getDocument(), elements);
    }

    @Override
    public IDomElementSelection addChild(Element elementPrototype, int index) {
        Elements elements = new Elements();
        for (Element e : getSelectedElements()) {
            Element child = elementPrototype.clone();
            e.insertChildren(index, Arrays.asList(child));
            elements.add(child);
        }
        return new DomElementSelection(getWebPage(), getDocument(), elements);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T removeAllChildren() {
        for (Element e : getSelectedElements()) {
            for (Element child : e.children()) {
                child.remove();
            }
        }
        return (T) this;
    }

    @Override
    public int size() {
        return getSelectedElements().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T ajax(AjaxEventTrigger ajaxEventTrigger, IAjaxCallback ajaxCallback) {
        for (Element e : getSelectedElements()) {
            getWebPage().registerAjaxEvent(e, ajaxEventTrigger, ajaxCallback);
        }
        return (T) this;
    }
}
