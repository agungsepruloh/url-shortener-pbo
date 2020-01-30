package com.pbo.urlshortener.services;

import com.pbo.urlshortener.models.Url;
import com.pbo.urlshortener.models.User;

import java.util.Map;

public interface UrlService {
    public Map<String, String> saveUrl(Url url);

    public Map<String, String> updateUrl(Url url);

    public Map<String, String> deleteUrl(int id);

    public boolean isUrlAlreadyPresent(Url url);

    public boolean isHashAlreadyPresent(Url url);

    public String generateString();

}
