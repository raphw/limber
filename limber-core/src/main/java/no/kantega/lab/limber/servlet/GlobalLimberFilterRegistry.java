package no.kantega.lab.limber.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public final class GlobalLimberFilterRegistry {

    private static final GlobalLimberFilterRegistry instance = new GlobalLimberFilterRegistry();

    private final Map<UUID, LimberFilter> limberFilterMap;

    private final Map<LimberFilter, UUID> limberFilterUUIDMap;

    private GlobalLimberFilterRegistry() {
        this.limberFilterMap = new HashMap<UUID, LimberFilter>(1);
        this.limberFilterUUIDMap = new WeakHashMap<LimberFilter, UUID>(1);
    }

    private UUID findOrCreateUUIDInstance(LimberFilter limberFilter) {
        if (limberFilter == null) {
            throw new IllegalArgumentException("[limberFilter] must not be null");
        }
        UUID uuid = limberFilterUUIDMap.get(limberFilter);
        if (uuid != null) {
            return uuid;
        }
        uuid = UUID.randomUUID();
        putInstance(uuid, limberFilter);
        return uuid;
    }

    private LimberFilter findLimberSettingsInstance(UUID uuid) {
        return limberFilterMap.get(uuid);
    }

    private void putInstance(UUID uuid, LimberFilter limberFilter) {
        limberFilterMap.put(uuid, limberFilter);
        limberFilterUUIDMap.put(limberFilter, uuid);
    }

    public static UUID findOrCreateUUID(LimberFilter limberFilter) {
        return instance.findOrCreateUUIDInstance(limberFilter);
    }

    public static LimberFilter findLimberSettings(UUID uuid) {
        return instance.findLimberSettingsInstance(uuid);
    }


}
