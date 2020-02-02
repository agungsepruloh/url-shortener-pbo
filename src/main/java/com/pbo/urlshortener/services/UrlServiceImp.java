package com.pbo.urlshortener.services;

import com.pbo.urlshortener.models.Url;
import com.pbo.urlshortener.models.User;
import com.pbo.urlshortener.repositories.UrlRepository;
import com.pbo.urlshortener.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UrlServiceImp implements UrlService {

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * @param url
     * @return Map of new URL status and message
     */
    @Override
    public Map<String, String> saveUrl(Url url) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.findByEmail(email);

        if (url.getHash().length() < 3) url.setHash(generateString());
        url.setCreateDate(dateTimeFormatter.format(now));
        url.setUser(user);
        Map<String, String> map = new HashMap<String, String>();

        try {
            urlRepository.save(url);
            map.put("status", "success");
            map.put("message", "New URL successfully saved");
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
        }

        return map;
    }

    /**
     * @param newUrl
     * @return Map of updated URL status and message
     */
    @Override
    public Map<String, String> updateUrl(Url newUrl) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Url url = urlRepository.findByIdAndUser(newUrl.getId(), user);
        Map<String, String> map = new HashMap<String, String>();
        Boolean isValid = true;

        if (url != null) {
            Url existedOriginalUrl = urlRepository.findByOriginalUrl(newUrl.getOriginalUrl());
            Url existedHashUrl = urlRepository.findByHash(newUrl.getHash());

            // Check is an unique original URL
            if (existedOriginalUrl != null) if (newUrl.getId() != existedOriginalUrl.getId()) {
                isValid = false;
                map.put("status", "error");
                map.put("message", "URL is already present");
            }

            // Check is an unique hash
            if (existedHashUrl != null) if (newUrl.getId() != existedHashUrl.getId()) {
                isValid = false;
                map.put("status", "error");
                map.put("message", "Hash is already used");
            }

        } else {
            map.put("status", "error");
            map.put("message", "URL not found.");
        }

        // Save new URL data if all user input is correct
        if (isValid) {
            url.setTitle(newUrl.getTitle());
            url.setOriginalUrl(newUrl.getOriginalUrl());
            url.setHash(newUrl.getHash());
            try {
                urlRepository.save(url);
                map.put("status", "success");
                map.put("message", "URL successfully edited");
            } catch (Exception e) {
                map.put("status", "error");
                map.put("message", e.getMessage());
            }
        }

        return map;
    }

    /**
     *
     * @param id
     * @return Map of updated URL status and message
     */
    @Override
    public Map<String, String> deleteUrl(int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Url url = urlRepository.findByIdAndUser(id, user);
        Map<String, String> map = new HashMap<String, String>();

        if (url != null) {
            try {
                urlRepository.delete(url);
                map.put("status", "success");
                map.put("message", "URL successfully deleted");
            } catch (Exception e) {
                map.put("status", "error");
                map.put("message", e.getMessage());
            }
        } else {
            map.put("status", "error");
            map.put("message", "URL not found.");
        }

        return map;
    }

    @Override
    public Url findUrl(String hash) {
        return urlRepository.findByHash(hash);
    }

    /**
     * @param url
     * @return boolean
     * Check is an URL is already present or not
     */
    @Override
    public boolean isUrlAlreadyPresent(Url url) {
        Url existedUrl = urlRepository.findByOriginalUrl(url.getOriginalUrl());
        if (existedUrl != null)
            return true;
        return false;
    }

    /**
     * @param url
     * @return boolean
     * Check is a hash is already used or not
     */
    @Override
    public boolean isHashAlreadyPresent(Url url) {
        Url existedUrl = urlRepository.findByHash(url.getHash());
        if (existedUrl != null)
            return true;
        return false;
    }

    /**
     * @return generated random string (6 characters)
     */
    @Override
    public String generateString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Url url = urlRepository.findByHash(generatedString);
        if (url != null) generateString();
        return generatedString;
    }

}
