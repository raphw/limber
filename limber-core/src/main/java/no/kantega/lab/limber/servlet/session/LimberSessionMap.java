package no.kantega.lab.limber.servlet.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LimberSessionMap {

    private final ConcurrentMap<SessionKey<?>, Object> sessionObjects;

    public LimberSessionMap() {
        this.sessionObjects = new ConcurrentHashMap<SessionKey<?>, Object>();
    }
}
