package no.kantega.lab.limber.example.page;

import no.kantega.lab.limber.dom.ajax.AjaxEventTrigger;
import no.kantega.lab.limber.dom.ajax.IAjaxCallback;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.IDomNodeVisitor;
import no.kantega.lab.limber.dom.page.WebPage;
import no.kantega.lab.limber.kernel.meta.RequestMapping;

import java.io.Serializable;
import java.util.Random;

@RequestMapping("/")
public class TestWebPage extends WebPage implements Serializable {

    public TestWebPage() {

//        dom().setTile("Limber framework");
//
//        dom().findByTag("h2").setContent("Hello limber framework");
//
//        dom().findByTag("ul").clear().visit(new IDomNodeVisitor<ElementNode<?>>() {
//            @Override
//            public void visit(ElementNode<?> node) {
//                for (String s : new String[]{"It is easy to use", "It is elegant to use", "Out-of-the-box jQuery"}) {
//                    node.appendChild("li").setContent(s);
//                }
//            }
//        });
//
//
//        dom().findByTag("button").setContent("Ajax demo").addAjaxEvent(AjaxEventTrigger.CLICK, new IAjaxCallback<ElementNode<?>>() {
//            @Override
//            public void onEvent(AjaxEventTrigger ajaxEventTrigger, ElementNode<?> eventTarget) {
//                System.out.println("Ajax!");
//                dom().findByTag("h2").setContent("Ajax action! (" + new Date().getTime() + ")");
//            }
//        });
//
//        LinkNode<?> link = new PlainLinkNode("a").setContent("Dynamic link").setTarget("/js/jquery-1.10.1.min.js");
//        dom().getBodyNode().appendChild(link);
//
//        dom().findByTag("ol").remove();
//
        dom().getBodyNode().clear();

        final int fieldSize = 30;
        final Random random = new Random();

        final IAjaxCallback<ElementNode<?>> ajax = new IAjaxCallback<ElementNode<?>>() {
            @Override
            public void onEvent(AjaxEventTrigger ajaxEventTrigger, ElementNode<?> eventTarget) {
                update(random, eventTarget);
            }
        };

        dom().getBodyNode().appendChild("table").appendChild("tbody").visit(new IDomNodeVisitor<ElementNode<?>>() {
            @Override
            public void visit(ElementNode<?> node) {
                for (int i = 0; i < fieldSize; i++) {
                    node.appendChild("tr").visit(new IDomNodeVisitor<ElementNode<?>>() {
                        @Override
                        public void visit(ElementNode<?> node) {
                            for (int i = 0; i < fieldSize; i++) {
                                update(random, node.appendChild("td").addAjaxEvent(AjaxEventTrigger.MOUSE_MOVE, ajax));
                            }
                        }
                    });
                }
            }
        });
    }

    private void update(Random random, ElementNode<?> elementNode) {
        int number = random.nextInt(1000);
        elementNode.setContent("" + number).addCssStyle("color", number < 500 ? "red" : "blue");
    }
}