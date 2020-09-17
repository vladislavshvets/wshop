package com.internet.shop.web.filters;

import com.internet.shop.controllers.user.LoginController;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    private static final Injector injector =
            Injector.getInstance("com.internet.shop");
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private final Map<String, List<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/users/all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/users/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/add", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/admin", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/shopping-carts/products", List.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-carts/products/add", List.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-carts/products/delete", List.of(Role.RoleName.USER));
        protectedUrls.put("/orders/by-user", List.of(Role.RoleName.USER));
        protectedUrls.put("/order/complete", List.of(Role.RoleName.USER));
        protectedUrls.put("/products/user-view-all-products", List.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestedUrl = req.getServletPath();
        if (!protectedUrls.containsKey(requestedUrl)) {
            chain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(LoginController.USER_ID);
        User user = userService.get(userId).get();
        if (isAuthorized(user, protectedUrls.get(requestedUrl))) {
            chain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/access-denied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, List<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
