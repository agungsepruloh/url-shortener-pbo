package com.pbo.urlshortener.services;

import com.pbo.urlshortener.models.Url;

public interface UrlService {
    public void saveUrl(Url url);

    public boolean isUrlAlreadyPresent(Url url);

    public String generateString();
}
