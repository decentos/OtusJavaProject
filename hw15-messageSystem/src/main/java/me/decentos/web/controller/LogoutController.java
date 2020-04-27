package me.decentos.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public RedirectView logout(HttpSession httpSession) {
        httpSession.invalidate();
        RedirectView redirectView = new RedirectView("/", true);
        return redirectView;
    }

}
