package no.kantega.lab.limber.ajax.jquery;

import no.kantega.lab.limber.ajax.container.AjaxCallbackEventTriggerElementNodeTupel;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.servlet.IResponseContainer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class JQueryRenderSupport {

    private static final JQueryRenderSupport INSTANCE = new JQueryRenderSupport();

    public static JQueryRenderSupport getInstance() {
        return INSTANCE;
    }

    public void makeEventJavascript(OutputStream outputStream, IResponseContainer response, Map<UUID, AjaxCallbackEventTriggerElementNodeTupel> ajaxEventRegister) throws IOException {
        if (ajaxEventRegister.size() == 0) {
            return;
        }

        // Start load wrapper
        IOUtils.write("jQuery(document).ready(function(){", outputStream);

        for (Map.Entry<UUID, AjaxCallbackEventTriggerElementNodeTupel> entry : ajaxEventRegister.entrySet()) {

            AjaxCallbackEventTriggerElementNodeTupel ajaxContainer = entry.getValue();

            // Write identifier
            IOUtils.write("jQuery('#", outputStream);
            IOUtils.write(ajaxContainer.getElement().setRandomIdIfNone().getId(), outputStream);
            IOUtils.write("')", outputStream);

            // Start bind
            IOUtils.write(".bind(", outputStream);

            // Event
            IOUtils.write("'click'", outputStream); // Prelim.

            //  Response wrapper
            IOUtils.write(",", outputStream);
            IOUtils.write("function(){", outputStream);

            // Ajax call
            IOUtils.write("jQuery.ajax({", outputStream);
            makeCallbackUrl(outputStream, response, entry.getKey());
            IOUtils.write(",", outputStream);
            makeSuccessFunction(outputStream);
            IOUtils.write("});", outputStream);

            // Close response wrapper
            IOUtils.write("}", outputStream);

            // Finish bind
            IOUtils.write(")", outputStream);
        }

        // Close load wrapper
        IOUtils.write("})", outputStream);
    }

    private void makeSuccessFunction(OutputStream outputStream) throws IOException {
        IOUtils.write("success:function(xml){", outputStream);
        IOUtils.write("var parsedXml = jQuery.parseXML(xml);var parsed = jQuery(parsedXml);parsed.children('updates').children('update').each(" +
                "function(i,e){var $update = jQuery(this);var id = $update.children('location').get(0).innerHTML;var content = " +
                "$update.children('content').get(0).innerHTML;jQuery(id).replaceWith(content);})", outputStream);
        IOUtils.write("}", outputStream);
    }

    private void makeCallbackUrl(OutputStream outputStream, IResponseContainer response, UUID ajaxId) throws IOException {
        IOUtils.write("url:", outputStream);
        IOUtils.write("'", outputStream);
        IOUtils.write(response.decodeLink(
                response.getRequest().getRenderableClass(),
                response.getRequest().getVersionId(),
                ajaxId).toASCIIString(), outputStream);
        IOUtils.write("'", outputStream);
    }

    public void makeUpdateResponse(OutputStream outputStream, Collection<ElementNode> elementNodes, IResponseContainer response) throws IOException {

        IOUtils.write("<updates>", outputStream);
        for (ElementNode elementNode : elementNodes) {
            makeElementTag(outputStream, elementNode, response);
        }
        IOUtils.write("</updates>", outputStream);

    }

    private void makeElementTag(OutputStream outputStream, ElementNode elementNode, IResponseContainer response) throws IOException {

        if (elementNode.getId() == null) {
            throw new IllegalStateException(); // prelim, later xpath fallback
        }

        IOUtils.write("<update>", outputStream);

        IOUtils.write("<location>", outputStream);
        IOUtils.write("#" + elementNode.getId(), outputStream); //prelim
        IOUtils.write("</location>", outputStream);

        IOUtils.write("<content>", outputStream);
        elementNode.render(outputStream, response);
        IOUtils.write("</content>", outputStream);

        IOUtils.write("</update>", outputStream);

    }

}
