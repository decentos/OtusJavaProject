package me.decentos.web.controller;

import me.decentos.core.dto.UserDTO;
import me.decentos.web.services.FrontendService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class UserWSController {

    private final FrontendService frontendService;
    private final SimpMessagingTemplate template;

    public UserWSController(FrontendService frontendService, SimpMessagingTemplate template) {
        this.frontendService = frontendService;
        this.template = template;
    }

    @MessageMapping("/getUser.{userId}")
    public void getUserById(@DestinationVariable long userId) {
        frontendService.getUserData(userId, data -> this.template.convertAndSend("/topic/user", data));
    }

    @MessageMapping("/getAllUsers")
    public void getAllUsers() {
        frontendService.getAllUsersData(data -> this.template.convertAndSend("/topic/allUsers", data));
    }

    @MessageMapping("/createUser")
    public void createUser(UserDTO userDTO) {
        frontendService.createUser(userDTO, data -> this.template.convertAndSend("/topic/user", data));
    }


}
