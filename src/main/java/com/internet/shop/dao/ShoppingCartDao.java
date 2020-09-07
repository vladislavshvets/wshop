package com.internet.shop.dao;

import com.internet.shop.model.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getById(Long id);

    List<ShoppingCart> getAll();

    boolean deleteById(Long id);
}
