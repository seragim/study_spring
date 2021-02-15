<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>방명록 안내 수정</h3>
<form action="update.bo" method="post" enctype="multipart/form-data">
<table>
<tr>
	<th>제목</th>
	<td><input type="text" name="title" class="chk" title="제목" value="${vo.title}"/></td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea name="content" class="chk" title="내용">${vo.content}</textarea></td>
</tr>
<tr>
	<th>첨부파일</th>
	<td class="left">
		<label><input type="file" name="file" id="attach-file"/>
				<img src="imgs/select.png" class="file-img" />
		</label>
		<span id="file-name">${vo.filename }</span>
		<span id="preview"></span>
		<span id="delete-file"><i class="fas fa-times"></i></span>
	</td>
</tr>
</table>
<input type="hidden" name="id" value="${vo.id}"/>
<input type="hidden" name="attach"/>
</form>
<div class="btnSet">
	<a class="btn-fill" onclick="$('[name=attach]').val( $('#file-name').text() ); $('form').submit();">저장</a>
	<a class="btn-empty" onclick="history.go(-1)">취소</a>
</div>
<script type="text/javascript" src="js/file_check.js?v=<%=new java.util.Date().getTime()%>"></script>
<script type="text/javascript">
if( ${!empty vo.filename} ){
	$('#delete-file').css('display','inline');
		if( isImage( '${vo.filename}' ) ){
		var img = '<img src="${vo.filepath}" class="file-img" id="preview-img" />';
		$('#preview').html( img );
	}
}
</script>
</body>
</html>