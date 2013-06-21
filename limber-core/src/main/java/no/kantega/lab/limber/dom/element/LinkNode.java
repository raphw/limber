package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomLinkNodeQueryable;
import no.kantega.lab.limber.dom.abstraction.IDomLinkNodeRepresentable;
import no.kantega.lab.limber.dom.target.ITargetable;
import no.kantega.lab.limber.dom.target.RenderableTarget;
import no.kantega.lab.limber.dom.target.URITarget;
import no.kantega.lab.limber.page.context.IHtmlRenderContext;
import no.kantega.lab.limber.servlet.IRenderable;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.Map;

public class LinkNode<N extends LinkNode<N>> extends ElementNode<N>
        implements IDomLinkNodeRepresentable<N>, IDomLinkNodeQueryable<N> {

    private ITargetable<?> targetable;

    public LinkNode(@Nonnull CharSequence tag) {
        super(tag);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N setTarget(@Nonnull URI uri) {
        return setTarget(new URITarget(uri));
    }

    @Nonnull
    @Override
    public N setTarget(@Nonnull Class<? extends IRenderable> clazz) {
        return setTarget(new RenderableTarget(clazz));
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N setTarget(@Nonnull ITargetable<?> targetable) {
        this.targetable = targetable;
        return (N) this;
    }

    @Nonnull
    @Override
    public ITargetable<?> getTarget() {
        return targetable;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N clearTarget() {
        targetable = null;
        return (N) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public N setTarget(@Nonnull CharSequence uri) {
        this.targetable = new URITarget(uri);
        return (N) this;
    }

    @Nonnull
    @Override
    public N clear() {
        targetable = null;
        return super.clear();
    }

    @Override
    protected void onRenderAttrs(@Nonnull Writer writer, @Nonnull IHtmlRenderContext htmlRenderContext) throws IOException {
        String overridenAttribute = ElementNodeFactory.getInstance().getTargetAttr(getTagName());
        for (Map.Entry<String, String> attr : getAttrs().entrySet()) {
            if (targetable != null && StringUtils.equals(attr.getKey(), overridenAttribute)) continue;
            writer.append(" ");
            onRenderAttr(attr.getKey(), attr.getValue(), writer, htmlRenderContext);
        }
        if (targetable != null) {
            writer.append(" ");
            onRenderAttr(overridenAttribute, targetable.renderTarget(htmlRenderContext), writer, htmlRenderContext);
        }
    }
}
