package no.kantega.lab.limber.dom.filter.util;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;

import javax.annotation.Nonnull;
import java.util.*;

public class NodeFilterSupport {

    private static final NodeFilterSupport INSTANCE = new NodeFilterSupport();

    @Nonnull
    public static NodeFilterSupport getInstance() {
        return INSTANCE;
    }

    private NodeFilterSupport() {
        /* empty */
    }

    @Nonnull
    public <N extends AbstractNode<? extends N>, N2 extends N> List<N> filterNodeTree(@Nonnull ElementNode origin,
                                                              @Nonnull INodeFilter<N> nodeFilter,
                                                              @Nonnull Class<? extends N2> filterBoundary,
                                                              int maxDepth) {
        if (maxDepth > 0) {
            return filterToBottomBreadthFirst(origin, nodeFilter, filterBoundary, maxDepth);
        } else if (maxDepth < 0) {
            return filterToRootNode(origin, nodeFilter, filterBoundary, maxDepth);
        } else {
            return Collections.emptyList();
        }
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private <N extends AbstractNode<? extends N>, N2 extends N> List<N> filterToBottomBreadthFirst(@Nonnull ElementNode origin,
                                                                           @Nonnull INodeFilter<N> nodeFilter,
                                                                           @Nonnull Class<? extends N2> filterBoundary,
                                                                           int maxDepth) {

        assert maxDepth > 0;

        List<N> foundNodes = new ArrayList<N>();
        if (maxDepth < 1) return foundNodes;
        Queue<ElementNode> nodesToScan = new ArrayDeque<ElementNode>();
        nodesToScan.add(origin);

        int currentDepth = 0, timeToDepthIncrease = 1, nextTimeToDepthIncrease = 0;

        while (!nodesToScan.isEmpty()) {
            ElementNode current = nodesToScan.poll();
            List<ElementNode> children = findElementNodeChildren(current);
            nextTimeToDepthIncrease += children.size();
            nodesToScan.addAll(children);
            if (--timeToDepthIncrease == 0) {
                if (++currentDepth > maxDepth) break;
                timeToDepthIncrease = nextTimeToDepthIncrease;
                nextTimeToDepthIncrease = 0;
            }
            for (AbstractNode<?> node : current.getChildren()) {
                if (filterBoundary.isAssignableFrom(node.getClass())) {
                    N castNode = (N) node;
                    if (nodeFilter.filter(castNode)) {
                        foundNodes.add(castNode);
                    }
                }
            }
        }

        return foundNodes;
    }

    @Nonnull
    private <N extends AbstractNode<? extends N>, N2 extends N> List<N> filterToRootNode(@Nonnull ElementNode origin,
                                                                 @Nonnull INodeFilter<N> nodeFilter,
                                                                 @Nonnull Class<? extends N2> filterBoundary,
                                                                 int maxDepth) {

        assert maxDepth < 0;

        List<N> foundNodes = new ArrayList<N>();

        for (int currentDepth = 0; currentDepth > maxDepth; currentDepth--) {
            origin = origin.getParent();
            if (origin == null) {
                break;
            }
            N filteredNode = filterNode(origin, nodeFilter, filterBoundary);
            if (filteredNode != null) {
                foundNodes.add(filteredNode);
            }
        }

        return foundNodes;
    }

    @Nonnull
    private List<ElementNode> findElementNodeChildren(@Nonnull ElementNode parent) {
        List<ElementNode> elementChildren = new LinkedList<ElementNode>();
        for (AbstractNode<?> child : parent.getChildren()) {
            if (child instanceof ElementNode) {
                elementChildren.add((ElementNode) child);
            }
        }
        return elementChildren;
    }

    @Nonnull
    public <N extends AbstractNode<? extends N>, N2 extends N> List<N> filterNodeList(@Nonnull List<? extends AbstractNode> nodeList,
                                                              @Nonnull INodeFilter<N> nodeFilter,
                                                              @Nonnull Class<? extends N2> filterBoundary) {
        List<N> resultNodeList = new ArrayList<N>(nodeList.size());
        for (AbstractNode<?> node : nodeList) {
            N castNode = filterNode(node, nodeFilter, filterBoundary);
            if (castNode != null) resultNodeList.add(castNode);
        }
        return resultNodeList;
    }

    @SuppressWarnings("unchecked")
    public <N extends AbstractNode<? extends N>, N2 extends N> N filterNode(@Nonnull AbstractNode<?> node,
                                                    @Nonnull INodeFilter<N> nodeFilter,
                                                    @Nonnull Class<? extends N2> filterBoundary) {
        if (filterBoundary.isAssignableFrom(node.getClass())) {
            N castNode = (N) node;
            return nodeFilter.filter(castNode) ? castNode : null;
        } else {
            return null;
        }
    }
}
