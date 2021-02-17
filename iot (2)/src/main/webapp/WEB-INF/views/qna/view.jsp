<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>질문글 상세보기</h3>

<table>
<tr>
	<th class="w-px160">제목</th>
	<td colspan="5" class="left">${vo.title}</td>
</tr>
<tr>
	<th>작성자</th>
	<td>${vo.name}</td>
	<th class="w-px120">작성일자</th>
	<td class="w-px120">${vo.writedate}</td>
	<th class="w-px80">조회수</th>
	<td class="w-px80">${vo.readcnt}</td>
</tr>
<tr>
	<th>내용</th>
	<td colspan="5" class="left">${vo.content}</td>
</tr>
</table>
<div class="btnSet">
	<a class="btn-fill" href="list.qa">목록으로</a>
	<!-- 본인으로 로그인된 경우 질문글 수정/삭제 가능 -->
	<c:if test="${loginInfo.id eq vo.writer}">
	<a class="btn-fill" >수정</a>
	<a class="btn-fill" >삭제</a>
	</c:if>
</div>
</body>
</html>