package no.kantega.lab.limber.dom.filter;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class NodeFilterSupport {

    private static final NodeFilterSupport instance = new NodeFilterSupport();

    public static NodeFilterSupport getInstance() {
        return instance;
    }

    private NodeFilterSupport() {
        /* empty */
    }

    public <T extends AbstractNode<T>> List<T> filter(ElementNode parent,
                                                      INodeFilter<T> nodeFilter,
                                                      int maxDepth) {

        Class<T> filterParameterClass = findFilterParameterClass(nodeFilter);

        List<T> foundNodes = new ArrayList<T>();
        if (maxDepth < 1) return foundNodes;
        Queue<ElementNode> nodesToScan = new ArrayDeque<ElementNode>();
        nodesToScan.add(parent);

        int currentDepth = 0, timeToDepthIncrease = 1, nextTimeToDepthIncrease = 0;

        while (!nodesToScan.isEmpty()) {
            ElementNode current = nodesToScan.poll();
            if (currentDepth > 0) {
                nodesToScan.add(current);
            }
            nextTimeToDepthIncrease += current.size();
            if (--timeToDepthIncrease == 0) {
                if (++currentDepth > maxDepth) break;
                timeToDepthIncrease = nextTimeToDepthIncrease;
                nextTimeToDepthIncrease = 0;
            }
            for (AbstractNode<?> node : current.children()) {
                if (filterParameterClass.isAssignableFrom(node.getClass())) {
                    T castNode = filterParameterClass.cast(node);
                    if (nodeFilter.filter(castNode)) {
                        foundNodes.add(castNode);
                    }
                }
            }
        }

        return foundNodes;

    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractNode<T>> Class<T> findFilterParameterClass(INodeFilter<T> filter) {
        Class<?> currentClass = filter.getClass();
        do {
            for (Type interfaceType : currentClass.getGenericInterfaces()) {
                ParameterizedType parameterizedInterfaceType = (ParameterizedType) interfaceType;
                Class<?> interfaceClass = (Class<?>) parameterizedInterfaceType.getRawType();
                if (interfaceClass == INodeFilter.class) {
                    return (Class<T>) parameterizedInterfaceType.getActualTypeArguments()[0];
                }
            }
        } while ((currentClass = currentClass.getSuperclass()) != null);
        throw new IllegalStateException();
    }
}
