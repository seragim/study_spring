<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>공지글 안내</h3>

<table>
<tr><th class='w-px160'>제목</th>
	<td colspan='5' class='left'>${vo.title}</td>
</tr>
<tr><th>작성자</th>
	<td>${vo.name}</td>
	<th class='w-px120'>작성일자</th>
	<td class='w-px120'>${vo.writedate}</td>
	<th class='w-px80'>조회수</th>
	<td class='w-px80'>${vo.readcnt}</td>
</tr>
<tr><th>내용</th>
	<td colspan='5' class='left'>${ fn:replace(vo.content, crlf, '<br>') }</td>
</tr>
<tr><th>첨부파일</th>
	<td colspan='5' class='left'>${vo.filename}
		<c:if test="${ ! empty vo.filename }">
		<a href='download.no?id=${vo.id}'><i class="fas fa-download"></i></a>
		</c:if>
	</td>
</tr>
</table>

<div class='btnSet'>
	<a class='btn-fill' href='list.no'>목록으로</a>
	<!-- 관리자로 로그인한 경우만 수정/삭제 권한 있음 -->
	<c:if test='${loginInfo.admin eq "Y"}'>
	<a class='btn-fill' href='modify.no?id=${vo.id}'>수정</a>
	<a class='btn-fill' 
	onclick="if( confirm('정말 삭제?') ){ location='delete.no?id=${vo.id}' }">삭제</a>
	</c:if>
</div>

</body>
</html>









