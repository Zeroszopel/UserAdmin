<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2>
        <a href="users?action=users">List All Users</a>
    </h2>
</center>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    Edit User
                </h2>
            </caption>
            <c:if test="${user != null}">
                <input type="hidden" name="id" value="<c:out value='${user.getId()}' />"/>
            </c:if>
            <tr>
                <th>User Name:</th>
                <td>
                    <input type="text" name="name" size="45" value="${user.getName()}"/>
                </td>
            </tr>
            <tr>
                <th>User Email:</th>
                <td>
                    <input type="text" name="email" size="45" value="${user.getEmail()}"  />
                </td>
            </tr>
            <tr>
            	<th>User Password:</th>
            	<td>
            		<input type="password" name="password" value="${user.getPassword()}" id="password" size="45">
            	</td>
            </tr>
            <tr>
                <th>Country:</th>
                <td>
                    <select name="country" style="width:150px">
                    <c:forEach var="country" items="${applicationScope.listCountry}">
                    	<option value="<c:out value='${country.getId()}'/>" 
                    	<c:if test="${country.getName() eq user.getCountry()}">
                    			selected
                    	</c:if>
                    	>
                    	<c:out value='${country.getName()}'/></option>
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
</div>
</body>
</html>