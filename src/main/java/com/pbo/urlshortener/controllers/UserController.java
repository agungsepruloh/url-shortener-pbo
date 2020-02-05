package com.pbo.urlshortener.controllers;

import com.pbo.urlshortener.models.Url;
import com.pbo.urlshortener.models.User;
import com.pbo.urlshortener.repositories.UrlRepository;
import com.pbo.urlshortener.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class UserController {
    @Autowired
    UserRepository userRepository;
    UrlRepository urlRepository;

    @RequestMapping(value = {"/user"}, method = RequestMethod.GET)
    public ModelAndView dashboard(HttpServletRequest request, Model model) {
        User user = new User();
        String requestServletPath = request.getServletPath();

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("user", user);
        modelAndView.setViewName(requestServletPath.equals("/user") ? "redirect:/user" : "pages/user");
        return modelAndView;
    }
}
