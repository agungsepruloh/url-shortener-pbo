package com.pbo.urlshortener.controllers;

import com.pbo.urlshortener.models.User;
import com.pbo.urlshortener.repositories.UserRepository;
import com.pbo.urlshortener.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest request,  Model model) {
        String email = request.getUserPrincipal().getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/profile"); // resources/templates/pages/profile.html
        return modelAndView;
    }


    @RequestMapping(value = {"/profile/update"}, method = RequestMethod.POST)
    public ModelAndView update_profile(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();

            userService.updateUser(user);
            modelAndView.addObject("successMessage", "User successfully updated!");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("pages/profile");

        return new ModelAndView("redirect:/profile");
    }

}
