package com.pbo.urlshortener.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {

    @RequestMapping(value = {"/", "/dashboard"})
    public ModelAndView dashboard(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String requestServletPath = request.getServletPath();
        // resources/templates/pages/dashboard.html
        modelAndView.setViewName(requestServletPath.equals("/") ? "redirect:/dashboard" : "pages/dashboard");
        return modelAndView;
    }

}
