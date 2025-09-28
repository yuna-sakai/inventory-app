<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>検索結果</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
	<h1 class="center">検索結果ページ</h1>

	<c:if test="${not empty inventoryList}">
		<table>
			<thead>
				<tr>
					<th>食材名</th>
					<th>個数</th>
					<th>賞味期限</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${inventoryList}">
					<tr>
						<td><a href="inventoryDetail?id=${item.itemId}">${item.itemName}</a></td>
						<td>${item.quantity}</td>
						<td>${item.formattedExpiryDate}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<div>
		<a href="inventoryList" class="btn">戻る</a>
	</div>
</body>
</html>
