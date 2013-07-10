package no.kantega.lab.limber.dom.comparison;

import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;
import java.util.List;

public interface IDomComparisonStrategy {

    @Nonnull
    List<IReplacementStep> compareElementNodes(@Nonnull ElementNode<?> fromRoot, @Nonnull ElementNode<?> toRoot);
}
