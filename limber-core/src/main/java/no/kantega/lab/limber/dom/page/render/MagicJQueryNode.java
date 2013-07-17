package no.kantega.lab.limber.dom.page.render;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.dom.page.util.JQueryRenderSupport;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;

public class MagicJQueryNode extends ElementNode<MagicJQueryNode> {

    private final Collection<IEventTriggerable> eventTriggerables;

    public MagicJQueryNode(@Nonnull Collection<IEventTriggerable> eventTriggerables) {
        super("script");
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

        writer.append("<script type=\"text/javascript\">\n");
        JQueryRenderSupport.getInstance().makeEventJavascript(writer, htmlRenderContext, ajaxEventTriggerables);
        writer.append("</script>\n");

        return true;
    }
}
