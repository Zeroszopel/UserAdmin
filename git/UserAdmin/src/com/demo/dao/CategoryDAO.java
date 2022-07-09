package com.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.demo.model.Category;

public class CategoryDAO implements ICategoryDAO{
	 private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
	    private String jdbcCategoryname = "root";
	    private String jdbcPassword = "";
	    private int noOfRecords;

	    private static final String INSERT_CATEGORY_SQL = "INSERT INTO Category" + "  (name) VALUES " +
	            " (?);";

	    private static final String SELECT_CATEGORY_BY_ID = "select id,name from category where id =?";
	    private static final String SELECT_ALL_CATEGORY = "select * from category";
	    private static final String SELECT_ALL_CATEGORY_PAGE = "select SQL_CALC_FOUND_ROWS * from category limit ?,?";
	    private static final String SELECT_SEARCH_CATEGORY = "SELECT SQL_CALC_FOUND_ROWS * FROM category where id like N? or name like N? limit ?,?";
	    private static final String DELETE_CATEGORY_SQL = "delete from category where id = ?;";
	    private static final String UPDATE_CATEGORY_SQL = "update Category set name = ? where id = ?;";

	    public CategoryDAO() {
	    }

	    protected Connection getConnection() {
	        Connection connection = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection(jdbcURL, jdbcCategoryname, jdbcPassword);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return connection;
	    }
	    
		@Override
		public void insertCategory(Category category) throws SQLException {
			System.out.println(INSERT_CATEGORY_SQL);
	        // try-with-resource statement will auto close the connection.
	        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY_SQL)) {
	            preparedStatement.setString(1, category.getName());
	            System.out.println(preparedStatement);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
		}

		@Override
		public Category selectCategory(int id) {
			Category category = null;
	        // Step 1: Establishing a Connection
	        try (Connection connection = getConnection();
	             // Step 2:Create a statement using connection object
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID);) {
	            preparedStatement.setInt(1, id);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                String name = rs.getString("name");
	                category = new Category(id, name);
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return category;
		}

		@Override
		public List<Category> selectAllCategory() {

	        // using try-with-resources to avoid closing resources (boiler plate code)
	        List<Category> Category = new ArrayList<>();
	        // Step 1: Establishing a Connection
	        try {Connection connection = getConnection();

	             // Step 2:Create a statement using connection object
	             PreparedStatement preparedStatement = 
	            		 connection.prepareStatement(SELECT_ALL_CATEGORY);
	            
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                Category.add(new Category(id, name));
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return Category;
		}
		public List<Category> selectAllCategory(int offset,int noOfRecords) {

	        // using try-with-resources to avoid closing resources (boiler plate code)
	        List<Category> Category = new ArrayList<>();
	        // Step 1: Establishing a Connection
	        try {Connection connection = getConnection();

	             // Step 2:Create a statement using connection object
	             PreparedStatement statement = 
	            		 connection.prepareStatement(SELECT_ALL_CATEGORY_PAGE);
	            statement.setInt(1, offset);
	            statement.setInt(2, noOfRecords);
	            // Step 3: Execute the query or update query
	            ResultSet rs = statement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                Category.add(new Category(id, name));
	            }
	            statement=connection.prepareStatement("SELECT FOUND_ROWS()");
	            rs = statement.executeQuery();
	            if(rs.next())
	                this.noOfRecords = rs.getInt(1);
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return Category;
		}
		public List<Category> selectSearchCategory(int offset,int noOfRecords,String search) {

	        // using try-with-resources to avoid closing resources (boiler plate code)
	        List<Category> Category = new ArrayList<>();
	        // Step 1: Establishing a Connection
	        try {Connection connection = getConnection();

	             // Step 2:Create a statement using connection object
	             PreparedStatement statement = 
	            		 connection.prepareStatement(SELECT_SEARCH_CATEGORY);
	             statement.setString(1, search);
	             statement.setString(2, "%"+search+"%");
	            statement.setInt(3, offset);
	            statement.setInt(4, noOfRecords);
	            // Step 3: Execute the query or update query
	            ResultSet rs = statement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                Category.add(new Category(id, name));
	            }
	            statement=connection.prepareStatement("SELECT FOUND_ROWS()");
	            rs = statement.executeQuery();
	            if(rs.next())
	                this.noOfRecords = rs.getInt(1);
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return Category;
		}
		@Override
		public boolean deleteCategory(int id) throws SQLException {
			boolean rowDeleted;
	        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORY_SQL);) {
	            statement.setInt(1, id);
	            rowDeleted = statement.executeUpdate() > 0;
	        }
	        return rowDeleted;
		}

		@Override
		public boolean updateCategory(Category category) throws SQLException {
			boolean rowUpdated;
	        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_CATEGORY_SQL);) {
	            statement.setString(1, category.getName());
	            statement.setInt(2, category.getId());

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
