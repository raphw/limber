package no.kantega.lab.limber.servlet.context;

import no.kantega.lab.limber.exception.NotYetImplementedException;
import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.request.interpreter.IRequestInterpreter;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.Deque;
import java.util.Iterator;
import java.util.UUID;

public class DefaultLimberPageRegister implements ILimberPageRegister {

    private final Deque<IRequestInterpreter> requestInterpreters;

    public DefaultLimberPageRegister(@Nonnull Deque<IRequestInterpreter> requestInterpreters) {
        this.requestInterpreters = requestInterpreters;
    }

    @Override
    public URI decodeLink(@Nonnull IRenderable renderable) {
        throw new NotYetImplementedException();
    }

    @Override
    public URI decodeLink(@Nonnull UUID subroutineId) {
        throw new NotYetImplementedException();
    }

    @Override
    public URI decodeLink(@Nonnull Class<? extends IRenderable> renderable) {
        return decodeLink(renderable, null);
    }

    @Override
    public URI decodeLink(@Nonnull Class<? extends IRenderable> renderable, UUID versionId) {
        return decodeLink(renderable, versionId, null);
    }

    @Override
    public URI decodeLink(@Nonnull Class<? extends IRenderable> renderable, UUID versionId, UUID subroutineId) {
        // Reversely iterate over the list of request interpreters.
        Iterator<IRequestInterpreter> iterator = requestInterpreters.descendingIterator();
        while (iterator.hasNext()) {
            IRequestInterpreter interpreter = iterator.next();
            URI link = interpreter.resolve(renderable, versionId, subroutineId);
            if (link != null) {
                return link;
            }
        }
        return null;
    }

    @Nonnull
    @Override
    public UUID register(@Nonnull IRenderable renderable) {
        throw new NotYetImplementedException();
    }

    @Nonnull
    @Override
    public UUID register(@Nonnull Class<? extends IRenderable> renderable) {
        throw new NotYetImplementedException();
    }
}
