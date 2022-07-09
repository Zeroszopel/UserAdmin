package com.demo.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Admin", urlPatterns = "/dash")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
    	            throws ServletException, IOException {
    		 RequestDispatcher dispatcher = request.getRequestDispatcher("admin/index.jsp");
    	        dispatcher.forward(request, response);
    	 }
}
