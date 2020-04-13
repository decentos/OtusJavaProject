package me.decentos.cache;

import me.decentos.core.cache.UserCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Component(UserCache.NAME)
public class MyCacheImpl<K, V> implements MyCache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyCacheImpl.class);

    private final Map<K, V> data = new WeakHashMap<>();
    private final List<Listener<K,V>> listeners = new ArrayList<>();


    @Override
    public void put(K key, V value) {
        data.put(key, value);
        notifyListeners(key, value, CacheAction.ADD_TO_CACHE);
    }

    @Override
    public void remove(K key) {
        final V value = data.remove(key);
        notifyListeners(key, value, CacheAction.REMOVE_FROM_CACHE);
    }

    @Override
    public V get(K key) {
        return data.get(key);
    }

    @Override
    public void addListener(Listener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, CacheAction action) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                logger.error("Error on listener {} notification (key: {}, value: {}, action: {}): ",
                        listener, key, value, action, e);
            }
        });
    }
}
