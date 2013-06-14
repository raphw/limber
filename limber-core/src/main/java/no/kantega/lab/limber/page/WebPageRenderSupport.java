package no.kantega.lab.limber.page;

import no.kantega.lab.limber.ajax.container.AjaxCallbackEventTriggerElementNodeTupel;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.element.IDomNodeVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebPageRenderSupport {

    private static final WebPageRenderSupport INSTANCE = new WebPageRenderSupport();

    public static WebPageRenderSupport getInstance() {
        return INSTANCE;
    }

    private WebPageRenderSupport() {
        /* empty */
    }

    public Map<UUID, AjaxCallbackEventTriggerElementNodeTupel> makeAjaxEventMap(ElementNode<?> root) {
        final Map<UUID, AjaxCallbackEventTriggerElementNodeTupel> ajaxEventMap = new HashMap<UUID, AjaxCallbackEventTriggerElementNodeTupel>();
        root.findElements().visit(new IDomNodeVisitor<ElementNode<?>>() {
            @Override
            public void visit(ElementNode node) {
//                for (AjaxCallbackEventTriggerTupel<? super ElementNode<?>> tupel : node.getAjaxEvents()) {
//                    ajaxEventMap.put(UUID.randomUUID(), new AjaxCallbackEventTriggerElementNodeTupel(tupel, node));
//                }
                // TODO: Revert!
            }
        });
        return ajaxEventMap;
    }

}
