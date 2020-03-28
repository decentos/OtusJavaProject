package me.decentos.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCacheDemo {
  private static final Logger logger = LoggerFactory.getLogger(MyCacheDemo.class);

  public static void main(String[] args) {
    new MyCacheDemo().demo();
  }

  private void demo() {
    MyCache<Integer, Integer> cache = new MyCacheImpl<>();

    // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
    Listener<Integer, Integer> listener = new Listener<>() {
      @Override
      public void notify(Integer key, Integer value, String action) {
        logger.info("key:{}, value:{}, action: {}", key, value, action);
      }
    };

    cache.addListener(listener);
    cache.put(1, 1);

    logger.info("getValue:{}", cache.get(1));
    cache.remove(1);
    cache.removeListener(listener);
  }
}
