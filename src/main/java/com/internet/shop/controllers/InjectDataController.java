package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        userService.create(new User("Vlad", "Vlad", "1", Set.of(Role.of("USER"))));
        userService.create(new User("Billy", "Billy", "1", Set.of(Role.of("USER"))));
        userService.create(new User("Admin", "Admin", "1", Set.of(Role.of("ADMIN"))));
        shoppingCartService.create(new ShoppingCart(1L));
        shoppingCartService.create(new ShoppingCart(2L));
        productService.create(new Product("apple", 3));
        productService.create(new Product("banana", 5));
        productService.create(new Product("milk", 12));
        req.getRequestDispatcher("/WEB-INF/views/inject-data.jsp").forward(req, resp);
    }
}
