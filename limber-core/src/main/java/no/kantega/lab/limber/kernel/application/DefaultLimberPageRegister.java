package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.exception.NotYetImplementedException;
import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.Deque;
import java.util.Iterator;
import java.util.UUID;

public class DefaultLimberPageRegister implements ILimberPageRegister {

    private final Deque<IRequestMapper> requestInterpreters;

    public DefaultLimberPageRegister(@Nonnull Deque<IRequestMapper> requestInterpreters) {
        this.requestInterpreters = requestInterpreters;
    }

    @Override
    public URI decodeLink(@Nonnull AbstractRenderable renderable) {
        throw new NotYetImplementedException();
    }

    @Override
    public URI decodeLink(@Nonnull UUID subroutineId) {
        throw new NotYetImplementedException();
    }

    @Override
    public URI decodeLink(@Nonnull Class<? extends AbstractRenderable> renderable) {
        return decodeLink(renderable, null);
    }

    @Override
    public URI decodeLink(@Nonnull Class<? extends AbstractRenderable> renderable, UUID versionId) {
        return decodeLink(renderable, versionId, null);
    }

    @Override
    public URI decodeLink(@Nonnull Class<? extends AbstractRenderable> renderable, UUID versionId, UUID subroutineId) {
        // Reversely iterate over the list of request interpreters.
        Iterator<IRequestMapper> iterator = requestInterpreters.descendingIterator();
        while (iterator.hasNext()) {
            IRequestMapper interpreter = iterator.next();
            URI link = interpreter.resolve(renderable, versionId, subroutineId);
            if (link != null) {
                return link;
            }
        }
        return null;
    }

    @Nonnull
    @Override
    public UUID register(@Nonnull AbstractRenderable renderable) {
        throw new NotYetImplementedException();
    }

    @Nonnull
    @Override
    public UUID register(@Nonnull Class<? extends AbstractRenderable> renderable) {
        throw new NotYetImplementedException();
    }
}
