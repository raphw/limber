package no.kantega.lab.limber.util;

import com.google.common.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public abstract class PersistingCache<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistingCache.class);

    private Cache<K, V> underlyingCache;

    private final File persistenceDirectory;

    protected PersistingCache(@Nonnull File directory) {
        this.persistenceDirectory = validateDirectory(directory);
        this.underlyingCache = makeCache();
    }

    @Nonnull
    private File validateDirectory(@Nonnull File directory) {
        directory.mkdirs();
        if (!directory.exists() || !directory.isDirectory() || !directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException();
        }
        return directory;
    }

    private class PersistingRemovalListener implements RemovalListener<K, V> {
        @Nonnull
        @Override
        public void onRemoval(@Nonnull RemovalNotification<K, V> notification) {
            if (isPersistenceRelevant(notification.getCause())) {
                try {
                    persistValue(notification.getKey(), notification.getValue());
                } catch (IOException e) {
                    LOGGER.error(String.format("Could not persist key-value: %s, %s", notification.getKey(), notification.getValue()), e);
                }
            }
        }
    }

    public abstract class PersistedStateCacheLoader implements Callable<V> {

        private final K key;

        protected PersistedStateCacheLoader(@Nonnull K key) {
            this.key = key;
        }

        @Override
        public V call() throws Exception {
            return load(key);
        }

        @Nonnull
        private V load(@Nonnull K key) {
            V value = null;
            try {
                value = findValueOnDisk(key);
            } catch (Exception e) {
                LOGGER.error(String.format("Could not find value to key: %s", key), e);
            }
            if (value != null) {
                return value;
            } else {
                return makeValue(key);
            }
        }

        protected abstract V makeValue(@Nonnull K key);
    }

    @Nonnull
    private Cache<K, V> makeCache() {
        return CacheBuilder.newBuilder().softValues().removalListener(new PersistingRemovalListener()).build();
    }

    private boolean isPersistenceRelevant(RemovalCause removalCause) {
        return removalCause != RemovalCause.COLLECTED;
    }

    private V findValueOnDisk(@Nonnull K key) throws IOException {
        if (!isPersist(key)) return null;
        File persistenceFile = makePathToFile(persistenceDirectory, directoryFor(key));
        if (!persistenceFile.exists()) return null;
        FileInputStream fileInputStream = new FileInputStream(persistenceFile);
        try {
            FileLock fileLock = fileInputStream.getChannel().lock();
            try {
                return readPersisted(key, fileInputStream);
            } finally {
                fileLock.release();
            }
        } finally {
            fileInputStream.close();
        }
    }

    private void persistValue(@Nonnull K key, @Nonnull V value) throws IOException {
        if (!isPersist(key)) return;
        File persistenceFile = makePathToFile(persistenceDirectory, directoryFor(key));
        persistenceFile.getParentFile().mkdirs();
        persistenceFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(persistenceFile);
        try {
            FileLock fileLock = fileOutputStream.getChannel().lock();
            try {
                persist(key, value, fileOutputStream);
            } finally {
                fileLock.release();
            }
        } finally {
            fileOutputStream.close();
        }
    }

    @Nonnull
    private File makePathToFile(@Nonnull File rootDir, @Nonnull List<String> pathSegments) {
        File persistenceFile = rootDir;
        for (String pathSegment : pathSegments) {
            persistenceFile = new File(persistenceFile, pathSegment);
        }
        if (rootDir.equals(persistenceFile) || persistenceFile.isDirectory()) {
            throw new IllegalArgumentException();
        }
        return persistenceFile;
    }

    public V getIfPresent(@Nonnull K key) {
        return underlyingCache.getIfPresent(key);
    }

    public V get(@Nonnull K key, @Nonnull PersistedStateCacheLoader valueMaker) {
        try {
            return underlyingCache.get(key, valueMaker);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void put(@Nonnull K key, @Nonnull V value) {
        underlyingCache.put(key, value);
    }

    public void remove(@Nonnull K key) {
        underlyingCache.invalidate(key);
    }

    @SuppressWarnings("unchecked")
    public boolean invalidate() {
        Cache<K, V> formerCache = underlyingCache;
        underlyingCache = new InvalidatedCache();
        formerCache.invalidateAll();
        return persistenceDirectory.delete();
    }

    @Nonnull
    protected Cache<K, V> getUnderlyingCache() {
        return underlyingCache;
    }

    @Nonnull
    protected abstract List<String> directoryFor(@Nonnull K key);

    protected abstract void persist(@Nonnull K key, @Nonnull V value, @Nonnull OutputStream outputStream) throws IOException;

    @Nonnull
    protected abstract V readPersisted(@Nonnull K key, @Nonnull InputStream inputStream) throws IOException;

    protected abstract boolean isPersist(@Nonnull K key);

}
