package com.pbo.urlshortener.controllers;

import com.pbo.urlshortener.models.Url;
import com.pbo.urlshortener.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HashController {

    @Autowired
    UrlService urlService;

    @RequestMapping(value = "/{hash}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView findUrl(@PathVariable("hash") String hash) {
        Url url = urlService.findUrl(hash);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(url == null ? "pages/404" : "redirect:" + url.getOriginalUrl());
        return modelAndView;
    }
}
