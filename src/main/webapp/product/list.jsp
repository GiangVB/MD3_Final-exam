<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 12-Apr-22
  Time: 9:24 AM
  To change this template use File | Settings | File Templates.
--%>
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
        <a href="/product?action=create">Add New Product</a>
    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Product</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Color</th>
            <th>Category</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="product" items="${listProduct}">
            <tr>
                <td><c:out value="${product.getId()}"/></td>
                <td><c:out value="${product.getName()}"/></td>
                <td><c:out value="${product.getPrice()}"/></td>
                <td><c:out value="${product.getQuantity()}"/></td>
                <td><c:out value="${product.getColor()}"/></td>
                <td><c:out value="${product.category.getName()}"/></td>
                <td>
                    <a href="/product?action=edit&id=${product.id}">Edit</a>
                    <a href="/product?action=delete&id=${product.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
