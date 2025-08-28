<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<link href="css/commons.css" rel="stylesheet">
<link href="css/login.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<h1 class="center">冷蔵庫管理アプリ</h1>
		<div class="error">
			<c:if test="${not empty errorMessage}">
                ${errorMessage}
            </c:if>
		</div>
		<form:form action="login" method="post" modelAttribute="loginForm"
			class="login-form">
			<h2 class="center">ログイン画面</h2>
			<fieldset>
				<div class="form-group">
					<label>ログインID</label>
					<form:input path="loginId" class="input" />
					<form:errors path="loginId" cssStyle="color: red; display: inline;" />
				</div>
				<div class="form-group">
					<label>パスワード</label>
					<form:input path="password" type="password" class="input" />
					<form:errors path="password"
						cssStyle="color: red; display: inline;" />
				</div>
			</fieldset>
			<button type="submit" class="btn">ログイン</button>
		</form:form>
	</div>
</body>
</html>
