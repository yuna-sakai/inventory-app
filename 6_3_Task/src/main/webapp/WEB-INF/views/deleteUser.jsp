<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー退会</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
	<h1 class="center">ユーザー退会</h1>

	<!-- 操作結果メッセージの表示 -->
	<c:if test="${not empty message}">
		<p class="center" style="color: green;">${message}</p>
	</c:if>

	<form:form action="/deleteUser" method="post"
		modelAttribute="manageUserForm">
		<fieldset>
			<div>
				<label>ログインID</label>
				<form:input path="loginId" />
				<form:errors path="loginId" cssStyle="color: red; display: inline;" />
			</div>
			<div>
				<label>パスワード</label>
				<form:input path="password" type="password" />
				<form:errors path="password" cssStyle="color: red; display: inline;" />
			</div>
		</fieldset>
	<div>
		<button type="submit" class="btn">退会</button>
		<a href="userManagement" class="btn">戻る</a>
	</div>
	</form:form>
</body>
</html>
