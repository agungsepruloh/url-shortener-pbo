package com.pbo.urlshortener.services;

import com.pbo.urlshortener.models.Url;

import java.util.Map;

public interface UrlService {
    public Map<String, String> saveUrl(Url url);

    public boolean isUrlAlreadyPresent(Url url);

    public boolean isHashAlreadyPresent(Url url);

    public String generateString();
}
