package com.pbo.urlshortener.services;

import com.pbo.urlshortener.models.User;

public interface UserService {
    public void saveUser(User user);

    public void updateUser(User user);

    public boolean isUserAlreadyPresent(User user);
}
