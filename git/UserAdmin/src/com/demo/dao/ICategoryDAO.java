package com.demo.dao;

import java.sql.SQLException;
import java.util.List;

import com.demo.model.Category;

public interface ICategoryDAO {
	public void insertCategory(Category Category) throws SQLException;

    public Category selectCategory(int id);

    public List<Category> selectAllCategory();

    public boolean deleteCategory(int id) throws SQLException;

    public boolean updateCategory(Category Category) throws SQLException;
}
