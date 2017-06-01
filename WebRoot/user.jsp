<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">

		<script src="js/jquery.js"></script>
		<script src="js/handlebars.js"></script>
		<script src="js/handlebars-jquery.js"></script>
		<script src="js/jquery-ui-1.10.3.custom.js"></script>
		<link href="mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" />
		<script src="myjs/user.js"></script>
		<title>用户登录界面</title>
	</head>
	<body>
	<%--只能通过这种方式在jsp中获得后台传过来的数据，然后js中再从jsp中读取（js一定有直接读取的方法，老子没有找到） --%>
	<form id="data" style="display:none">
		<input id="usernameData" type="text" value="${userselfname}"/>
		<input id="visitorData" type="text" value="${visitor}"/>
	</form>
	<div id="function"></div>
	
	<div id="lists"></div>
	
	
	</body>
</html>