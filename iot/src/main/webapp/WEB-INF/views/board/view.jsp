<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
#popup{
	position:absolute;  left:50%; top:50%; transform:translate(-50%, -50%);
	width:350px; height:350px;
	border:2px solid #666; display:none;
}

.popup { width:100%; height:100%; }
#comment_regist span{ width:50%; float:left; }
</style>

</head>
<body>
<h3>방명록 안내</h3>
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
	<td colspan='5' class='left'>${fn: replace(  fn:replace(vo.content, crlf, '<br>'), lf, '<br>') }</td>	
</tr>
<tr><th>첨부파일</th>
	<td colspan='5' class='left'>${vo.filename}
		<c:if test="${ ! empty vo.filename }">
			<span id='preview'></span>
			<a href='download.bo?id=${vo.id}'><i class='fas fa-download'></i></a>
		</c:if>
	</td>
</tr>
</table>

<div class='btnSet'>
	<a class='btn-fill' href='javascript:$("form").submit()'>목록으로</a>
	<!-- 로그인한 사용자가 작성한 글에 대해 수정/삭제 권한 가짐 -->
	<c:if test="${loginInfo.id eq vo.writer}">  
	<a class='btn-fill' onclick="$('form').attr('action', 'modify.bo');  $('form').submit();">수정</a>
	<a class='btn-fill' onclick="if( confirm('정말 삭제?') ){ location='delete.bo?id=${vo.id}' }">삭제</a>
	</c:if>
</div>

<form method="post" action="list.bo">
<input type='hidden' name='id' value='${vo.id}'/>
<input type='hidden' name='curPage' value='${page.curPage}'/>
<input type='hidden' name='search' value='${page.search}'/>
<input type='hidden' name='keyword' value='${page.keyword}'/>
<input type='hidden' name='pageList' value='${page.pageList}'/>
<input type='hidden' name='viewType' value='${page.viewType}'/>
</form>

<div style='margin:0 auto; padding-top:20px; width:500px'>
	<div id='comment_regist'>
		<span class='left'><strong>댓글작성</strong></span>
		<span class='right'><a class='btn-fill-s' onclick='comment_regist()' >댓글등록</a></span>
		<textarea id='comment' style='margin-top:5px; width:96%; height:60px; resize:none'></textarea>
	</div>
	<div id='comment_list' style='text-align:left'>
	</div>
</div>

<div id='popup-background' onclick="$('#popup, #popup-background').css('display', 'none');"></div>
<div id='popup'></div>

<script type="text/javascript" src="js/file_check.js"></script>
<script>
comment_list();
function comment_regist(){
	if( ${empty loginInfo} ){
		alert('댓글을 등록하려면 로그인하세요!');
		return;
	}else if( $.trim($('#comment').val())=='' ){
		alert('댓글을 입력하세요!');
		$('#comment').val('');
		$('#comment').focus();
		return;
	}

	$.ajax({
		url: 'board/comment/insert',
		data: { pid:${vo.id}, content:$('#comment').val() },
		success: function( response ){
			if( response ){
				alert('댓글이 등록되었습니다!');
				$('#comment').val('');
				comment_list();
			}else{
				alert('댓글 등록 실패ㅠㅠ');
			}
		},error: function(req, text){
			alert(text+ ':'+ req.status);
		}
	
	});
}

function comment_list(){
	$.ajax({
		url: 'board/comment/${vo.id}',
		success: function( response ){
			$('#comment_list').html(response);
		
		},error: function(req, text){
			alert(text+ ':'+ req.status);
		}
	});
}

$(function(){
	//첨부된 파일이 있고, 그 파일이 이미지라면 미리보기에 이미지가 보이게
	if( ${ !empty vo.filename } ){
		if( isImage( '${vo.filename}' ) ){
			var img = '<img src="${vo.filepath}" class="file-img" id="preview-img" />';
			$('#preview').html( img );
		}
	}
});
$(document).on( 'click', '#preview-img', function(){
	$('#popup, #popup-background').css('display', 'block');
	var img = '<img src="${vo.filepath}" class="popup" id="preview-img" />';
	$('#popup').html( img );
});
</script>

</body>
</html>
















