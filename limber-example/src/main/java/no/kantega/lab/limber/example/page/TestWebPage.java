package no.kantega.lab.limber.example.page;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.abstraction.element.DefaultImmutableElement;
import no.kantega.lab.limber.dom.abstraction.element.IDomElement;
import no.kantega.lab.limber.dom.abstraction.selection.IDomSelection;
import no.kantega.lab.limber.page.WebPage;
import no.kantega.lab.limber.page.annotations.RequestMapping;

@RequestMapping("/")
public class TestWebPage extends WebPage {

    public TestWebPage() {

        dom().setTitle("Limber framework");

        dom().tag("h2").setContent("Hello limber framework");

        IDomSelection list = dom().tag("ul").removeAllChildren();
        for (String s : new String[]{"It is easy to use", "It is elegant to use", "Supports jQuery"}) {
            list.appendChild(new DefaultImmutableElement("li")).setContent(s);
        }

        dom().tag("button").setContent("Ajax demo").ajax(
                AjaxEventTrigger.CLICK, new IAjaxCallback() {
            @Override
            public void onEvent(AjaxEventTrigger ajaxEventTrigger, IDomElement target) {
                System.out.println("This is an Ajax event!");
            }
        });

    }
}
