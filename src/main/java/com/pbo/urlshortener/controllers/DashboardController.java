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
public class DashboardController {

    @Autowired
    UserRepository userRepository;
    UrlRepository urlRepository;
    String search;

    @RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
    public ModelAndView dashboard(HttpServletRequest request, Model model, @RequestParam(value="search", required = false) String search) {
        String email = request.getUserPrincipal().getName();
        User user = userRepository.findByEmail(email);
        String requestServletPath = request.getServletPath();

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("user", user);
        if (search != null && search != "") {
            Url url = urlRepository.findByTitle(search);
            modelAndView.addObject("url", url);
        }else{
            Url url = new Url();
            modelAndView.addObject("url", url);
        }
        modelAndView.setViewName(requestServletPath.equals("/") ? "redirect:/dashboard" : "pages/dashboard");
        return modelAndView;
    }

}