package no.kantega.lab.limber.ajax;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

public class AjaxEventTupel<N extends ElementNode<? extends N>> {

    private final AjaxEventTrigger ajaxEventTrigger;

    private final IAjaxCallback<? super N> ajaxCallback;

    public AjaxEventTupel(@Nonnull AjaxEventTrigger ajaxEventTrigger, @Nonnull IAjaxCallback<? super N> ajaxCallback) {
        this.ajaxEventTrigger = ajaxEventTrigger;
        this.ajaxCallback = ajaxCallback;
    }

    @Nonnull
    public AjaxEventTrigger getAjaxEventTrigger() {
        return ajaxEventTrigger;
    }

    @Nonnull
    public IAjaxCallback<? super N> getAjaxCallback() {
        return ajaxCallback;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AjaxEventTupel<?> that = (AjaxEventTupel<?>) o;

        return ajaxCallback.equals(that.ajaxCallback)
                && ajaxEventTrigger == that.ajaxEventTrigger;

    }

    @Override
    public int hashCode() {
        int result = ajaxEventTrigger.hashCode();
        result = 31 * result + ajaxCallback.hashCode();
        return result;
    }
}
