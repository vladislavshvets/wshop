package com.internet.shop.security;

import com.internet.shop.exceptions.AuthenticationException;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;

@Service
public class AuthenticationServiceServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password)
            throws AuthenticationException {
        User userFromDB = userService.findByLogin(login).get();
        if (userService.findByLogin(login).isPresent()
                && userFromDB.getPassword().equals(password)) {
            return userFromDB;
        } else {
            throw new AuthenticationException("Incorrect login or password!");
        }
    }
}
