<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>질문게시판</h3>

<form action="list.qa" method="post">
<div id="list-top">
	<div>
	<ul>
		<li><select>
				<option>전체</option>
				<option>제목</option>
				<option>내용</option>
				<option>작성자</option>
			</select>
		</li>
		<li><input type="text"></li>
		<li><a class="btn-fill">검색</a></li>
	</ul>
	<ul>
		<li><a class="btn-fill" href="new.qa">글쓰기</a></li>
	</ul>
	</div>
</div>
<input type="hidden" name="curPage" value="1" />
</form>

<table>
<tr>
	<th class="w-px80">번호</th>
	<th>제목</th>
	<th class="w-px120">작성자</th>
	<th class="w-px120">작성일자</th>
</tr>
<c:forEach items="${page.list}" var="vo">
<tr>
	<td>${vo.no }</td>
	<td class="left"><a href="view.qa?id=${vo.id}">${vo.title}</a></td>
	<td>${vo.name }</td>
	<td>${vo.writedate }</td>
</tr>
</c:forEach>
</table>
<div class="btnSet">
	<jsp:include page="/WEB-INF/views/include/page.jsp"/>
</div>
</body>
</html>