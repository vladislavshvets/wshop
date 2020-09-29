package com.internet.shop.dao.impl;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.db.Storage;
import com.internet.shop.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        List<Order> orders = getAll();
        for (int i = 0; i <= orders.size(); i++) {
            if (orders.get(i).getId().equals(order.getId())) {
                orders.set(i, order);
                return order;
            }
        }
        throw new IllegalArgumentException("The order [" + order.getId()
                + "] doesn't exist in storage!");
    }

    @Override
    public Optional<Order> getById(Long id) {
        return getAll().stream().filter(order -> order.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public boolean deleteById(Long id) {
        return Storage.orders.removeIf(order -> order.getId().equals(id));
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return Storage.orders.stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
