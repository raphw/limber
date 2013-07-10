package no.kantega.lab.limber.dom.comparison;

import no.kantega.lab.limber.dom.element.ElementNode;

import java.util.List;

public class TreeComparisonSupport {

    private static final TreeComparisonSupport INSTANCE = new TreeComparisonSupport();

    public static TreeComparisonSupport getInstance() {
        return INSTANCE;
    }

    private TreeComparisonSupport() {
        /* empty */
    }

    public List<IReplacementStep> compare(Class<? extends IDomComparisonStrategy> comparisonStrategyClass, ElementNode<?> leftRoot, ElementNode<?> rightRoot) {
        IDomComparisonStrategy comparisonStrategy = makeInstance(comparisonStrategyClass);
        return comparisonStrategy.compareElementNodes(leftRoot, rightRoot);
    }

    IDomComparisonStrategy makeInstance(Class<? extends IDomComparisonStrategy> comparisonStrategyClass) {
        try {
            return comparisonStrategyClass.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
