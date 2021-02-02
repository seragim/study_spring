<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>공지글 안내</h3>

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
	<td colspan="5" class="left">${fn:replace(vo.content, crlf, '<br>')}</td>
</tr>
</table>

</body>
</html>