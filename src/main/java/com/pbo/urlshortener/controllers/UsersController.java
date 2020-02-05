package com.pbo.urlshortener.controllers;

import com.pbo.urlshortener.models.Url;
import com.pbo.urlshortener.models.User;
import com.pbo.urlshortener.repositories.UrlRepository;
import com.pbo.urlshortener.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public ModelAndView users(HttpServletRequest request, Model model) {
        User user = new User();
        String requestServletPath = request.getServletPath();

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("user", user);
        modelAndView.setViewName("pages/users");
        return modelAndView;
    }
}
