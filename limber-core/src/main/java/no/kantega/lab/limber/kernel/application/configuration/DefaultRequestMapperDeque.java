package no.kantega.lab.limber.kernel.application.configuration;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;
import no.kantega.lab.limber.kernel.request.IHttpServletRequestWrapper;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

public class DefaultRequestMapperDeque extends LinkedList<IRequestMapper> implements IRequestMapperDeque {

    @Override
    public IRequestMapping map(@Nonnull IHttpServletRequestWrapper rawRequest) {
        for (IRequestMapper requestMapper : this) {
            IRequestMapping requestMapping = requestMapper.map(rawRequest);
            if (requestMapping != null) return requestMapping;
        }
        return null;
    }

    @Override
    public URI resolve(@Nonnull Class<? extends AbstractRenderable> renderableClass, UUID versionId, UUID ajaxId) {
        Iterator<IRequestMapper> descendingIterator = descendingIterator();
        while (descendingIterator.hasNext()) {
            IRequestMapper requestMapper = descendingIterator.next();
            URI uri = requestMapper.resolve(renderableClass, versionId, ajaxId);
            if (uri != null) return uri;
        }
        return null;
    }
}
