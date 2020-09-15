package com.internet.shop.security;

import com.internet.shop.exceptions.AuthenticationException;
import com.internet.shop.model.User;

import java.util.Optional;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
