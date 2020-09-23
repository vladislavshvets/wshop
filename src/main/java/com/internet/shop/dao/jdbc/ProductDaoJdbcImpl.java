package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
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
public class ProductDaoJdbcImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products(name, price) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query,
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong("id"));
            }
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Incorrect data of product[" + product + "] failed.", e);
        }
    }

    @Override
    public Optional<Product> getById(Long id) {
        Product product = null;
        String query = "SELECT name, price FROM products WHERE product_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                product = getProductFromResSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Getting product by id " + id + " failed", e);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? WHERE product_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    query);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Failed update info. Incorrect data of product[" + product + "] failed.", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE products SET deleted = TRUE WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Deleted of product with id=" + id + " is failed", e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = getProductFromResSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed, list products not found", e);
        }
        return products;
    }

    private Product getProductFromResSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        return new Product(name, price);
    }
}
