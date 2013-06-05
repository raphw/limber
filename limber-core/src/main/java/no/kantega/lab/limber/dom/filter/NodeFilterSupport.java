package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class NodeFilterSupport {

    private static final NodeFilterSupport instance = new NodeFilterSupport();

    public static NodeFilterSupport getInstance() {
        return instance;
    }

    private NodeFilterSupport() {
        /* empty */
    }

    public <N extends AbstractNode<N>> List<N> filter(@Nonnull ElementNode origin,
                                                      @Nonnull INodeFilter<N> nodeFilter,
                                                      int maxDepth) {

        Class<? extends N> filterParameterClass = findFilterParameterClass(nodeFilter);

        List<N> foundNodes = new ArrayList<N>();
        if (maxDepth < 1) return foundNodes;
        Queue<ElementNode> nodesToScan = new ArrayDeque<ElementNode>();
        nodesToScan.add(origin);

        int currentDepth = 0, timeToDepthIncrease = 1, nextTimeToDepthIncrease = 0;

        while (!nodesToScan.isEmpty()) {
            ElementNode current = nodesToScan.poll();
            List<ElementNode> children = findBrowsableChildren(current);
            nextTimeToDepthIncrease += children.size();
            nodesToScan.addAll(children);
            if (--timeToDepthIncrease == 0) {
                if (++currentDepth > maxDepth) break;
                timeToDepthIncrease = nextTimeToDepthIncrease;
                nextTimeToDepthIncrease = 0;
            }
            for (AbstractNode<?> node : current.children()) {
                if (filterParameterClass.isAssignableFrom(node.getClass())) {
                    N castNode = filterParameterClass.cast(node);
                    if (nodeFilter.filter(castNode)) {
                        foundNodes.add(castNode);
                    }
                }
            }
        }

        return foundNodes;

    }

    private List<ElementNode> findBrowsableChildren(@Nonnull ElementNode parent) {
        List<ElementNode> elementChildren = new LinkedList<ElementNode>();
        for (AbstractNode<?> child : parent.children()) {
            if (child instanceof ElementNode) {
                elementChildren.add((ElementNode) child);
            }
        }
        return elementChildren;
    }

    @SuppressWarnings("unchecked")
    public <N extends AbstractNode<N>> Class<? extends N> findFilterParameterClass(@Nonnull INodeFilter<N> filter) {
        Class<?> currentClass = filter.getClass();
        do {
            for (Type interfaceType : currentClass.getGenericInterfaces()) {
                ParameterizedType parameterizedInterfaceType = (ParameterizedType) interfaceType;
                Class<?> interfaceClass = (Class<?>) parameterizedInterfaceType.getRawType();
                if (interfaceClass == INodeFilter.class) {
                    return (Class<N>) parameterizedInterfaceType.getActualTypeArguments()[0];
                }
            }
        } while ((currentClass = currentClass.getSuperclass()) != null);
        throw new IllegalStateException();
    }
}
