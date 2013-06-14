package no.kantega.lab.limber.example.page;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.selection.IElementNodeSelection;
import no.kantega.lab.limber.page.WebPage;
import no.kantega.lab.limber.servlet.meta.RequestMapping;

@RequestMapping("/")
public class TestWebPage extends WebPage {

    public TestWebPage() {

        dom().setTile("Limber framework");

        dom().findByTag("h2").setContent("Hello limber framework");

        IElementNodeSelection<?> selection = dom().findByTag("ul").clear();
        for (String s : new String[]{"It is easy to use", "It is elegant to use", "Out-of-the-box jQuery"}) {
            selection.appendChild("li").setContent(s);
        }
        selection.setRandomIdIfNone();

        dom().findByTag("button").setContent("Ajax demo").addAjaxEvent(AjaxEventTrigger.CLICK, new IAjaxCallback<ElementNode<?>>() {
            @Override
            public void onEvent(AjaxEventTrigger ajaxEventTrigger, ElementNode<?> eventTarget) {
                System.out.println("Ajax!");
            }
        });

    }
}
