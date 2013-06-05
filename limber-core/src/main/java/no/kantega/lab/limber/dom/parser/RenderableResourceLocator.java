package no.kantega.lab.limber.dom.parser;

import no.kantega.lab.limber.exception.NotYetImplementedException;
import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.meta.ResourceIdentification;
import no.kantega.lab.limber.servlet.meta.ResourceType;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Locale;

public class RenderableResourceLocator {

    public InputStream locateResource(Class<? extends IRenderable> renderableClass) {

        ResourceIdentification resourceIdentification = renderableClass.getAnnotation(ResourceIdentification.class);
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
        if (StringUtils.isEmpty(resourceLocation)) {
            String resourceName = String.format("%s.%s",
                    renderableClass.getSimpleName(),
                    resourceType.toString().toLowerCase(Locale.US));
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
