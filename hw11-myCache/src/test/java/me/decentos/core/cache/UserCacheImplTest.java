package me.decentos.core.cache;

import me.decentos.cache.CacheAction;
import me.decentos.cache.Listener;
import me.decentos.core.model.AddressDataSet;
import me.decentos.core.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Кэш пользователей должен ")
class UserCacheImplTest {

    private UserCache cache;
    private User user;

    @BeforeEach
    void setUp() {
        cache = new UserCacheImpl();

        user = new User("Ivan");
        user.setAddress(new AddressDataSet("Lenina"));
    }

    @Test
    @DisplayName(" добавлять пользователя в кэш и извлекать из кэша.")
    void shouldAddUserToCacheAndGet() {
        cache.add(user);

        Optional<User> mayBeUser = cache.get(user.getId());
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser).get().isEqualToComparingFieldByField(user);
    }

    @Test
    @DisplayName(" добавлять пользователя в кэш и очищаться после GC.")
    void shouldAddUserToCache() throws InterruptedException {
        cache.add(user);

        System.gc();
        Thread.sleep(500);

        Optional<User> mayBeUser = cache.get(user.getId());
        assertThat(mayBeUser).isNotPresent();
    }

    @Test
    @DisplayName(" удалять пользователя из кэша.")
    void shouldRemoveUserFromCache() {
        cache.add(user);

        cache.remove(user);

        Optional<User> mayBeUser = cache.get(user.getId());
        assertThat(mayBeUser).isNotPresent();
    }

    @Test
    @DisplayName(" добавлять UserCacheListener и вызывать его при добавлении/удаления пользователя.")
    void shouldAddUserCacheListenerAndNotifyIt() {
        AtomicReference<User> userFromListener = new AtomicReference<>();
        AtomicReference<CacheAction> actionFromListener = new AtomicReference<>();
        Listener<String, User> listener = (String key, User user, CacheAction action) -> {
            userFromListener.set(user);
            actionFromListener.set(action);
        };
        cache.addListener(listener);

        cache.add(user);

        assertThat(userFromListener).hasValue(user);
        assertThat(actionFromListener).hasValue(CacheAction.ADD_TO_CACHE);

        cache.remove(user);

        assertThat(userFromListener).hasValue(user);
        assertThat(actionFromListener).hasValue(CacheAction.REMOVE_FROM_CACHE);

        cache.removeListener(listener);
    }
}