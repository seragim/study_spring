<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>방명록 목록</h3>


<div id="list-top">
<form action="list.bo" method="post">
<div>
	<ul>
		<li>
			<select name="search" class="w-px80">
				<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
				<option value="title" ${page.search eq 'title' ? 'selected' : '' }>제목</option>
				<option value="content" ${page.search eq 'content' ? 'selected' : '' }>내용</option>
				<option value="writer" ${page.search eq 'writer' ? 'selected' : '' }>작성자</option>
			</select>
		</li>
		<li>
		<input type="text" name="keyword" value="${page.keyword}" class="w-px300">
		</li>
		<li><a class="btn-fill" onclick="$('form').submit()">검색</a></li>
	</ul>
	<ul>
		<!-- 로그인한 경우 글쓰기 가능 -->
		<c:if test="${!empty loginInfo}">
		<li><a class="btn-fill" href="new.bo">글쓰기</a></li>
		</c:if>
	</ul>
</div>
<input type="hidden" name="curPage" value="1"/>
</form>
</div>

<div id="data-list">
<table>
<tr>
	<th class="w-px80">번호</th>
	<th>제목</th>
	<th class="w-px120">작성자</th>
	<th class="w-px120">작성일자</th>
	<th class="w-px80">첨부파일</th>
</tr>
<c:forEach items="${page.list}" var="vo">
<tr>
	<td>${vo.no}</td>
	<td class="left">${vo.title}</td>
	<td>${vo.name}</td>
	<td>${vo.writedate}</td>
	<td>${empty vo.filename ? '' : '<img class="file-img" src="imgs/attach.png"/>'}</td>
</tr>


</c:forEach>

</table>
</div>

<div class="btnSet">
	<jsp:include page="/WEB-INF/views/include/page.jsp"/>
</div>

</body>
</html>