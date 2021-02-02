<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>공지글쓰기</h3>

<form action="insert.no" method="post">
<table>
<tr>
	<th class="w-px160">제목</th>
	<td><input type="text" name="title" title="제목" class="chk"></td>
</tr>
<tr>
	<th>작성자</th>
	<td>${loginInfo.name }</td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea name="content" title="내용" class="chk"></textarea></td>
</tr>

</table>
</form>

<div class="btnSet">
<a class="btn-fill" onclick="if( emptyCheck() ) $('form').submit()">저장</a>
<a class="btn-empty" href="list.no">취소</a>
</div>

<script type="text/javascript">
function emptyCheck(){
	var ok = true;
	$('.chk').each(function(){
		if( $(this).val() == '' ){
			alert( $(this).attr('title') + '을 입력하세요');
			$(this).focus();
			ok = false;
			return ok;
		}
		
	});
	return ok;
}
</script>
</body>
</html>