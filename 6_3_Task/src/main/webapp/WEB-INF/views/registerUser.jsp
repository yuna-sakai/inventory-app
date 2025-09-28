<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録ページ</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
	<h1 class="center">ユーザー登録ページ</h1>
	<p>
		登録情報を入力してください<br> <span class="required"></span>は必須です
	</p>
	<c:if test="${not empty loginIdError}">
		<span class="error">${loginIdError}</span>
	</c:if>

	<form:form action="registerUser" method="post"
		modelAttribute="registerForm">
		<fieldset class="label-60">
			<div>
				<label class="required">ログインID</label>
				<form:input path="loginId" />
				<form:errors path="loginId" cssClass="error" />
			</div>
			<div>
				<label class="required">名前</label>
				<form:input path="userName" />
				<form:errors path="userName" cssClass="error" />
			</div>
			<div>
				<label class="required">権限</label>
				<form:select path="roleId">
					<c:forEach var="role" items="${roles}">
						<form:option value="${role.roleId}">${role.roleName}</form:option>
					</c:forEach>
				</form:select>
				<form:errors path="roleId" cssClass="error" />
			</div>
			<div>
				<label class="required">パスワード</label>
				<form:password path="password" />
				<form:errors path="password" cssClass="error" />
			</div>
		</fieldset>
		<div>
			<button type="submit" class="btn">登録</button>
			<a href="userManagement" class="btn">戻る</a>
		</div>
	</form:form>
</body>
</html>
