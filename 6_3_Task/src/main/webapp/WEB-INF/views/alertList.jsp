<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>賞味期限アラートページ</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
	<h1 class="center">賞味期限アラート</h1>
	<p class="center">賞味期限が3日以内のものを表示します</p>
	<c:if test="${not empty alertList}">
		<table>
			<thead>
				<tr>
					<th>食材</th>
					<th>個数</th>
					<th>賞味期限</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="alert" items="${alertList}">
					<tr>
						<td>${alert.itemName}</td>
						<td>${alert.quantity}</td>
						<td>${alert.expiryDate}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${empty alertList}">
		<p>アラートはありません。</p>
	</c:if>
	<div>
		<a href="inventoryList" class="btn">戻る</a>
	</div>
</body>
</html>
