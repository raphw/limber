package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

public class TagNameVisibilityFilter<N extends ElementNode<? extends N>> extends TagNameFilter<N> {

    public TagNameVisibilityFilter(CharSequence tagName) {
        super(tagName);
    }

    @Override
    public boolean filter(@Nonnull N element) {
        return super.filter(element) && element.isRendered();
    }
}
