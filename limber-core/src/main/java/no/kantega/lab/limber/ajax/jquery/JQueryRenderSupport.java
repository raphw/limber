package no.kantega.lab.limber.ajax.jquery;

import no.kantega.lab.limber.ajax.container.AjaxCallbackEventTriggerElementNodeTupel;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.servlet.IResponseContainer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
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
        IOUtils.write("jQuery(document).ready(function(){", outputStream);
        for (Map.Entry<UUID, AjaxCallbackEventTriggerElementNodeTupel> entry : ajaxEventRegister.entrySet()) {

            AjaxCallbackEventTriggerElementNodeTupel ajaxContainer = entry.getValue();
            UUID ajaxId = entry.getKey();

            IOUtils.write("jQuery('#", outputStream);
            IOUtils.write(ajaxContainer.getElement().setRandomIdIfNone().getId(), outputStream);
            IOUtils.write("').bind('", outputStream);
            IOUtils.write("click", outputStream); // Prelim.
            IOUtils.write("',function(){", outputStream);
            IOUtils.write("jQuery.ajax(", outputStream);
            IOUtils.write("'", outputStream);
            IOUtils.write(response.decodeLink(
                    response.getRequest().getRenderableClass(),
                    response.getRequest().getVersionId(),
                    ajaxId).toASCIIString(), outputStream);
            IOUtils.write("'", outputStream);

            IOUtils.write(");", outputStream);
            IOUtils.write("})", outputStream);
        }
        IOUtils.write("})", outputStream);
    }

    public void makeJsonResponeJavascript(OutputStream outputStream, ElementNode elementNode, IResponseContainer response) throws IOException {

        if(elementNode.getId() == null) {
            throw new IllegalStateException(); // prelim, later xpath fallback
        }

        IOUtils.write("{", outputStream);

        IOUtils.write("\"location\":\"", outputStream);
        IOUtils.write("#", outputStream); // prelim
        IOUtils.write(elementNode.getId(), outputStream);
        IOUtils.write("\",", outputStream);

        IOUtils.write("\"content\":\"", outputStream);
        elementNode.render(outputStream, response);
        IOUtils.write("\"", outputStream);

        IOUtils.write("}", outputStream);
    }

}
