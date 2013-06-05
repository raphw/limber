package no.kantega.lab.limber.example.page;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.TextNode;
import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.page.WebPage;
import no.kantega.lab.limber.servlet.meta.RequestMapping;

@RequestMapping("/")
public class TestWebPage extends WebPage {

    public TestWebPage() {

        dom().setTile("Limber framework");

        dom().findByTag("h2").clear().appendText("Hello limber framework");

        ElementNodeSelection list = dom().findByTag("ul").clear();
        for (String s : new String[]{"It is easy to use", "It is elegant to use", "Supports jQuery"}) {
            list.appendChild(new ElementNode("li").appendChild(new TextNode(s)));
        }

        dom().findByTag("button").clear().appendText("Ajax demo");
//        .ajax(
//                AjaxEventTrigger.CLICK, new IAjaxCallback() {
//            @Override
//            public void onEvent(AjaxEventTrigger ajaxEventTrigger, Element eventTarget) {
//                System.out.println("Ajax Event");
//            }
//        });

    }
}
