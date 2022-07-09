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
        <a href="products?action=products">List All Products</a>
    </h2>
</center>
<div align="center">
    <form method="post" enctype="multipart/form-data">
        <table border="1" cellpadding="5">
            <caption>
                <h2>Add New Product</h2>
            </caption>
            <input type="hidden" name="id" value="${product.getId()}"/>
            <tr>
                <th>Product Name:</th>
                <td>
                    <input type="text" name="name" value="${product.getName()}" id="name" size="50" required/>
                </td>
            </tr>
            <tr>
                <th>Product Description:</th>
                <td>
                    <input type="text" name="desc" value="${product.getDescription()}" id="desc" size="100" required/>
                </td>
            </tr>
            <tr>
            	<th>Product Price:</th>
            	<td>
            		<input type="text" name="price" value="${product.getPrice()}" id="price" size="50">
            	</td>
            </tr>
            <tr>
                <th>Category:</th>
                <td>
                    <select name="category" style="width:150px">
                    <c:forEach var="category" items="${applicationScope.listCategory}">
                    	<option value="<c:out value='${category.getId()}'/>">
                    	<c:out value='${category.getName()}'/></option>
                    </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
            	<th>Image:</th>
            	<td><input type="file" name="image" size="60" accept="image/*" onchange="showPreview(event);"/><br/>
            	<img id="file-preview" width="50" height="50"></td>
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

<script>
function showPreview(event){
	  if(event.target.files.length > 0){
	    var src = URL.createObjectURL(event.target.files[0]);
	    var preview = document.getElementById("file-preview");
	    preview.src = src;
	    preview.style.display = "block";
	  }
}
</script>
</body>
</html>