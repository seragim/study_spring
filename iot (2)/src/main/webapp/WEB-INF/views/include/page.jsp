<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page_list">

<!-- 이전/처음 -->
<c:if test="${page.curBlock gt 1}">
	<a class="page_first" title="처음" onclick="go_page(1)">처음</a>
	<a class="page_prev" title="이전" onclick="go_page(${page.beginPage - page.blockPage})">이전</a><!-- 이전블럭의 첫페이지 -->
	<%-- <a class="page_prev" title="이전" onclick="go_page(${page.beginPage - 1})">이전</a> --%><!-- 이전블럭의 마지막페이지 -->
</c:if>
<c:forEach var="no" begin="${page.beginPage}" end="${page.endPage}">
	<c:if test="${no eq page.curPage}"><!-- 현재페이지인 경우 -->
	<span class="page_on">${no}</span>
	</c:if>
	<c:if test="${no ne page.curPage}"><!-- 현재페이지 아닌경우 -->
	<a class="page_off" onclick="go_page(${no})">${no}</a>
	</c:if>
</c:forEach>

<!-- 다음/마지막 -->
<c:if test="${page.curBlock lt page.totalPage }">
	<a class="page_next" title="다음" onclick="go_page(${page.endPage+1})">다음</a>
	<a class="page_last" title="마지막" onclick="go_page(${page.totalPage})">마지막</a>
</c:if>
</div>
<script type="text/javascript">
function go_page(page){
	$('[name=curPage]').val( page );
	$('form').submit();
}
</script>
<style>
.page_on, .page_off, .page_next, .page_last, .page_first, .page_prev { 
	display: inline-block; line-height: 30px; margin: 0;
}
.page_on { border: 1px solid gray; background-color: gray; color: #fff; font-weight: bold;}

.page_on, .page_off { 
	min-width: 22px; padding: 0 5px 2px;
}
.page_next, .page_last, .page_first, .page_prev { 
	width: 30px; height: 32px; border: 1px solid gray; text-indent: -999999999999999px;
 }

.page_next { background: url("imgs/page_next.jpg") center no-repeat; }
.page_last { background: url("imgs/page_last.jpg") center no-repeat; }
.page_first { background: url("imgs/page_first.jpg") center no-repeat; }
.page_prev { background: url("imgs/page_prev.jpg") center no-repeat; }
</style>