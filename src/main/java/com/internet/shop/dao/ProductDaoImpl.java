package com.internet.shop.dao;

import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> getById(Long productId) {
        return Storage.products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(i -> Storage.products.get(i)
                        .getId().equals(product.getId()))
                .findFirst()
                .ifPresent(i -> Storage.products.set(i, product));
        return product;
    }

    @Override
    public boolean deleteById(Long productId) {
        return Storage.products.remove(getById(productId).orElseThrow());
    }

    @Override
    public List<Product> getAllProducts() {
        return Storage.products;
    }
}
