package no.kantega.lab.limber.dom.page.util;

import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.kernel.request.IRenderContext;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.UUID;

public class JQueryRenderSupport {

    private static final JQueryRenderSupport INSTANCE = new JQueryRenderSupport();

    public static JQueryRenderSupport getInstance() {
        return INSTANCE;
    }

    public void makeEventJavascript(Writer writer, IHtmlRenderContext htmlRenderContext,
                                    Collection<IEventTriggerable> ajaxEventRegister) throws IOException {

        // Start load wrapper
        writer.append("jQuery(document).ready(function(){");

        for (IEventTriggerable eventTriggerable : ajaxEventRegister) {

            // Write identifier
            if (eventTriggerable.getEventTarget().isIdSet()) {
                writer.append("jQuery('#").append(eventTriggerable.getEventTarget().getId()).append("')");
            } else {
                writer.append("jQuery(document).xpath('").append(eventTriggerable.getEventTarget().getXPath()).append("')");
            }

            // Start bind
            writer.append(".bind(");

            // Event
            writer.append("'").append(eventTriggerable.getAjaxEventTrigger().getEventDescription()).append("'");

            //  Response wrapper
            writer.append(",");
            writer.append("function(){");

            // Ajax call
            writer.append("jQuery.ajax({");
            makeCallbackUrl(writer, htmlRenderContext, eventTriggerable.getUUID());
            writer.append(",");
            makeSuccessFunction(writer);
            writer.append("});");

            // Close response wrapper
            writer.append("}");

            // Finish bind
            writer.append(")");
        }

        // Close load wrapper
        writer.append("})");
    }

    private void makeSuccessFunction(Writer writer) throws IOException {
        writer.append("success:function(xml){limberAnswer(xml);}");
    }

    private void makeCallbackUrl(Writer writer, IRenderContext renderContext, UUID ajaxId) throws IOException {
        writer.append("url:");
        writer.append("'");
        writer.append(renderContext.getPageContext().decodeLink(
                renderContext.getRequestMapping().getRenderableClass(),
                renderContext.getRequestMapping().getVersionId(),
                ajaxId).toASCIIString());
        writer.append("'");
    }

}
