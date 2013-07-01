package no.kantega.lab.limber.example.page;

import no.kantega.lab.limber.dom.ajax.AjaxEventTrigger;
import no.kantega.lab.limber.dom.ajax.IAjaxCallback;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.LinkNode;
import no.kantega.lab.limber.dom.element.PlainLinkNode;
import no.kantega.lab.limber.dom.page.WebPage;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.servlet.meta.RequestMapping;

import java.util.Date;

@RequestMapping("/")
public class TestWebPage extends WebPage {

    public TestWebPage() {

        dom().setTile("Limber framework");

        dom().findByTag("h2").setContent("Hello limber framework");

        ElementNodeSelection<?, ?> selection = dom().findByTag("ul").clear();
        for (String s : new String[]{"It is easy to use", "It is elegant to use", "Out-of-the-box jQuery"}) {
            selection.appendChild("li").setContent(s);
        }
        selection.setRandomIdIfNone();

        dom().findByTag("button").setContent("Ajax demo").setRandomIdIfNone().addAjaxEvent(AjaxEventTrigger.CLICK, new IAjaxCallback<ElementNode<?>>() {
            @Override
            public void onEvent(AjaxEventTrigger ajaxEventTrigger, ElementNode<?> eventTarget) {
                System.out.println("Ajax!");
                dom().findByTag("h2").setContent("Ajax action! (" + new Date().getTime() + ")");
            }
        });

        LinkNode<?> link = new PlainLinkNode("a").setContent("Dynamic link").setTarget("/js/jquery-1.10.1.min.js");
        dom().getBodyNode().appendChild(link);

        dom().findByTag("ol").remove();

    }
}
