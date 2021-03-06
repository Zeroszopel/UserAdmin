package com.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.demo.model.Product;

public class ProductDAO implements IProductDAO{
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
    private String jdbcProductname = "root";
    private String jdbcPrice = "";
    private int noOfRecords;
    private static final String INSERT_PRODUCTS_SQL = "INSERT INTO products" + "  (name, description,price,category,image) VALUES " +
            " (?, ?, ?, ?,?);";

    private static final String SELECT_USER_BY_ID = "select * from products where id =?";
    private static final String SELECT_USER_BY_EMAIL = "select * from products where description =?";
    private static final String SELECT_ALL_PRODUCTS = "select * from products";
    private static final String SELECT_ALL_PRODUCTS_PAGE = "select SQL_CALC_FOUND_ROWS * from products limit ?,?";
    private static final String SELECT_SEARCH_PRODUCTS = "SELECT SQL_CALC_FOUND_ROWS * FROM products where id like N? or name like N? or description like N? or category like N? or price like N? limit ?,?";
    private static final String DELETE_PRODUCTS_SQL = "delete from products where id = ?;";
    private static final String UPDATE_PRODUCTS_SQL = "update products set name = ?,description= ?,price=?, category =?, image=? where id = ?;";

    public ProductDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcProductname, jdbcPrice);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public void insertProduct(Product user) throws SQLException {
        
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getDescription());
            preparedStatement.setInt(3, user.getPrice());
            preparedStatement.setString(4, user.getCategory());
            preparedStatement.setString(5, user.getImage());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Product selectProduct(int id) {
        Product user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String image=rs.getString("image");
                int price = rs.getInt("price");
                user = new Product(id, name, description, category,image, price);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }
    public Product selectProductByDesc(String _description) {
        Product user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);) {
            preparedStatement.setString(1, _description);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String image=rs.getString("image");
                int price = rs.getInt("price");
                user = new Product(id, name, description, category,image, price);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List<Product> selectAllProducts() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Product> products = new ArrayList<>();
        // Step 1: Establishing a Connection
        try {Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = 
            		 connection.prepareStatement(SELECT_ALL_PRODUCTS);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String image=rs.getString("image");
                int price = rs.getInt("price");
                products.add(new Product(id, name, description, category,image, price));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }
    public List<Product> selectAllProducts(int offset,int noOfRecords) {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Product> products = new ArrayList<>();
        // Step 1: Establishing a Connection
        try {Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement statement = 
            		 connection.prepareStatement(SELECT_ALL_PRODUCTS_PAGE);
             statement.setInt(1,offset);
             statement.setInt(2,noOfRecords);
            // Step 3: Execute the query or update query
            ResultSet rs = statement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String image=rs.getString("image");
                int price = rs.getInt("price");
                products.add(new Product(id, name, description, category, image, price));
            }
            statement=connection.prepareStatement("SELECT FOUND_ROWS()");
            rs = statement.executeQuery();
            if(rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }
    public List<Product> selectSearchProducts(int offset,int noOfRecords,String search) {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Product> products = new ArrayList<>();
        // Step 1: Establishing a Connection
        try {Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement statement = 
            		 connection.prepareStatement(SELECT_SEARCH_PRODUCTS);
             statement.setString(1,search);
             statement.setString(2,"%"+search+"%");
             statement.setString(3,"%"+search+"%");
             statement.setString(4,"%"+search+"%");
             statement.setString(5,search);
             statement.setInt(6,offset);
             statement.setInt(7,noOfRecords);
            // Step 3: Execute the query or update query
            ResultSet rs = statement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String image=rs.getString("image");
                int price = rs.getInt("price");
                products.add(new Product(id, name, description, category, image, price));
            }
            statement=connection.prepareStatement("SELECT FOUND_ROWS()");
            rs = statement.executeQuery();
            if(rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }
    @Override
    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCTS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    @Override
    public boolean updateProduct(Product user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCTS_SQL);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getDescription());
            statement.setInt(3, user.getPrice());
            statement.setString(4, user.getCategory());
            statement.setString(5, user.getImage());
            statement.setInt(6, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}