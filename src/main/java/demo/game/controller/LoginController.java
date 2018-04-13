package demo.game.controller;

import demo.game.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @Autowired
    private Env env;

    @GetMapping
    public ModelAndView root() {
        ModelAndView model = new ModelAndView("login");
        model.addObject("authorize_url", env.getAuthorizeUrl());
        model.addObject("dobby_url", env.getDobbyUrl());

        return model;
    }
}