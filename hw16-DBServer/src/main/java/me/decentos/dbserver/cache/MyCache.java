package me.decentos.dbserver.cache;

import java.util.Optional;

public interface MyCache<K, V> {

    void put(K key, V value);

    void remove(K key);

    Optional<V> get(K key);

    void addListener(Listener listener);

    void removeListener(Listener listener);

}
