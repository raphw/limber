package no.kantega.lab.limber.dom.implementation.limber.selection;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.abstraction.element.IDomElement;
import no.kantega.lab.limber.dom.abstraction.selection.HeadResource;
import no.kantega.lab.limber.dom.abstraction.selection.IDomDocumentSelection;
import no.kantega.lab.limber.dom.abstraction.selection.IDomElementSelection;

import java.net.URI;

public class HtmlDocumentSelection implements IDomDocumentSelection {

    @Override
    public void setTitle(String title) {
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getOutput() {
        return null;
    }

    @Override
    public IDomDocumentSelection addExternalResource(HeadResource headerResource, URI resourceLocation) {
        return null;
    }

    @Override
    public IDomDocumentSelection addEmbededResource(HeadResource headerResource, String value) {
        return null;
    }

    @Override
    public IDomElementSelection tag(String identifier) {
        return null;
    }

    @Override
    public IDomElementSelection id(String identifier) {
        return null;
    }

    @Override
    public IDomElementSelection cssClass(String identifier) {
        return null;
    }

    @Override
    public IDomElementSelection cssClass(String[] identifier) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public IDomDocumentSelection setContent(String content) {
        return null;
    }

    @Override
    public IDomDocumentSelection addCssClass(String className) {
        return null;
    }

    @Override
    public IDomDocumentSelection removeCssClass(String className) {
        return null;
    }

    @Override
    public IDomElementSelection prependChild(IDomElement element) {
        return null;
    }

    @Override
    public IDomElementSelection appendChild(IDomElement element) {
        return null;
    }

    @Override
    public IDomElementSelection addChild(IDomElement element, int index) {
        return null;
    }

    @Override
    public IDomDocumentSelection removeAllChildren() {
        return null;
    }

    @Override
    public IDomDocumentSelection ajax(AjaxEventTrigger ajaxEvent, IAjaxCallback ajaxCallback) {
        return null;
    }
}
