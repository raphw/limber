package no.kantega.lab.limber.example.page;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.abstraction.selection.IDomSelection;
import no.kantega.lab.limber.dom.abstraction.selection.LimberElement;
import no.kantega.lab.limber.page.WebPage;
import no.kantega.lab.limber.page.annotations.RequestMapping;
import org.jsoup.nodes.Element;

@RequestMapping("/")
public class TestWebPage extends WebPage {

    public TestWebPage() {

        dom().setTitle("Limber framework");

        dom().tag("h2").setContent("Hello limber framework");

        IDomSelection list = dom().tag("ul").removeAllChildren();
        for (String s : new String[]{"It is easy to use", "It is elegant to use", "Supports jQuery"}) {
            list.appendChild(new LimberElement("li")).setContent(s);
        }

        dom().tag("button").setContent("Ajax demo").ajax(
                AjaxEventTrigger.CLICK, new IAjaxCallback() {
            @Override
            public void onEvent(AjaxEventTrigger ajaxEventTrigger, Element eventTarget) {
                System.out.println("Ajax Event");
            }
        });

    }
}
