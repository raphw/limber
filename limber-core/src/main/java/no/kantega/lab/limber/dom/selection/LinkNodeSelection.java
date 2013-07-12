package no.kantega.lab.limber.dom.selection;

import no.kantega.lab.limber.dom.element.LinkNode;
import no.kantega.lab.limber.dom.target.ITargetable;
import no.kantega.lab.limber.kernel.AbstractRenderable;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;

public class LinkNodeSelection<N extends LinkNode<? extends N>, S extends LinkNodeSelection<N, ?>>
        extends ElementNodeSelection<N, S>
        implements ILinkNodeSelection<N> {

    public LinkNodeSelection(@Nonnull List<? extends N> selected) {
        super(selected);
    }

    public LinkNodeSelection(@Nonnull INodeSelection<? extends N> that) {
        super(that);
    }

    public LinkNodeSelection(@Nonnull LinkedHashSet<? extends N> selected) {
        super(selected);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setTarget(@Nonnull CharSequence uri) {
        for (N linkNode : getSelected()) {
            linkNode.setTarget(uri);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setTarget(@Nonnull URI uri) {
        for (N linkNode : getSelected()) {
            linkNode.setTarget(uri);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setTarget(@Nonnull Class<? extends AbstractRenderable> clazz) {
        for (N linkNode : getSelected()) {
            linkNode.setTarget(clazz);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S setTarget(@Nonnull ITargetable<?> targetable) {
        for (N linkNode : getSelected()) {
            linkNode.setTarget(targetable);
        }
        return (S) this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public S clearTarget() {
        for (N linkNode : getSelected()) {
            linkNode.clearTarget();
        }
        return (S) this;
    }
}
