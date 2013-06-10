package no.kantega.lab.limber.example.page;

import no.kantega.lab.limber.dom.selection.ElementNodeSelection;
import no.kantega.lab.limber.page.WebPage;
import no.kantega.lab.limber.servlet.meta.RequestMapping;

@RequestMapping("/")
public class TestWebPage extends WebPage {

    public TestWebPage() {

        dom().setTile("Limber framework");

        dom().findByTag("h2").setContent("Hello limber framework");
        dom().findByTag("h2").setContent(String.valueOf(dom().getBodyNode().getChildren().reduceToElement().size()));

        ElementNodeSelection list = dom().findByTag("ul").clear();
        for (String s : new String[]{"It is easy to use", "It is elegant to use", "Supports jQuery"}) {
            list.appendChild("li").setContent(s);
        }
//
//        dom().findByTag("button").setContent("Ajax demo");

    }
}
