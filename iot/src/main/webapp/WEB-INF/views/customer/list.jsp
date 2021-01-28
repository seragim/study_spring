<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>고객목록</h3>
<table class='w-pct40'>
<tr><th class='w-pct40'>고객명</th>
	<th>이메일</th>
</tr>
<c:forEach items="${list}" var="vo">
<tr><td><a href='detail.cu?id=${vo.id}'>${vo.name}</a></td>
	<td>${vo.email}</td>
</tr>
</c:forEach>
</table>

<div class='btnSet'>
	<a class='btn-fill' href='new.cu'>신규고객</a>
</div>
</body>
</html>






