<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
#popup { 
	position: absolute; left: 50%; top: 50%; transform:translate(-50%, -50%);
	width: 350px; height: 350px;
	border: 2px solid #666; display: none;
 }

#popup-background { 
	position: absolute; left: 0; top: 0; width: 100%; height: 100%;
	background-color: #000; opacity: 0.3; display: none;
 }
 
 .popup { width: 100%; height:100%; }

</style>

</head>
<body>
<h3>방명록 안내</h3>
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
	<td colspan="5" class="left">${fn:replace( fn:replace(vo.content, crlf, '<br>') , lf, '<br>')}</td>
</tr>
<tr>
	<th>첨부파일</th>
	<td colspan="5" class="left">${vo.filename}
		<c:if test="${!empty vo.filename}">
			<span id="preview"></span>
			<a href="download.bo?id=${vo.id}"><i class="fas fa-download"></i></a>
		</c:if>
	</td>

</tr>

</table>

<div class="btnSet">
	<a href="list.bo" class="btn-fill">목록으로</a>
	
</div>

<div id="popup-background" onclick="$('#popup, #popup-background').css('display', 'none');"></div>
<div id="popup"></div>

<script type="text/javascript" src="js/file_check.js"></script>
<script type="text/javascript">

$(function(){
	//첨부된 파일이 있고, 그 파일이 이미지라면 미리보기에 이미지가 보이게
	if( ${!empty vo.filename} ){
		if( isImage( '${vo.filename}' ) ){
			var img = '<img src="${vo.filepath}" class="file-img" id="preview-img" />';
			$('#preview').html( img );
		}
	}
});
$(document).on('click', '#preview-img', function(){
	$('#popup, #popup-background').css('display', 'block');
	var img = '<img src="${vo.filepath}" class="popup" id="preview-img" />';
	$('#popup').html( img );
});
</script>
</body>
</html>