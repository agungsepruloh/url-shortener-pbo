package com.pbo.urlshortener.controllers;

import com.pbo.urlshortener.models.Url;
import com.pbo.urlshortener.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UrlController {

    @Autowired
    UrlService urlService;

    /**
     *
     * @param url
     * @param bindingResult
     * @return Map of new URL status and message
     */
    @RequestMapping(value = "url", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> insertUrl(@Valid Url url, BindingResult bindingResult) {
        Map<String, String> map = new HashMap<String, String>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            map.put("status", "error");
            for (int i = 0; i < errors.size(); i++) {
                map.put("message", errors.get(i).getDefaultMessage());
            }
        } else if (urlService.isUrlAlreadyPresent(url)) {
            map.put("status", "error");
            map.put("message", "URL is already present");
        } else if (urlService.isHashAlreadyPresent(url)) {
            map.put("status", "error");
            map.put("message", "Hash is already used");
        } else {
            map = urlService.saveUrl(url);
        }

        return map;
    }

    /**
     *
     * @param url
     * @param bindingResult
     * @param id
     * @return Map of updated URL status and message
     */
    @RequestMapping(value = "url/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updateUrl(@Valid Url url, BindingResult bindingResult, @PathVariable("id") String id) {
        Map<String, String> map = new HashMap<String, String>();

        // Validate all user input
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            map.put("status", "error");
            for (int i = 0; i < errors.size(); i++) {
                map.put("message", errors.get(i).getDefaultMessage());
            }
        } else {
            url.setId(Integer.parseInt(id));
            map = urlService.updateUrl(url);
        }

        return map;
    }

}
