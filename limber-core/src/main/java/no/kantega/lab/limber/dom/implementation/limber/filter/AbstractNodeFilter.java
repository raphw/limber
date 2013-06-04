package no.kantega.lab.limber.dom.implementation.limber.filter;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractNodeFilter<T extends AbstractNode> {

    private transient Class<T> parameterClass;

    public abstract boolean filter(T element);

    @SuppressWarnings("unchecked")
    public Class<T> getParameterClass() {

        if(parameterClass != null) {
            return parameterClass;
        }

        Class<?> nodeFilterSubclass = this.getClass();
        while (AbstractNodeFilter.class != nodeFilterSubclass.getSuperclass()) {
            nodeFilterSubclass = nodeFilterSubclass.getSuperclass();
        }

        ParameterizedType parameterizedType = (ParameterizedType) nodeFilterSubclass.getGenericSuperclass();
        return parameterClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }
}
