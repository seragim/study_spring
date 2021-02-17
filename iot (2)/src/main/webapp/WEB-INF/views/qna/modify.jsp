<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>질문글 수정</h3>

<form action="update.qa" method="post">
<table>
<tr>
	<th>제목</th>
	<td><input type="text" name="title" value="${vo.title}"></td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea name="content">${vo.content}</textarea></td>
</tr>
<tr>
	<th>첨부파일</th>
	<td></td>
</tr>
</table>
<input type="hidden" name="id" value="${vo.id}">
</form>
<div class="btnSet">
	<a class="btn-fill" onclick="$('form').submit();">저장</a>
	<a class="btn-empty" href="javascript:history.go(-1)">취소</a>
</div>
</body>
</html>