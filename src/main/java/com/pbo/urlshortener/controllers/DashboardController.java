package com.pbo.urlshortener.controllers;

import com.pbo.urlshortener.models.User;
import com.pbo.urlshortener.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = {"/", "/dashboard"})
    public ModelAndView dashboard(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
    public ModelAndView dashboard(HttpServletRequest request, Model model) {
        String email = request.getUserPrincipal().getName();
        User user = userRepository.findByEmail(email);
        String requestServletPath = request.getServletPath();

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("user", user);
        // resources/templates/pages/dashboard.html
        modelAndView.setViewName(requestServletPath.equals("/") ? "redirect:/dashboard" : "pages/dashboard");
        return modelAndView;
    }

}
