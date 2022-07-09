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

import com.demo.dao.CountryDAO;
import com.demo.model.Country;
@WebServlet(name="CountryServlet", urlPatterns= {"/country"})
public class CountryServlet extends HttpServlet{
	   private static final long serialVersionUID = 1L;
	    private CountryDAO countryDAO;

	    public void init() {
	        countryDAO = new CountryDAO();
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
	                    insertCountry(request, response);
	                    break;
	                case "edit":
	                    updateCountry(request, response);
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
	                    deleteCountry(request, response);
	                    break;
	                default:
	                    listCountry(request, response);
	                    break;
	            }
	        } catch (SQLException ex) {
	            throw new ServletException(ex);
	        }
	    }

	    private void listCountry(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	    	int page = 1;
	        int recordsPerPage = 5;
	        if(request.getParameter("page") != null)
	            page = Integer.parseInt(request.getParameter("page"));
	        String text=request.getParameter("search");
	    	if(text!=null) {
	    			if(text.trim()!="") {
	    				
	    				List<Country> listCountry = countryDAO.selectSearchCountry((page-1)*recordsPerPage,
                                recordsPerPage,text);
	    				request.setAttribute("search", text);
	    				request.setAttribute("listCountry", listCountry);
	    			} else {
	    				List<Country> listCountry = countryDAO.selectAllCountry((page-1)*recordsPerPage,
                                recordsPerPage);
	    				request.setAttribute("listCountry", listCountry);
	    			}
	    	}else {
				List<Country> listCountry = countryDAO.selectAllCountry((page-1)*recordsPerPage,
                        recordsPerPage);
				request.setAttribute("listCountry", listCountry);
	    	}
	    	int noOfRecords = countryDAO.getNoOfRecords();
	        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
	        request.setAttribute("noOfPages", noOfPages);
	        request.setAttribute("currentPage", page);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/country/list.jsp");
	        dispatcher.forward(request, response);
	    }

	    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/country/create.jsp");
	        dispatcher.forward(request, response);
	    }

	    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, ServletException, IOException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        Country existingCountry = countryDAO.selectCountry(id);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/country/edit.jsp");
	        request.setAttribute("country", existingCountry);
	        dispatcher.forward(request, response);

	    }

	    private void insertCountry(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        String name = request.getParameter("name");
	        Country newCountry = new Country(name);
	        countryDAO.insertCountry(newCountry);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/country/create.jsp");
	        dispatcher.forward(request, response);
	    }

	    private void updateCountry(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        String name = request.getParameter("name");
	        System.out.println(name);
	        Country book = new Country(id, name);
	        countryDAO.updateCountry(book);
	        Country existingCountry = countryDAO.selectCountry(id);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/country/edit.jsp");
	        request.setAttribute("country", existingCountry);
	        dispatcher.forward(request, response);
	    }

	    private void deleteCountry(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        countryDAO.deleteCountry(id);

	        List<Country> listCountry = countryDAO.selectAllCountry();
	        request.setAttribute("listCountry", listCountry);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/country/list.jsp");
	        dispatcher.forward(request, response);
	    }
}
