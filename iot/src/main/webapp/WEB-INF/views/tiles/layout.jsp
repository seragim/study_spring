<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:choose>
	<c:when test="${category eq 'cu'}">
		<c:set var="title" value="고객관리 :" />
	</c:when>
	<c:when test="${category eq 'no' }">
		<c:set var="title" value="공지사항 :" />
	</c:when>
	<c:when test="${category eq 'bo' }">
		<c:set var="title" value="방명록 :" />
	</c:when>
	<c:when test="${category eq 'da' }">
		<c:set var="title" value="공공데이터 :" />
	</c:when>
	<c:when test="${category eq 'hr' }">
		<c:set var="title" value="사원관리 :" />
	</c:when>
	<c:when test="${category eq 'login' }">
		<c:set var="title" value="로그인 :" />
	</c:when>
	<c:when test="${category eq 'join' }">
		<c:set var="title" value="회원가입 :" />
	</c:when>
</c:choose>

<title>${title}IOT</title>
<link rel="icon" type="image/x-icon" href="imgs/hanul.ico">
<link rel='stylesheet' type='text/css' 
		href='css/common.css?v=<%=new java.util.Date().getTime() %>'>
<script src='https://code.jquery.com/jquery-3.5.1.min.js'></script>
<script type="text/javascript" 
src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/js/all.min.js"></script>
</head>
<body>
<tiles:insertAttribute name="header" />
<div id='content'>
<tiles:insertAttribute name="content" />
</div>
<tiles:insertAttribute name="footer" />
</body>
</html>