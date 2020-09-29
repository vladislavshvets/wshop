package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = "SELECT * FROM orders WHERE user_id = ? AND deleted = FALSE ";
        List<Order> userOrders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, userId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    userOrders.add(getOrderFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Couldn't find orders by userId: " + userId, e);
        }
        for (Order order : userOrders) {
            order.setProducts(getProductListByOrderId(order.getId()));
        }
        return userOrders;
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?) ";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Couldn't create provided orderId: " + order.getId(), e);
        }
        addProducts(order.getProducts(), order.getId());
        return order;
    }

    @Override
    public Optional<Order> getById(Long orderId) {
        String query = "SELECT * FROM orders WHERE order_id = ? AND deleted = FALSE ";
        List<Product> productsInOrder = getProductListByOrderId(orderId);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order searchedOrder = getOrderFromResultSet(resultSet);
                searchedOrder.setProducts(productsInOrder);
                return Optional.of(searchedOrder);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't find order by specified orderID: "
                    + orderId, e);
        }
    }

    @Override
    public Order update(Order order) {
        deleteProducts(order.getId());
        addProducts(order.getProducts(), order.getId());
        return order;
    }

    @Override
    public boolean deleteById(Long orderId) {
        String query = "UPDATE orders SET deleted = TRUE WHERE order_id = ? AND deleted = FALSE ";
        deleteProducts(orderId);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete order by specified orderID: "
                    + orderId, e);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> allOrders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE deleted = FALSE ";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allOrders.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get orders from DataBase", e);
        }
        for (Order order : allOrders) {
            order.setProducts(getProductListByOrderId(order.getId()));
        }
        return allOrders;
    }

    private void deleteProducts(Long orderId) {
        String query = "DELETE FROM orders_products WHERE order_id = ? ";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete products by specified orderID: "
                    + orderId, e);
        }
    }

    private List<Product> getProductListByOrderId(Long orderId) {
        List<Product> orderProducts = new ArrayList<>();
        String query = "SELECT * FROM products "
                + "JOIN orders_products USING (product_id) "
                + "WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("product_id");
                String productName = resultSet.getString("product_name");
                double price = resultSet.getDouble("price");
                Product product = new Product(productName, price);
                product.setId(id);
                orderProducts.add(product);
            }
            return orderProducts;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Couldn't get products from DB, orderId: " + orderId, e);
        }
    }

    private void addProducts(List<Product> products, Long orderId) {
        String query = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : products) {
                statement.setLong(1, orderId);
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't add products by specified orderID: "
                    + orderId, e);
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        Order order = new Order(userId);
        order.setId(orderId);
        return order;
    }
}
