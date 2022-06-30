package com.demo.dao;

import java.sql.SQLException;
import java.util.List;

import com.demo.model.Product;

public interface IProductDAO {
    public void insertProduct(Product product) throws SQLException;

    public Product selectProduct(int id);

    public List<Product> selectAllProducts();

    public boolean deleteProduct(int id) throws SQLException;

    public boolean updateProduct(Product product) throws SQLException;
}