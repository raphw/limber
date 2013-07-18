package no.kantega.lab.limber.dom.ajax;

import no.kantega.lab.limber.dom.page.IEventTriggerable;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.kernel.request.IRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.UUID;

public class XhrBindingRenderSupport {

    private static final XhrBindingRenderSupport INSTANCE = new XhrBindingRenderSupport();

    public static XhrBindingRenderSupport getInstance() {
        return INSTANCE;
    }

    private static final String JQUERY_LOADER_OPENING = "jQuery(document).ready(function(){";
    private static final String JQUERY_LOADER_CLOSING = "});";

    private static final String JQUERY_LIMBER_BINDER_OPENING = "Limber.getXhrBinder().bind(";
    private static final String JQUERY_LIMBER_BINDER_CLOSING = ");";

    private static final char SEPARATOR_SYMBOL = ',';
    private static final char QUOTE_SYMBOL = '\'';

    public void renderAjaxBindings(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext,
                                   @Nonnull Collection<IEventTriggerable> ajaxEventRegister) throws IOException {
        writer.write(JQUERY_LOADER_OPENING);
        for (IEventTriggerable eventTriggerable : ajaxEventRegister) {
            renderElementBinding(writer, htmlRenderContext, eventTriggerable);
        }
        writer.write(JQUERY_LOADER_CLOSING);
    }

    private void renderElementBinding(@Nonnull Writer writer, @Nonnull IRenderContext renderContext,
                                      @Nonnull IEventTriggerable eventTriggerable) throws IOException {

        writer.write(JQUERY_LIMBER_BINDER_OPENING);

        // Write type and location
        if (eventTriggerable.getEventTarget().isIdSet()) {
            renderInsideQuotes(writer, AjaxElementIdentificator.SELECTOR.getIdentificatorName());
            renderSeparator(writer);
            renderInsideQuotes(writer, eventTriggerable.getEventTarget().getId());
        } else {
            renderInsideQuotes(writer, AjaxElementIdentificator.XPATH.getIdentificatorName());
            renderSeparator(writer);
            renderInsideQuotes(writer, eventTriggerable.getEventTarget().getXPath());
        }

        // Write event description
        renderSeparator(writer);
        renderInsideQuotes(writer, eventTriggerable.getAjaxEventTrigger().getEventDescription());

        // Write event callback URL
        renderSeparator(writer);
        renderInsideQuotes(writer, makeCallbackUrl(renderContext, eventTriggerable.getUUID()));

        writer.write(JQUERY_LIMBER_BINDER_CLOSING);
    }

    private String makeCallbackUrl(@Nonnull IRenderContext renderContext, @Nonnull UUID ajaxId) throws IOException {
        return renderContext.getPageContext().decodeLink(
                renderContext.getRequestMapping().getRenderableClass(),
                renderContext.getRequestMapping().getVersionId(),
                ajaxId).toASCIIString();
    }

    private void renderInsideQuotes(@Nonnull Writer writer, @Nonnull String value) throws IOException {
        writer.append(QUOTE_SYMBOL);
        writer.append(value);
        writer.append(QUOTE_SYMBOL);
    }

    private void renderSeparator(@Nonnull Writer writer) throws IOException {
        writer.append(SEPARATOR_SYMBOL);
    }
}
