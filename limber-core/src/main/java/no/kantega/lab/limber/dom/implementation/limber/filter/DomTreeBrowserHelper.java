package no.kantega.lab.limber.dom.implementation.limber.filter;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;
import no.kantega.lab.limber.dom.implementation.limber.element.ElementNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DomTreeBrowserHelper {

    private static final DomTreeBrowserHelper instance = new DomTreeBrowserHelper();

    public static DomTreeBrowserHelper getInstance() {
        return instance;
    }

    private DomTreeBrowserHelper() {
        /* empty */
    }

    public <T extends AbstractNode> List<T> filter(ElementNode parent,
                                                   AbstractNodeFilter<T> nodeFilter,
                                                   int maxDepth) {

        Class<T> filterArgumentClass = nodeFilter.getParameterClass();

        List<T> foundNodes = new ArrayList<T>();
        Queue<ElementNode> nodesToScan = new ArrayDeque<ElementNode>();
        nodesToScan.add(parent);

        int currentDepth = 0, timeToDepthIncrease = 1, nextTimeToDepthIncrease = 0;

        while (!nodesToScan.isEmpty()) {
            ElementNode current = nodesToScan.poll();
            nextTimeToDepthIncrease += current.size();
            if (--timeToDepthIncrease == 0) {
                if (++currentDepth > maxDepth) break;
                timeToDepthIncrease = nextTimeToDepthIncrease;
                nextTimeToDepthIncrease = 0;
            }
            for (AbstractNode<?> node : current.children()) {
                if (filterArgumentClass.isAssignableFrom(node.getClass())) {
                    T castNode = filterArgumentClass.cast(node);
                    if (nodeFilter.filter(castNode)) {
                        foundNodes.add(castNode);
                    }
                }
                if (!(node instanceof ElementNode)) {
                    continue;
                }
                nodesToScan.add((ElementNode) node);
            }
        }

        return foundNodes;

    }
}
