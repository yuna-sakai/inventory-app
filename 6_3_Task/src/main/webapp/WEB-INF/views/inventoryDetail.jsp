<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>食材詳細</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
<h1 class="center">食材詳細ページ</h1>

<table>
    <tr>
        <th>食材名</th>
        <td>${inventory.itemName}</td>
    </tr>
    <tr>
        <th>個数</th>
        <td>${inventory.quantity}</td>
    </tr>
    <tr>
        <th>賞味期限</th>
        <td>${inventory.formattedExpiryDate}</td>
    </tr>
</table>

<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>

<div>
    <form action="increaseQuantity" method="post" style="display: inline;">
        <input type="hidden" name="id" value="${inventory.itemId}" />
        <label>増やす量:
            <input type="number" name="quantity" min="1" value="1" max="${9999 - inventory.quantity}" />
        </label>
        <button type="submit" class="btn">増やす</button>
    </form>

    <form action="decreaseQuantity" method="post" style="display: inline;">
        <input type="hidden" name="id" value="${inventory.itemId}" />
        <label>減らす量:
            <input type="number" name="quantity" min="1" value="1" max="${inventory.quantity}" />
        </label>
        <button type="submit" class="btn">減らす</button>
    </form>

    <form action="deleteInventory" method="post" style="display: inline;">
        <input type="hidden" name="id" value="${inventory.itemId}" />
        <button type="submit" class="btn">削除</button>
    </form>
</div>

<div>
    <a href="inventoryList" class="btn">戻る</a>
</div>
</body>
</html>
