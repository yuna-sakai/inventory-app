<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫一覧ページ</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
	<h1 class="center">在庫一覧ページ</h1>
	<form action="inventoryList" method="get">
    <label>並び順:</label>
    <select name="sortKey">
        <option value="item_name" ${param.sortKey == 'item_name' ? 'selected' : ''}>商品名</option>
        <option value="expiry_date" ${param.sortKey == 'expiry_date' ? 'selected' : ''}>賞味期限</option>
    </select>
    <button type="submit">並び替え</button>
</form>

	

	<form action="searchResult" method="get">
		<div>
			<input type="text" name="searchQuery" placeholder="検索" />
			<button type="submit" class="btn">検索</button>
		</div>
	</form>

	<c:if test="${not empty errorMessage}">
		<p style="color: red;">${errorMessage}</p>
	</c:if>

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
				<c:forEach var="inventory" items="${inventoryList}">
					<tr>
						<td><a href="inventoryDetail?id=${inventory.itemId}">${inventory.itemName}</a></td>
						<td>${inventory.quantity}</td>
						<td style="${inventory.expiringSoon ? 'color: red;' : ''}">
							${inventory.expiryDate != null ? inventory.expiryDate.toString() : ''}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<c:if test="${empty inventoryList}">
		<p>在庫が見つかりませんでした。</p>
	</c:if>

	<div>
		<a href="registerInventory" class="btn">登録</a> <a href="alertList"
			class="btn">アラート一覧</a>

		<form action="logout" method="post" style="display: inline;">
			<button type="submit" class="btn">ログアウト</button>
		</form>
	</div>
</body>
</html>
