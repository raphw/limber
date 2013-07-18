package no.kantega.lab.limber.kernel.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class ReflectionInstanceCreator implements IInstanceCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionInstanceCreator.class);

    @Override
    public <U> U create(@Nonnull Class<? extends U> requestedClass) {
        try {
            return requestedClass.newInstance();
        } catch (Exception e) {
            LOGGER.info(String.format("Could not instanciate class %s", requestedClass.getCanonicalName()), e);
            return null;
        }
    }
}
