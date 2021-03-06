package com.demo.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.demo.dao.ProductDAO;
import com.demo.model.Product;
import com.demo.dao.CategoryDAO;
import com.demo.model.Category;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 50, // 50MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private String errors = null;
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        if(this.getServletContext().getAttribute("listCategory")==null)
        	this.getServletContext().setAttribute("listCategory", categoryDAO.selectAllCategory());
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
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
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
                    deleteProduct(request, response);
                    break;
                default:
                    listProduct(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        String text=request.getParameter("search");
    	if(text!=null) {
    			if(text.trim()!="") {
    				
    				List<Product> listProduct = productDAO.selectSearchProducts((page-1)*recordsPerPage,
                            recordsPerPage,text);
    				request.setAttribute("search", text);
    				request.setAttribute("listProduct", listProduct);
    			} else {
    				List<Product> listProduct = productDAO.selectAllProducts((page-1)*recordsPerPage,
                            recordsPerPage);
    				request.setAttribute("listProduct", listProduct);
    			}
    	}else {
    		List<Product> listProduct = productDAO.selectAllProducts((page-1)*recordsPerPage,
                    recordsPerPage);
    		request.setAttribute("listProduct", listProduct);
    	}
    	int noOfRecords = productDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/product/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Category> listCategory = categoryDAO.selectAllCategory();
        request.setAttribute("listCategory", listCategory);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/product/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productDAO.selectProduct(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/product/edit.jsp");
        request.setAttribute("product", existingProduct);
        dispatcher.forward(request, response);

    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		/*
		 * String name = request.getParameter("name"); String email =
		 * request.getParameter("email"); String category =
		 * request.getParameter("category"); Product newProduct = new Product(name, email,
		 * category); productDAO.insertProduct(newProduct); RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("admin/product/create.jsp"); dispatcher.forward(request,
		 * response);
		 */
    	Product product = new Product();
    	boolean flag = true;
    	Map<String, String> hashMap = new HashMap<String, String>();
    	response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
			Part part = request.getPart("image");
			String realPath = request.getServletContext().getRealPath("/admin/images");
			String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			
			if(!Files.exists(Paths.get(realPath))) {
				Files.createDirectory(Paths.get(realPath));
				
			}
			product.setImage("admin/images/"+filename);
			part.write(realPath+"\\"+filename);
    	System.out.println(this.getClass()+" Validation");
    	try {
    		product.setName(request.getParameter("name"));
    		product.setDescription(request.getParameter("desc"));
    		product.setPrice(Integer.parseInt(request.getParameter("price")));
    		int idCategory=Integer.parseInt(request.getParameter("category"));
    		product.setCategory(categoryDAO.selectCategory(idCategory).getName());
    		System.out.println(this.getClass()+" Product info: "+product.getName() );
    		ValidatorFactory validatorFactory=Validation.buildDefaultValidatorFactory();
    		Validator validator= validatorFactory.getValidator();
    		Set<ConstraintViolation<Product>> constraintViolations =validator.validate(product);
    		
    		if(!constraintViolations.isEmpty()) {
    			errors= "<ul>";
    			for(ConstraintViolation<Product> constraintViolation: constraintViolations) {
    				errors+="<li>"+constraintViolation.getPropertyPath()+" "+constraintViolation.getMessage()+"</li>";
    			}
    			errors+="</ul>";
    			request.setAttribute("product", product);
    			request.setAttribute("errors", errors);
    			request.getRequestDispatcher("admin/product/create.jsp").forward(request, response);
    		} else {
    			if(categoryDAO.selectCategory(idCategory)==null) {
    				flag=false;
    				hashMap.put("category", "Category invalid");
    				System.out.println(this.getClass()+" Category invalid");
    			
    			}
    			if(flag) {
    				productDAO.insertProduct(product);
    				Product u = new Product();
    				request.setAttribute("product", u);
    				request.getRequestDispatcher("admin/product/create.jsp").forward(request, response);
    			} else {
    				errors = "<ul>";
    				hashMap.forEach(new BiConsumer<String, String>(){
    					@Override
    					public void accept(String keyError,String valueError) {
    						errors+="<li>"+ valueError+"</li>";
    					}
    				});
    				errors+="</ul>";
    				request.setAttribute("product", product);
    				request.setAttribute("errors", errors);
    				System.out.println(this.getClass()+" !constraintViolation.isEmpty()");
    				request.getRequestDispatcher("admin/product/create.jsp").forward(request, response);
    			}
    		}
    	} catch(NumberFormatException e){
    		System.out.println(this.getClass()+" NumberFormatException: Product info: "+ product.getName());
    		errors ="<ul>";
    		errors+="<li>"+"Input format wrong"+"</li>";
    		errors+="</ul>";
    		request.setAttribute("product", product);
    		request.setAttribute("errors", errors);
    		request.getRequestDispatcher("admin/product/create.jsp").forward(request, response);;
    	}
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		/*
		 * int id = Integer.parseInt(request.getParameter("id")); String name =
		 * request.getParameter("name"); String email = request.getParameter("email");
		 * String category = request.getParameter("category"); String password =
		 * request.getParameter("password"); Product book = new Product(id, name, email,
		 * category, password); productDAO.updateProduct(book); Product existingProduct =
		 * productDAO.selectProduct(id); List<Category> listCategory =
		 * categoryDAO.selectAllCategory(); request.setAttribute("listCategory",
		 * listCategory); RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("admin/product/edit.jsp"); request.setAttribute("product",
		 * existingProduct); dispatcher.forward(request, response);
		 */
    	
    	Product product = new Product();
    	boolean flag = true;
    	Map<String, String> hashMap = new HashMap<String, String>();
    	response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
			Part part = request.getPart("image");
			String realPath = request.getServletContext().getRealPath("/admin/images");
			String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			
			System.out.println(realPath + "\\"+filename);
			
			if(!Files.exists(Paths.get(realPath))) {
				Files.createDirectory(Paths.get(realPath));
				
			}
			product.setImage("admin/images/"+filename);
			part.write(realPath+"\\"+filename);
    	System.out.println(this.getClass()+" Validation");
    	try {
    		int id = Integer.parseInt(request.getParameter("id"));
    		product.setId(id);
    		product.setName(request.getParameter("name"));
    		product.setDescription(request.getParameter("desc"));
    		product.setPrice(Integer.parseInt(request.getParameter("price")));
    		int idCategory=Integer.parseInt(request.getParameter("category"));
    		product.setCategory(categoryDAO.selectCategory(idCategory).getName());

        	System.out.println(categoryDAO.selectCategory(idCategory).getName());
    		
    		System.out.println(this.getClass()+" Product info: "+product.getName() );
    		ValidatorFactory validatorFactory=Validation.buildDefaultValidatorFactory();
    		Validator validator= validatorFactory.getValidator();
    		Set<ConstraintViolation<Product>> constraintViolations =validator.validate(product);
    		
    		if(!constraintViolations.isEmpty()) {
    			errors= "<ul>";
    			for(ConstraintViolation<Product> constraintViolation: constraintViolations) {
    				errors+="<li>"+constraintViolation.getPropertyPath()+" "+constraintViolation.getMessage()+"</li>";
    			}
    			errors+="</ul>";
    			request.setAttribute("product", product);
    			request.setAttribute("errors", errors);
    			request.getRequestDispatcher("admin/product/edit.jsp").forward(request, response);
    		} else {
    			if(categoryDAO.selectCategory(idCategory)==null) {
    				flag=false;
    				hashMap.put("category", "Category invalid");
    				System.out.println(this.getClass()+" Category invalid");
    			
    			}
    			if(flag) {
    				productDAO.updateProduct(product);
    				Product u = new Product();
    				u=productDAO.selectProduct(id);
    				request.setAttribute("product", u);
    				request.getRequestDispatcher("admin/product/edit.jsp").forward(request, response);
    			} else {
    				errors = "<ul>";
    				hashMap.forEach(new BiConsumer<String, String>(){
    					@Override
    					public void accept(String keyError,String valueError) {
    						errors+="<li>"+ valueError+"</li>";
    					}
    				});
    				errors+="</ul>";
    				request.setAttribute("product", product);
    				request.setAttribute("errors", errors);
    				System.out.println(this.getClass()+" !constraintViolation.isEmpty()");
    				request.getRequestDispatcher("admin/product/edit.jsp").forward(request, response);
    			}
    		}
    	} catch(NumberFormatException e){
    		System.out.println(this.getClass()+" NumberFormatException: Product info: "+ product.getName());
    		errors ="<ul>";
    		errors+="<li>"+"Input format wrong"+"</li>";
    		errors+="</ul>";
    		request.setAttribute("product", product);
    		request.setAttribute("errors", errors);
    		request.getRequestDispatcher("admin/product/edit.jsp").forward(request, response);;
    	}
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);

        List<Product> listProduct = productDAO.selectAllProducts();
        request.setAttribute("listProduct", listProduct);
        response.sendRedirect("/products");
        //RequestDispatcher dispatcher = request.getRequestDispatcher("admin/product/list.jsp");
        //dispatcher.forward(request, response);
    }
      
}