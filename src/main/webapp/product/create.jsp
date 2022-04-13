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
        <a href="/product?action=product">List All Product</a>
    </h2>
</center>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>Add New Product</h2>
            </caption>
            <tr>
                <th>Name:</th>
                <td>
                    <input type="text" name="name" id="name" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Price:</th>
                <td>
                    <input type="number" name="price" id="price" size="45"/>
                </td>
            </tr><tr>
            <th>Quantity:</th>
            <td>
                <input type="number" name="quantity" id="quantity" size="45"/>
            </td>
        </tr><tr>
            <th>Color:</th>
            <td>
                <input type="text" name="color" id="color" size="45"/>
            </td>
        </tr><tr>
            <th>Category:</th>
            <td>
                <select name="category">
                    <c:forEach items="${listCategory}" var="category" >
                        <option value="${category.id}">${category.name}</option>
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
