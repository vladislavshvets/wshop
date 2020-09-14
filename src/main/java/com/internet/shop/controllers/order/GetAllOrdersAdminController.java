package com.internet.shop.controllers.order;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllOrdersAdminController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private ShoppingCartService cartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> orderList = orderService.getAll();

        req.setAttribute("orders", orderList);
        req.getRequestDispatcher("/WEB-INF/views/orders/all-orders-forAdmin.jsp")
                .forward(req, resp);
    }
}
