package me.decentos.web.controller;

import me.decentos.core.model.User;
import me.decentos.core.service.ServiceException;
import me.decentos.core.service.UserService;
import me.decentos.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public List<UserDto> findAllUsers() {
        return userService.getAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/api/user/{id}")
    public UserDto findUserById(@PathVariable("id") String id) {
        return new UserDto(userService.getUser(id).orElse(null));
    }

    @PostMapping("/api/user")
    public ResponseEntity<String> createUser(@RequestBody UserDto data) {
        User newUser = new User(data.getName());
        newUser.setLogin(data.getLogin());
        newUser.setPassword(data.getPassword());
        newUser.setAdmin(data.isAdmin());

        try {
            userService.saveUser(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_HTML)
                    .body(e.getMessage());
        }
    }
}
