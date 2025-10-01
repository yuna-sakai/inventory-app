<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫一覧ページ</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
	<h1 class="center">在庫一覧ページ</h1>

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
          <th>
            <!-- 単独で 食材名↓ に並べ替えるリンク（任意） -->
            <a href="<c:url value='/inventoryList'>
                        <c:param name='searchQuery' value='${param.searchQuery}'/>
                        <c:param name='sort1' value='item_name'/>
                        <c:param name='dir1'  value='desc'/>
                     </c:url>">
              食材名 ▼
            </a>
          </th>
          <th>個数</th>
          <th>
            <!-- 単独で 賞味期限↑ に並べ替えるリンク（任意） -->
            <a href="<c:url value='/inventoryList'>
                        <c:param name='searchQuery' value='${param.searchQuery}'/>
                        <c:param name='sort1' value='expiry_date'/>
                        <c:param name='dir1'  value='asc'/>
                     </c:url>">
              賞味期限 ▲
            </a>
          </th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="inventory" items="${inventoryList}">
          <tr>
            <td>
              <a href="<c:url value='/inventoryDetail'>
                         <c:param name='id' value='${inventory.itemId}'/>
                       </c:url>">${inventory.itemName}</a>
            </td>
            <td>${inventory.quantity}</td>
            <<td style="${inventory.expiringSoon ? 'color: red;' : ''}">
  <c:if test="${not empty inventory.expiryDate}">
    <c:out value="${fn:replace(fn:substring(inventory.expiryDate, 0, 10), '-', '/')}"/>
  </c:if>
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
