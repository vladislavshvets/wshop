package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;

public class AppMain {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        productService.create(new Product("apple", 3));
        productService.create(new Product("banana", 5));
        productService.create(new Product("milk", 12));
        System.out.println("All products:");
        productService.getAllProducts().forEach(System.out::println);
        System.out.println();
        UserService userService =
                (UserService) injector.getInstance(UserService.class);
        userService.create(new User("Misha", "Misha", "Misha"));
        userService.create(new User("Sasha", "Sasha", "Sasha"));
        System.out.println("All users:");
        userService.getAll().forEach(System.out::println);
        System.out.println();
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart user1 = new ShoppingCart(1L);
        ShoppingCart user2 = new ShoppingCart(2L);
        shoppingCartService.create(user1);
        shoppingCartService.create(user2);
        shoppingCartService.addProduct(user1, productService.getById(1L));
        shoppingCartService.addProduct(user1, productService.getById(2L));
        shoppingCartService.addProduct(user1, productService.getById(3L));
        user1.getProducts().forEach(System.out::println);
        System.out.println();
        System.out.println("Deleted first product from user1 cart:");
        shoppingCartService.deleteProduct(user1, productService.getById(3L));
        user1.getProducts().forEach(System.out::println);
        System.out.println();
        OrderService orderService =
                (OrderService) injector.getInstance(OrderService.class);
        System.out.println("Placing an order from user1 cart:");
        orderService.completeOrder(user1);
        System.out.println("user1 cart (must be empty now): " + user1.getProducts());
        System.out.println();
        System.out.println("Placing one more order for user1 and user2");
        shoppingCartService.addProduct(user1, productService.getById(3L));
        orderService.completeOrder(user1);
        shoppingCartService.addProduct(user2, productService.getById(2L));
        orderService.completeOrder(user2);
        System.out.println("User1 orders:");
        System.out.println(orderService.getUserOrders(1L));
        System.out.println("User2 orders:");
        System.out.println(orderService.getUserOrders(2L));
    }
}
