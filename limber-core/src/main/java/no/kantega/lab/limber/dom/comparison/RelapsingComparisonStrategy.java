package no.kantega.lab.limber.dom.comparison;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RelapsingComparisonStrategy implements IComparisonStrategy {

    @Nonnull
    @Override
    public List<IReplacementStep> compareElementNodes(@Nonnull ElementNode<?> fromRoot, @Nonnull ElementNode<?> toRoot) {

        Queue<ElementNode<?>[]> pairsToCheck = new LinkedList<ElementNode<?>[]>();
        pairsToCheck.add(new ElementNode<?>[]{fromRoot, toRoot});

        List<IReplacementStep> replacementSteps = new LinkedList<IReplacementStep>();

        while (!pairsToCheck.isEmpty()) {
            ElementNode<?>[] nextPair = pairsToCheck.poll();
            compareElementNodes(replacementSteps, pairsToCheck, nextPair[0], nextPair[1]);
        }

        return replacementSteps;
    }

    @SuppressWarnings("unchecked")
    private void compareElementNodes(@Nonnull List<IReplacementStep> replacementSteps,
                                     @Nonnull Queue<ElementNode<?>[]> pairsToCheck,
                                     @Nonnull ElementNode<?> from, @Nonnull ElementNode<?> to) {

        if (from.size() != to.size()) {
            replacementSteps.add(new DefaultReplacementStep(ReplacementStrategy.RERENDER, from, to));
        }

        Iterator<? extends AbstractNode<?>>
                fromIterator = from.getChildren().nodeList().iterator(),
                toIterator = to.getChildren().nodeList().iterator();

        List<ElementNode<?>[]> localPairsToCheck = new LinkedList<ElementNode<?>[]>();

        while (fromIterator.hasNext() && toIterator.hasNext()) {
            AbstractNode<?> fromChild = fromIterator.next(), toChild = toIterator.next();
            if (fromChild.localHashCode() != toChild.localHashCode()) {
                replacementSteps.add(new DefaultReplacementStep(ReplacementStrategy.RERENDER, from, to));
                return;
            } else if (fromChild instanceof ElementNode && toChild instanceof ElementNode) {
                localPairsToCheck.add(new ElementNode<?>[]{(ElementNode<?>) fromChild, (ElementNode<?>) toChild});
            } else if (fromChild instanceof ElementNode ^ toChild instanceof ElementNode) {
                throw new IllegalArgumentException();
            }
        }

        pairsToCheck.addAll(localPairsToCheck);
    }
}
