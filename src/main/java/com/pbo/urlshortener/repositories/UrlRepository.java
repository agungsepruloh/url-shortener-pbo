package com.pbo.urlshortener.repositories;

import com.pbo.urlshortener.models.Url;
import com.pbo.urlshortener.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {
    public Url findByOriginalUrl(String originalUrl);

    public Url findByIdAndUser(int id, User user);

    public Url findByHash(String hash);
}
