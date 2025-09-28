<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー管理画面</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
	<h1 class="center">ユーザー管理画面</h1>

	<h2>ユーザー一覧</h2>
	<c:if test="${not empty userList}">
		<table>
			<thead>
				<tr>
					<th>ログインID</th>
					<th>名前</th>
					<th>権限</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${userList}">
					<tr class="${user.roleName == '管理者' ? 'admin-row' : 'user-row'}">
						<td>${user.loginId}</td>
						<td>${user.userName}</td>
						<td>${user.roleName}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${empty userList}">
		<p>ユーザーが見つかりませんでした。</p>
	</c:if>

	<div>
		<a href="deleteUser" class="btn">退会</a> <a href="registerUser"
			class="btn">登録</a> <a href="updateUser" class="btn">変更</a>
		<form action="logout" method="post" style="display: inline;">
			<button type="submit" class="btn">ログアウト</button>
		</form>
	</div>
</body>
</html>
