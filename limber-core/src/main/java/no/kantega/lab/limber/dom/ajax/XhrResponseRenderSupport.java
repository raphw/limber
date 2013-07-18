package no.kantega.lab.limber.dom.ajax;

import no.kantega.lab.limber.dom.comparison.IReplacementStep;
import no.kantega.lab.limber.dom.page.context.IHtmlRenderContext;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class XhrResponseRenderSupport {

    private static final XhrResponseRenderSupport INSTANCE = new XhrResponseRenderSupport();

    public static XhrResponseRenderSupport getInstance() {
        return INSTANCE;
    }

    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    private static final char TAG_OPENING_SYMBOL = '<';
    private static final char TAG_CLOSING_SYMBOL = '>';
    private static final char CLOSING_TAG_SYMBOL = '/';

    public static final String UPDATES_TAG_NAME = "updates";
    public static final String UPDATE_TAG_NAME = "update";

    public static final String IDENTIFICATOR_TYPE_TAG_NAME = "locationType";
    public static final String LOCATION_TAG_NAME = "locationId";
    public static final String ACTION_TAG_NAME = "action";
    public static final String CONTENT_TAG_NAME = "content";

    private static final String CDATA_OPENING_TAG_NAME = "<![CDATA[";
    private static final String CDATA_CLOSING_TAG_NAME = "]]>";

    public void renderReplacementSteps(Writer writer, IHtmlRenderContext htmlRenderContext, List<IReplacementStep> replacementSteps) throws IOException {

        writer.append(XML_HEADER);
        renderOpeningTag(writer, UPDATES_TAG_NAME);
        for (IReplacementStep replacementStep : replacementSteps) {
            renderOpeningTag(writer, UPDATE_TAG_NAME);
            renderReplacementStep(writer, htmlRenderContext, replacementStep);
            renderClosingTag(writer, UPDATE_TAG_NAME);
        }
        renderClosingTag(writer, UPDATES_TAG_NAME);

    }

    private void renderReplacementStep(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext, @Nonnull IReplacementStep replacementStep) throws IOException {

        // Write type and location
        if (replacementStep.getFromNode().isIdSet()) {
            renderInsideTag(writer, IDENTIFICATOR_TYPE_TAG_NAME, AjaxElementIdentificator.SELECTOR.getIdentificatorName());
            renderInsideTag(writer, LOCATION_TAG_NAME, replacementStep.getFromNode().getId());
        } else {
            renderInsideTag(writer, IDENTIFICATOR_TYPE_TAG_NAME, AjaxElementIdentificator.XPATH.getIdentificatorName());
            renderInsideTag(writer, LOCATION_TAG_NAME, replacementStep.getFromNode().getXPath());
        }

        // Write required action
        renderInsideTag(writer, ACTION_TAG_NAME, replacementStep.getReplacementStrategy().getReplacementStrategyName());

        // Write content
        renderOpeningTag(writer, CONTENT_TAG_NAME);
        writer.write(CDATA_OPENING_TAG_NAME);
        replacementStep.getToNode().render(writer, htmlRenderContext);
        writer.write(CDATA_CLOSING_TAG_NAME);
        renderClosingTag(writer, CONTENT_TAG_NAME);

    }

    private void renderInsideTag(@Nonnull Writer writer, @Nonnull String tagName, @Nonnull String value) throws IOException {
        renderOpeningTag(writer, tagName);
        writer.append(value);
        renderClosingTag(writer, tagName);
    }

    private void renderOpeningTag(@Nonnull Writer writer, @Nonnull String tagName) throws IOException {
        writer.append(TAG_OPENING_SYMBOL).append(tagName).append(TAG_CLOSING_SYMBOL);
    }

    private void renderClosingTag(@Nonnull Writer writer, @Nonnull String tagName) throws IOException {
        writer.append(TAG_OPENING_SYMBOL).append(CLOSING_TAG_SYMBOL).append(tagName).append(TAG_CLOSING_SYMBOL);
    }
}
