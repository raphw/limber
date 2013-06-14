package no.kantega.lab.limber.ajax;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

public class AjaxBoundEventTupel<N extends ElementNode<? extends N>> {

    private final N node;

    private final AjaxEventTrigger ajaxEventTrigger;

    private final IAjaxCallback<? super N> ajaxCallback;

    public AjaxBoundEventTupel(@Nonnull N node, @Nonnull AjaxEventTrigger ajaxEventTrigger, @Nonnull IAjaxCallback<? super N> ajaxCallback) {
        this.node = node;
        this.ajaxEventTrigger = ajaxEventTrigger;
        this.ajaxCallback = ajaxCallback;
    }
    @Nonnull
    public N getElementNode() {
        return node;
    }

    @Nonnull
    public AjaxEventTrigger getAjaxEventTrigger() {
        return ajaxEventTrigger;
    }

    @Nonnull
    public IAjaxCallback<? super N> getAjaxCallback() {
        return ajaxCallback;
    }

    public void triggerEvent() {
        ajaxCallback.onEvent(ajaxEventTrigger, node);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AjaxBoundEventTupel<?> that = (AjaxBoundEventTupel<?>) o;

        if (!ajaxCallback.equals(that.ajaxCallback)) return false;
        if (ajaxEventTrigger != that.ajaxEventTrigger) return false;
        if (!node.equals(that.node)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = node.hashCode();
        result = 31 * result + ajaxEventTrigger.hashCode();
        result = 31 * result + ajaxCallback.hashCode();
        return result;
    }
}
