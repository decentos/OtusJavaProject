package me.decentos.dbserver.cache;

import org.springframework.stereotype.Component;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;

@Component
public class MyCacheImpl<K, V> implements MyCache<K, V>, Listener<K, V> {
    private final static String ENTITY_CACHED = "Entity is cached.";
    private final static String ENTITY_LOADED = "Entity is loaded from cache.";
    private final static String ENTITY_REMOVED = "Entity is removed from cache.";

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<WeakReference<Listener<K, V>>> listeners = new ArrayList<>();
    private final ReferenceQueue<Listener<K, V>> listenerReferenceQueue = new ReferenceQueue<>();

    @Override
    public void put(K key, V value) {
        cleanUp();
        cache.put(key, value);
        notify(key, value, ENTITY_CACHED);
        notifyAllListeners(key, value, ENTITY_CACHED);
    }

    @Override
    public void remove(K key) {
        cleanUp();
        V oldValue = cache.remove(key);
        notify(key, oldValue, ENTITY_REMOVED);
        notifyAllListeners(key, oldValue, ENTITY_REMOVED);
    }

    @Override
    public Optional<V> get(K key) {
        cleanUp();
        V value = cache.get(key);

        if (value != null) {
            notify(key, value, ENTITY_LOADED);
            notifyAllListeners(key, value, ENTITY_LOADED);
        }
        return Optional.ofNullable(value);
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(new WeakReference<Listener<K, V>>(listener, listenerReferenceQueue));
    }

    @Override
    public void removeListener(Listener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    private void cleanUp() {
        WeakReference listenerWeakRef = (WeakReference) listenerReferenceQueue.poll();

        while (listenerWeakRef != null) {
            listeners.remove(listenerWeakRef);
            listenerWeakRef = (WeakReference) listenerReferenceQueue.poll();
        }
    }

    private void notifyAllListeners(K key, V value, String action) {
        listeners.forEach(weakRefListener -> {
            Listener<K, V> listener = weakRefListener.get();
            if (listener != null) {
                try {
                    listener.notify(key, value, action);
                } catch (Exception e) {
                    logger.error("Exception at listener", e);
                }
            }
        });
    }
}
