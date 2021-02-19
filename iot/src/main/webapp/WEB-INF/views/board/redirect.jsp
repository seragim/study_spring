<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${url}" method="post">
<input type='hidden' name='id' value='${id}'/>
<input type='hidden' name='curPage' value='${page.curPage}'/>
<input type='hidden' name='search' value='${page.search}'/>
<input type='hidden' name='keyword' value='${page.keyword}'/>
<input type='hidden' name='pageList' value='${page.pageList}'/>
<input type='hidden' name='viewType' value='${page.viewType}'/>
</form>
<script type="text/javascript">
$('form').submit();
</script>
</body>
</html>






