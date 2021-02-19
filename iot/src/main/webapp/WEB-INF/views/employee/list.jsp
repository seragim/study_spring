<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>사원목록</h3>
<div id='list-top'>
<form method="post" action='list.hr'>
<ul><li><span style="margin-right:10px">부서명</span></li>
	<li>
		<select name="depts" onchange="$('form').submit()">
			<option ${dept_id eq 'all' ? 'selected' :''} value='all'>전체</option>
			<c:forEach items="${depts}" var="vo">
			<option 
	${!empty dept_id and dept_id ne 'all' and dept_id eq vo.department_id ? 'selected' :''}  value='${vo.department_id}'>${vo.department_name}</option>

			</c:forEach>
		</select>
	</li>
</ul>
</form>
</div>

<table>
<tr><th class='w-px80'>사번</th>
	<th class='w-px250'>성명</th>
	<th class='w-px300'>부서명</th>
	<th>업무명</th>
	<th class='w-px120'>급여</th>
</tr>
<c:forEach items="${list}" var="vo">
<tr>
	<td>${vo.employee_id}</td>
	<td><a href='detail.hr?id=${vo.employee_id}'>${vo.last_name} ${vo.first_name}</a></td>
	<td>${vo.department_name}</td>
	<td>${vo.job_title}</td>
	<td>${vo.salary}</td>
</tr>
</c:forEach>
</table>

</body>
</html>






