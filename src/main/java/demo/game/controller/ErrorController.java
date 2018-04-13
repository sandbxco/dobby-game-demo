package demo.game.controller;


import demo.game.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
    @Autowired
    private Env env;

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView model = new ModelAndView("error");
        model.addObject("authorize_url", env.getAuthorizeUrl());

        return model;
    }
}