package no.kantega.lab.limber.page.util;

import no.kantega.lab.limber.dom.ajax.AjaxBoundEventTupel;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;

import java.awt.image.renderable.RenderContext;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class JQueryRenderSupport {

    private static final JQueryRenderSupport INSTANCE = new JQueryRenderSupport();

    public static JQueryRenderSupport getInstance() {
        return INSTANCE;
    }

    public void makeEventJavascript(Writer writer, IRenderContext renderContext,
                                    Map<UUID, AjaxBoundEventTupel<?>> ajaxEventRegister) throws IOException {
        if (ajaxEventRegister.size() == 0) {
            return;
        }

        // Start load wrapper
        writer.append("jQuery(document).ready(function(){");

        for (Map.Entry<UUID, AjaxBoundEventTupel<?>> entry : ajaxEventRegister.entrySet()) {

            AjaxBoundEventTupel<?> ajaxContainer = entry.getValue();

            // Write identifier
            writer.append("jQuery('#");
            writer.append(ajaxContainer.getElementNode().setRandomIdIfNone().getId());
            writer.append("')");

            // Start bind
            writer.append(".bind(");

            // Event
            writer.append("'click'"); // Prelim.

            //  Response wrapper
            writer.append(",");
            writer.append("function(){");

            // Ajax call
            writer.append("jQuery.ajax({");
            makeCallbackUrl(writer, renderContext, entry.getKey());
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
        writer.append("success:function(xml){");
        writer.append("var parsedXml = jQuery.parseXML(xml);var parsed = jQuery(parsedXml);parsed.children('updates').children('update').each(" +
                "function(i,e){var $update = jQuery(this);var id = $update.children('location').get(0).innerHTML;var content = " +
                "$update.children('content').get(0).innerHTML;jQuery(id).replaceWith(content);})");
        writer.append("}");
    }

    private void makeCallbackUrl(Writer writer, IRenderContext renderContext, UUID ajaxId) throws IOException {
        writer.append("url:");
        writer.append("'");
        writer.append(renderContext.getLimberPageRegister().decodeLink(
                renderContext.getRequestMapping().getRenderableClass(),
                renderContext.getRequestMapping().getVersionId(),
                ajaxId).toASCIIString());
        writer.append("'");
    }

    public void makeUpdateResponse(Writer writer, Collection<ElementNode> elementNodes, RenderContext renderContext) throws IOException {

        writer.append("<updates>");
        for (ElementNode elementNode : elementNodes) {
            makeElementTag(writer, elementNode, renderContext);
        }
        writer.append("</updates>");

    }

    private void makeElementTag(Writer writer, ElementNode elementNode, RenderContext renderContext) throws IOException {

        if (elementNode.getId() == null) {
            throw new IllegalStateException(); // prelim, later xpath fallback
        }

        writer.append("<update>");

        writer.append("<location>");
        writer.append("#").append(elementNode.getId()); //prelim
        writer.append("</location>");

        writer.append("<content>");
        elementNode.render(writer, null); // prelim
        writer.append("</content>");

        writer.append("</update>");

    }

}
