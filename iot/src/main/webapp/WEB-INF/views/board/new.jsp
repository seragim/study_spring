<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>방명록 글쓰기</h3>
<form method="post" action="insert.bo" enctype="multipart/form-data" >
<table>
<tr><th class='w-px160'>제목</th>
	<td><input type='text' name='title' title='제목' class='chk' /></td>
</tr>
<tr><th>작성자</th>
	<td>${loginInfo.name}</td>
</tr>
<tr><th>내용</th>
	<td><textarea name='content' title='내용' class='chk' ></textarea> </td>
</tr>
<tr><th>파일첨부</th>
	<td class='left'>
		<label>
		<input type='file' name='file' id='attach-file' />
		<img src='imgs/select.png' class='file-img' />
		</label>
		<span id='file-name'></span>
		<span id='preview'></span>
		<span id='delete-file'><i class='fas fa-times'></i></span>
	</td>
</tr>
</table>
</form>
<div class='btnSet'>
<a class='btn-fill' onclick="if( emptyCheck() ) $('form').submit()">저장</a>
<a class='btn-empty' href='list.bo'>취소</a>
</div>

<script type="text/javascript" 
	src="js/file_check.js?v=<%=new java.util.Date().getTime()%>"></script>

</body>
</html>


















