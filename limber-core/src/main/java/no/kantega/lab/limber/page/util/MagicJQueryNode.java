package no.kantega.lab.limber.page.util;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.page.IEventTriggerable;
import no.kantega.lab.limber.page.context.IHtmlRenderContext;

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
            if (eventTriggerable.isAjaxResponse()) ajaxEventTriggerables.add(eventTriggerable);
        }

        if (ajaxEventTriggerables.isEmpty()) {
            return false;
        }

        writer.append("<script type=\"text/javascript\">\n");
        //TODO: render Ajax registers
        writer.append("</script>\n");

        return true;
    }
}
