package me.decentos.cache;

public class MyCacheImpl<K, V> implements MyCache<K, V> {

    @Override
    public void put(K key, V value) {

    }

    @Override
    public void remove(K key) {

    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void addListener(Listener<K, V> listener) {

    }

    @Override
    public void removeListener(Listener<K, V> listener) {

    }
}
