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
	    private CategoryDAO categoryDAO;

	    public void init() {
	        categoryDAO = new CategoryDAO();
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
	    	int page = 1;
	        int recordsPerPage = 5;
	        if(request.getParameter("page") != null)
	            page = Integer.parseInt(request.getParameter("page"));
	       
	        String text=request.getParameter("search");
	    	if(text!=null) {
	    			if(text.trim()!="") {
	    				
	    				List<Category> listCategory = categoryDAO.selectSearchCategory((page-1)*recordsPerPage,
                                recordsPerPage,text);
	    				request.setAttribute("search", text);
	    				request.setAttribute("listCategory", listCategory);
	    			} else {
	    				List<Category> listCategory = categoryDAO.selectAllCategory((page-1)*recordsPerPage,
                                recordsPerPage);
	    				request.setAttribute("listCategory", listCategory);
	    			}
	    	}else {
	    		List<Category> listCategory = categoryDAO.selectAllCategory((page-1)*recordsPerPage,
                        recordsPerPage);
	    		request.setAttribute("listCategory", listCategory);
	    	}
	    	int noOfRecords = categoryDAO.getNoOfRecords();
		    int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		    request.setAttribute("noOfPages", noOfPages);
		    request.setAttribute("currentPage", page);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/category/list.jsp");
	        dispatcher.forward(request, response);
	    }

	    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/category/create.jsp");
	        dispatcher.forward(request, response);
	    }

	    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, ServletException, IOException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        Category existingCategory = categoryDAO.selectCategory(id);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/category/edit.jsp");
	        request.setAttribute("category", existingCategory);
	        dispatcher.forward(request, response);

	    }

	    private void insertCategory(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        String name = request.getParameter("name");
	        Category newCategory = new Category(name);
	        categoryDAO.insertCategory(newCategory);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/category/create.jsp");
	        dispatcher.forward(request, response);
	    }

	    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        String name = request.getParameter("name");
	        System.out.println(name);
	        Category book = new Category(id, name);
	        categoryDAO.updateCategory(book);
	        Category existingCategory = categoryDAO.selectCategory(id);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/category/edit.jsp");
	        request.setAttribute("category", existingCategory);
	        dispatcher.forward(request, response);
	    }

	    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        categoryDAO.deleteCategory(id);

	        List<Category> listCategory = categoryDAO.selectAllCategory();
	        request.setAttribute("listCategory", listCategory);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/category/list.jsp");
	        dispatcher.forward(request, response);
	    }
}
