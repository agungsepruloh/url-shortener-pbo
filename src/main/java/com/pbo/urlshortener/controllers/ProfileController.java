package com.pbo.urlshortener.controllers;

import com.pbo.urlshortener.models.User;
import com.pbo.urlshortener.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest request, Model model) {
        String email = request.getUserPrincipal().getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/profile"); // resources/templates/pages/profile.html
        return modelAndView;
    }

}
