package me.decentos.web.services;

import me.decentos.core.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
    void getUserData(long userId, Consumer<UserDTO> dataConsumer);

    void getAllUsersData(Consumer<List<UserDTO>> dataConsumer);

    void createUser(UserDTO userDTO, Consumer<UserDTO> dataConsumer);

    <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}
