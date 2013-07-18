package no.kantega.lab.limber.dom.page.render;

import no.kantega.lab.limber.dom.ajax.XhrBindingRenderSupport;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;

public class MagicJQueryNode extends ElementNode<MagicJQueryNode> {

    private static final String SCRIPT_TAG_NAME = "script";
    private static final String JAVASCRIPT_TAG_OPENING = "<script type=\"text/javascript\">";
    private static final String JAVASCRIPT_TAG_CLOSING = "</script>\n";

    private final Collection<IEventTriggerable> eventTriggerables;


    public MagicJQueryNode(@Nonnull Collection<IEventTriggerable> eventTriggerables) {
        super(SCRIPT_TAG_NAME);
        this.eventTriggerables = eventTriggerables;
    }

    @Override
    protected boolean onRender(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext) throws IOException {

        Collection<IEventTriggerable> ajaxEventTriggerables = new HashSet<IEventTriggerable>();

        for (IEventTriggerable eventTriggerable : eventTriggerables) {
            if (eventTriggerable.isAjaxEventTrigger()) ajaxEventTriggerables.add(eventTriggerable);
        }

        if (ajaxEventTriggerables.isEmpty()) {
            return false;
        }

        writer.append(JAVASCRIPT_TAG_OPENING);
        XhrBindingRenderSupport.getInstance().renderAjaxBindings(writer, htmlRenderContext, ajaxEventTriggerables);
        writer.append(JAVASCRIPT_TAG_CLOSING);

        return true;
    }
}
