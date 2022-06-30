<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Product Management Application</title>
</head>
<body>
<center>
    <h1>Product Management</h1>
    <h2>
        <a href="/products?action=products">List All Products</a>
    </h2>
</center>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    Edit Product
                </h2>
            </caption>
            <c:if test="${product != null}">
                <input type="hidden" name="id" value="<c:out value='${product.getId()}' />"/>
            </c:if>
            <tr>
                <th>Product Name:</th>
                <td>
                    <input type="text" name="name" size="50" value="${product.getName()}"/>
                </td>
            </tr>
            <tr>
                <th>Product Description:</th>
                <td>
                    <input type="text" name="desc" size="300" value="${product.getDescription()}"  />
                </td>
            </tr>
            <tr>
            	<th>Product Price:</th>
            	<td>
            		<input type="text" name="price" value="${product.getPrice()}" id="price" size="45">
            	</td>
            </tr>
            <tr>
                <th>Category:</th>
                <td>
                    <select name="category" style="width:150px">
                    <c:forEach var="category" items="${applicationScope.listCategory}">
                    	<option value="<c:out value='${category.getId()}'/>" 
                    	<c:if test="${category.getName() eq product.getCategory()}">
                    			selected
                    	</c:if>
                    	>
                    	<c:out value='${category.getName()}'/></option>
                    </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save"/>
                </td>
            </tr>
        </table>
    </form>
    <div>
    	${errors}
    </div>
</div>
</body>
</html>