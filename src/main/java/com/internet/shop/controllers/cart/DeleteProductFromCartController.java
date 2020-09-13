package com.internet.shop.controllers.cart;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductFromCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector =
            Injector.getInstance("com.internet.shop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        String productId = req.getParameter("id");
        long id = Long.parseLong(productId);
        Product product = productService.getById(id);
        shoppingCartService.deleteProduct(shoppingCart,product);
        resp.sendRedirect(req.getContextPath() + "/shopping-carts/products");
    }
}
