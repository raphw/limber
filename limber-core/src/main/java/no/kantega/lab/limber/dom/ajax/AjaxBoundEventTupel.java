package no.kantega.lab.limber.dom.ajax;

import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.target.EventTrigger;
import no.kantega.lab.limber.page.IEventTriggerable;

import javax.annotation.Nonnull;
import java.util.UUID;

public class AjaxBoundEventTupel<N extends ElementNode<? extends N>> implements IEventTriggerable {

    private final N node;

    private final EventTrigger ajaxEventTrigger;

    private final IAjaxCallback<? super N> ajaxCallback;

    private final UUID uuid;

    public AjaxBoundEventTupel(@Nonnull N node, @Nonnull EventTrigger ajaxEventTrigger, @Nonnull IAjaxCallback<? super N> ajaxCallback) {
        this.node = node;
        this.ajaxEventTrigger = ajaxEventTrigger;
        this.ajaxCallback = ajaxCallback;
        this.uuid = UUID.randomUUID();
    }

    @Nonnull
    public N getElementNode() {
        return node;
    }

    @Nonnull
    public EventTrigger getEventTrigger() {
        return ajaxEventTrigger;
    }

    @Nonnull
    public IAjaxCallback<? super N> getAjaxCallback() {
        return ajaxCallback;
    }

    @Override
    public void trigger() {
        ajaxCallback.onEvent(ajaxEventTrigger, node);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean isAjaxResponse() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AjaxBoundEventTupel<?> that = (AjaxBoundEventTupel<?>) o;

        return ajaxCallback.equals(that.ajaxCallback)
                && ajaxEventTrigger == that.ajaxEventTrigger
                && node.equals(that.node);

    }

    @Override
    public int hashCode() {
        int result = node.hashCode();
        result = 31 * result + ajaxEventTrigger.hashCode();
        result = 31 * result + ajaxCallback.hashCode();
        return result;
    }
}
