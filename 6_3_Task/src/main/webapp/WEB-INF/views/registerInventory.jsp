<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫登録ページ</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
    <h1 class="center">在庫登録ページ</h1>
    <p>
        登録情報を入力してください<br> <span class="required"></span>は必須です
    </p>

    <form:form action="registerInventory" method="post" modelAttribute="inventoryForm">
        <fieldset>
            <div>
                <label class="required">食材名</label>
                <form:input path="itemName" />
                <br />
                <form:errors path="itemName" cssClass="error" />
            </div>

            <div>
                <label class="required">個数</label>
                <form:input path="quantity" type="number" min="1" max="9999" />
                <br />
                <form:errors path="quantity" cssClass="error" />
            </div>

            <div>
                <label class="required">賞味期限</label>
                <form:input path="expiryDate" type="date" />
                <br />
                <form:errors path="expiryDate" cssClass="error" />
            </div>
        </fieldset>

        <div>
            <button type="submit" class="btn">登録</button>
            <a href="inventoryList" class="btn">戻る</a>
        </div>
    </form:form>
</body>
</html>
