package com.pbo.urlshortener.controllers;

import com.pbo.urlshortener.models.Url;
import com.pbo.urlshortener.services.UrlService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UrlController {

    @Autowired
    UrlService urlService;

    @RequestMapping(value = "urls", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> addUrl(@Valid Url url, BindingResult bindingResult, ModelMap modelMap) throws JSONException {
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
        } if (urlService.isHashAlreadyPresent(url)) {
            map.put("status", "error");
            map.put("message", "Hash is already used");
        } else {
            map = urlService.saveUrl(url);
        }

        return map;
    }

}
