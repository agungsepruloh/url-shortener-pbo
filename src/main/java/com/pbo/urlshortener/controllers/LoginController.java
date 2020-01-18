package com.pbo.urlshortener.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping(value = {"/login" }, method = RequestMethod.GET)
    public ModelAndView login(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        // resources/templates/pages/login.html
        modelAndView.setViewName(principal == null ? "pages/login" : "redirect:/dashboard");
        return modelAndView;
    }
}
