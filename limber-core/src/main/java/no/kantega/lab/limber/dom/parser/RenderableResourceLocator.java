package no.kantega.lab.limber.dom.parser;


import no.kantega.lab.limber.exception.NotYetImplementedException;
import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.meta.ResourceIdentification;
import no.kantega.lab.limber.servlet.meta.ResourceType;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.InputStream;

public class RenderableResourceLocator {

    public InputStream locateResource(@Nonnull Class<? extends AbstractRenderable> renderableClass) {

        ResourceIdentification resourceIdentification =
                renderableClass.getAnnotation(ResourceIdentification.class);

        ResourceType resourceType;
        String resourceLocation;
        if (resourceIdentification == null) {
            resourceType = ResourceType.TXT;
            resourceLocation = "";
        } else {
            resourceType = resourceIdentification.value();
            resourceLocation = resourceIdentification.location();
        }

        InputStream inputStream;
        if (StringUtils.isBlank(resourceLocation)) {
            String resourceName = String.format("%s%s%s",
                    renderableClass.getSimpleName(),
                    resourceType.getFileNameExtension() == null ? "" : ".",
                    StringUtils.defaultString(resourceType.getFileNameExtension()));
            inputStream = renderableClass.getResourceAsStream(resourceName);
        } else {
            throw new NotYetImplementedException();
        }

        if (inputStream == null) {
            throw new RuntimeException("Cannot find resource.");
        }

        return inputStream;
    }
}
