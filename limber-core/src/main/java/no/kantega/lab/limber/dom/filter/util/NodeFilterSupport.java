package no.kantega.lab.limber.dom.filter.util;

import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ElementNode;
import no.kantega.lab.limber.dom.filter.INodeFilter;

import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class NodeFilterSupport {

    private static final NodeFilterSupport instance = new NodeFilterSupport();

    @Nonnull
    public static NodeFilterSupport getInstance() {
        return instance;
    }

    private NodeFilterSupport() {
        /* empty */
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public <N extends AbstractNode<?>> List<N> filterBreadthFirst(@Nonnull ElementNode origin,
                                                                  @Nonnull INodeFilter<N> nodeFilter,
                                                                  int maxDepth) {

        Class<?> filterParameterClass = evaluateFilterParameterType(nodeFilter);

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
                if (filterParameterClass.isAssignableFrom(node.getClass())) {
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
    private List<ElementNode> findElementNodeChildren(@Nonnull ElementNode parent) {
        List<ElementNode> elementChildren = new LinkedList<ElementNode>();
        for (AbstractNode<?> child : parent.getChildren()) {
            if (child instanceof ElementNode) {
                elementChildren.add((ElementNode) child);
            }
        }
        return elementChildren;
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public <N extends AbstractNode<?>> Class<?> evaluateFilterParameterType(@Nonnull INodeFilter<N> filter) {
        Class<?> currentClass = filter.getClass();
        do {
            for (Type interfaceType : currentClass.getGenericInterfaces()) {
                ParameterizedType parameterizedInterfaceType = (ParameterizedType) interfaceType;
                Class<?> interfaceClass = (Class<?>) parameterizedInterfaceType.getRawType();
                if (interfaceClass == INodeFilter.class) {
                    Type foundType = parameterizedInterfaceType.getActualTypeArguments()[0];
                    if (foundType instanceof Class<?>)
                        return (Class<?>) foundType;
                    else {
                        return AbstractNode.class;
                    }
                }
            }
        } while ((currentClass = currentClass.getSuperclass()) != null);
        throw new IllegalStateException();
    }

    public <N extends AbstractNode<?>> N filterNode(@Nonnull AbstractNode<?> node, @Nonnull INodeFilter<N> nodeFilter) {
        Class<?> filterClass = evaluateFilterParameterType(nodeFilter);
        return filterNode(node, nodeFilter, filterClass);
    }

    @Nonnull
    public <N extends AbstractNode<?>> List<N> filterNodeList(@Nonnull List<? extends AbstractNode<?>> nodeList, @Nonnull INodeFilter<N> nodeFilter) {
        Class<?> filterClass = evaluateFilterParameterType(nodeFilter);
        List<N> resultNodeList = new ArrayList<N>(nodeList.size());
        for (AbstractNode<?> node : nodeList) {
            N castNode = filterNode(node, nodeFilter, filterClass);
            if (castNode != null) resultNodeList.add(castNode);
        }
        return resultNodeList;
    }

    @SuppressWarnings("unchecked")
    private <N extends AbstractNode<?>> N filterNode(@Nonnull AbstractNode<?> node, @Nonnull INodeFilter<N> nodeFilter, Class<?> filterClass) {
        if (filterClass.isAssignableFrom(node.getClass())) {
            N castNode = (N) node;
            return nodeFilter.filter(castNode) ? castNode : null;
        } else {
            return null;
        }
    }
}
