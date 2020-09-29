package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
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
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String query = "SELECT * FROM shopping_carts WHERE user_id = ? AND deleted = FALSE";
        Optional<ShoppingCart> searchedCart = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                searchedCart = Optional.of(getCartFromResultSet(resultSet));
            }
            if (searchedCart.isEmpty()) {
                return searchedCart;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't find cart by userId: "
                    + userId, e);
        }
        searchedCart.get().setProducts(getProductsListByCartId(searchedCart.get().getId()));
        return searchedCart;
    }

    @Override
    public ShoppingCart create(ShoppingCart cartId) {
        String query = "INSERT INTO shopping_carts (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, cartId.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                cartId.setId(resultSet.getLong(1));
            }
            return cartId;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create provided cartId: " + cartId, e);
        }
    }

    @Override
    public Optional<ShoppingCart> getById(Long cartId) {
        String query = "SELECT * FROM shopping_carts WHERE cart_id = ? AND deleted = FALSE";
        List<Product> productsInCart = getProductsListByCartId(cartId);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart searchedCart = getCartFromResultSet(resultSet);
                searchedCart.setProducts(productsInCart);
                return Optional.of(searchedCart);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't find cart by  cartId: "
                    + cartId, e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        deleteProducts(cart.getId());
        addProducts(cart.getProducts(), cart.getId());
        return cart;
    }

    @Override
    public boolean deleteById(Long cartId) {
        String query = "UPDATE shopping_carts SET deleted = TRUE WHERE cart_id = ? "
                + "AND deleted = FALSE";
        deleteProducts(cartId);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete cart by cartId: "
                    + cartId, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        List<ShoppingCart> allCarts = new ArrayList<>();
        String query = "SELECT * FROM shopping_carts WHERE deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allCarts.add(getCartFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get carts from DB", e);
        }
        for (ShoppingCart cart : allCarts) {
            cart.setProducts(getProductsListByCartId(cart.getId()));
        }
        return allCarts;
    }

    private List<Product> getProductsListByCartId(Long cartId) {
        List<Product> cartProducts = new ArrayList<>();
        String query = "SELECT product_id, product_name, price FROM products "
                + "JOIN shopping_carts_products USING (product_id) "
                + "WHERE cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("product_id");
                String productName = resultSet.getString("product_name");
                double price = resultSet.getDouble("price");
                Product productFromCart = new Product(productName, price);
                productFromCart.setId(id);
                cartProducts.add(productFromCart);
            }
            return cartProducts;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Couldn't get products from DB, cartId: " + cartId, e);
        }
    }

    private void deleteProducts(Long cartId) {
        String query = "DELETE FROM shopping_carts_products WHERE cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete products by cartId: "
                    + cartId, e);
        }
    }

    private void addProducts(List<Product> products, Long cartId) {
        String query = "INSERT INTO shopping_carts_products (cart_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : products) {
                statement.setLong(1, cartId);
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't add products by cartId: "
                    + cartId, e);
        }
    }

    private ShoppingCart getCartFromResultSet(ResultSet resultSet) throws SQLException {
        long cartId = resultSet.getLong("cart_id");
        long userId = resultSet.getLong("user_id");
        ShoppingCart cart = new ShoppingCart(userId);
        cart.setId(cartId);
        return cart;
    }
}
