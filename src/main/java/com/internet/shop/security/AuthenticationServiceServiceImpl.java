package com.internet.shop.security;

import com.internet.shop.exceptions.AuthenticationException;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.util.Optional;

@Service
public class AuthenticationServiceServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password)
            throws AuthenticationException {
        Optional<User> userFromDB = userService.findByLogin(login);
        if (userFromDB.isEmpty()
                || !userFromDB.get().getPassword().equals(password)) {
            throw new AuthenticationException("Incorrect login or password!");
        }
        return userFromDB.get();
    }
}
