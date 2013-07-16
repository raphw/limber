package no.kantega.lab.limber.kernel.creator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;

public class DefaultInstanceCreationMapper implements IInstanceCreationMapper {

    private final Multimap<Class<? extends AbstractRenderable>, IInstanceCreator> instanceCreatorMultimap;

    public DefaultInstanceCreationMapper() {
        this.instanceCreatorMultimap = ArrayListMultimap.create();
    }

    @Nonnull
    @Override
    public IInstanceCreationMapper addCreator(@Nonnull Class<? extends AbstractRenderable> createdClass, @Nonnull IInstanceCreator instanceCreator) {
        instanceCreatorMultimap.put(createdClass, instanceCreator);
        return this;
    }

    @Nonnull
    @Override
    public IInstanceCreationMapper removeCreator(@Nonnull IInstanceCreator instanceCreator) {
        instanceCreatorMultimap.values().remove(instanceCreator);
        return this;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public AbstractRenderable create(@Nonnull IRequestMapping requestMapping) {
        Class<?> renderableSuperClass = requestMapping.getRenderableClass();
        do {
            Class<? extends AbstractRenderable> renderableClass = (Class<? extends AbstractRenderable>) renderableSuperClass;
            for (IInstanceCreator instanceCreator : instanceCreatorMultimap.get(renderableClass)) {
                AbstractRenderable renderable = instanceCreator.create(requestMapping);
                if (renderable != null) return renderable;
            }
            renderableSuperClass = renderableClass.getSuperclass();
        } while (renderableSuperClass != Object.class);
        throw new IllegalArgumentException();
    }

    @Override
    public int size() {
        return instanceCreatorMultimap.size();
    }
}
