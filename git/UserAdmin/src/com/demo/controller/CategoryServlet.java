package com.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.dao.CategoryDAO;
import com.demo.model.Category;
@WebServlet(name="CategoryServlet", urlPatterns= {"/category"})
public class CategoryServlet extends HttpServlet{
	   private static final long serialVersionUID = 1L;
	    private CategoryDAO CategoryDAO;

	    public void init() {
	        CategoryDAO = new CategoryDAO();
	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String action = request.getParameter("action");
	        if (action == null) {
	            action = "";
	        }
	        try {
	            switch (action) {
	                case "create":
	                    insertCategory(request, response);
	                    break;
	                case "edit":
	                    updateCategory(request, response);
	                    break;
	            }
	        } catch (SQLException ex) {
	            throw new ServletException(ex);
	        }
	    }

	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String action = request.getParameter("action");
	        if (action == null) {
	            action = "";
	        }

	        try {
	            switch (action) {
	                case "create":
	                    showNewForm(request, response);
	                    break;
	                case "edit":
	                    showEditForm(request, response);
	                    break;
	                case "delete":
	                    deleteCategory(request, response);
	                    break;
	                default:
	                    listCategory(request, response);
	                    break;
	            }
	        } catch (SQLException ex) {
	            throw new ServletException(ex);
	        }
	    }

	    private void listCategory(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	    	
	        List<Category> listCategory = CategoryDAO.selectAllCategory();
	        request.setAttribute("listCategory", listCategory);
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("category/list.jsp");
	        dispatcher.forward(request, response);
	    }

	    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        RequestDispatcher dispatcher = request.getRequestDispatcher("category/create.jsp");
	        dispatcher.forward(request, response);
	    }

	    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, ServletException, IOException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        Category existingCategory = CategoryDAO.selectCategory(id);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("category/edit.jsp");
	        request.setAttribute("category", existingCategory);
	        dispatcher.forward(request, response);

	    }

	    private void insertCategory(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        String name = request.getParameter("name");
	        Category newCategory = new Category(name);
	        CategoryDAO.insertCategory(newCategory);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("category/create.jsp");
	        dispatcher.forward(request, response);
	    }

	    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        String name = request.getParameter("name");
	        System.out.println(name);
	        Category book = new Category(id, name);
	        CategoryDAO.updateCategory(book);
	        Category existingCategory = CategoryDAO.selectCategory(id);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("category/edit.jsp");
	        request.setAttribute("category", existingCategory);
	        dispatcher.forward(request, response);
	    }

	    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        CategoryDAO.deleteCategory(id);

	        List<Category> listCategory = CategoryDAO.selectAllCategory();
	        request.setAttribute("listCategory", listCategory);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("category/list.jsp");
	        dispatcher.forward(request, response);
	    }
}
