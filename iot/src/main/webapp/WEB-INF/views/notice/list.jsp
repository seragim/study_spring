<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>공지글 목록</h3>

<div id="list-top">
	<div>
	<ul>
		<c:if test="${loginInfo.admin eq 'Y' }">
			<li><a class="btn-fill" href="new.no">글쓰기</a></li>
		</c:if>
	</ul>
	</div>
</div>
<table>
<tr>
	<th class="w-px60">번호</th>
	<th>제목</th>
	<th class="w-px120">작성자</th>
	<th class="w-px120">작성일자</th>
</tr>
<c:forEach items="${list}" var="vo">
<tr>
	<td>${vo.no }</td>
	<td class="left"><a href="view.no?id=${vo.id}">${vo.title }</a></td>
	<td>${vo.name }</td>
	<td>${vo.writedate }</td>
</tr>

</c:forEach>

</table>

</body>
</html>