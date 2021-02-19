<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>방명록 목록</h3>

<div id='list-top'>
<form method="post" action='list.bo'>
<div>
	<ul>
		<li><select name='search' class='w-px100'>
			<option value='all' ${page.search eq 'all' ? 'selected' : ''}>전체</option>
			<option value='title' ${page.search eq 'title' ? 'selected' : ''}>제목</option>
			<option value='content' ${page.search eq 'content' ? 'selected' : ''}>내용</option>
			<option value='writer' ${page.search eq 'writer' ? 'selected' : ''}>작성자</option>
			</select>
		</li>
		<li><input type='text' name='keyword' value='${page.keyword}' class='w-px300'/>
		</li>
		<li><a class='btn-fill' onclick="$('form').submit()">검색</a></li>
	</ul>
	<ul>
		<li><select name='pageList' class='w-px80' 
					onchange="$('[name=curPage]').val(1); $('form').submit()" >
				<option value='10' ${page.pageList eq 10 ? 'selected' : ''}>10개씩</option>
				<option value='20' ${page.pageList eq 20 ? 'selected' : ''}>20개씩</option>
				<option value='30' ${page.pageList eq 30 ? 'selected' : ''}>30개씩</option>
			</select>
		</li>
		<li><select name='viewType' class='w-px100'
					onchange='$("form").submit()'  >
			<option value='list' ${page.viewType eq 'list' ? 'selected' : ''}>리스트형태</option>
			<option value='grid' ${page.viewType eq 'grid' ? 'selected' : ''}>바둑판형태</option>
			</select>
		</li>
		<!-- 로그인한 경우 글쓰기 가능 -->
		<c:if test="${!empty loginInfo}">
		<li><a class='btn-fill' href='new.bo'>글쓰기</a></li>
		</c:if>
	</ul>
</div>
<input type='hidden' name='curPage' value='1'/>
<input type="hidden" name='id'/>
</form>
</div>

<div id='data-list'>

<c:if test='${page.viewType eq "grid"}'>
<ul class='grid'>
	<c:forEach items="${page.list}" var="vo">
	<li><div><a href='javascript:go_view(${vo.id})'>${vo.title}</a></div>
		<div>${vo.name}</div>
		<div>${vo.writedate}</div>
	</li>
	</c:forEach>
</ul>
</c:if>

<c:if test="${page.viewType eq 'list'}">
<table>
<tr><th class='w-px80'>번호</th>
	<th>제목</th>
	<th class='w-px120'>작성자</th>
	<th class='w-px120'>작성일자</th>
	<th class='w-px80'>첨부파일</th>
</tr>
<c:forEach items="${page.list}" var="vo">
<tr><td>${vo.no}</td>
	<td class='left'><a href='javascript:go_view(${vo.id})'>${vo.title}</a></td>
	<td>${vo.name}</td>
	<td>${vo.writedate}</td>
	<td>${empty vo.filename ? '' : '<img class="file-img" src="imgs/attach.png"/>'}</td>
</tr>
</c:forEach>
</table>
</c:if>

</div>

<div class='btnSet'>
	<jsp:include page="/WEB-INF/views/include/page.jsp"/>
</div>

<script type="text/javascript">
function go_view(id){
	$('[name=id]').val(id);
	$('form').attr('action', 'view.bo');
	$('form').submit();
}

$(function(){
	var len = $('.grid li').length;
	var height = ( ( len % 5 > 0 ? 1 : 0 ) + Math.floor(len / 5) ) 
					* $('.grid li').outerHeight(true) - 20;  
	
	$('#data-list ul.grid').css('height', height );
});
</script>
</body>
</html>













