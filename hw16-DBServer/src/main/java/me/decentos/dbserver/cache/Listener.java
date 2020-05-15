package me.decentos.dbserver.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Listener<K, V> {
    Logger logger = LoggerFactory.getLogger(Listener.class);

    default void notify(K key, V value, String action) {
        logger.info("[Default Listener] key:{}, value:{}, action: {}", key, value, action);
    }
}
