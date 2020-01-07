package com.pbo.urlshortener.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

    @RequestMapping(value = {"/dashboard"})
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/dashboard"); // resources/templates/pages/dashboard.html
        return modelAndView;
    }

}
