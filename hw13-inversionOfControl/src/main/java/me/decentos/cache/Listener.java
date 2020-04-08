package me.decentos.cache;

public interface Listener<K, V> {
    void notify(K key, V value, CacheAction action);
}
