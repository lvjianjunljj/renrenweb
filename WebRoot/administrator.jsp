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
		<script src="myjs/administrator.js"></script>
		<link href="mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" />
		<title>管理员登录界面</title>
	</head>
	<body>
		欢迎管理员登录！
		<br/>
		用户名：<input type="text" name="usernamefind"/>&nbsp;
		<button name="find">查询</button>
		<button name="clean">清空</button>
		<br/><br/>
		<form>
        <div id="radioDiv">
            <input type="radio" name="list" id="allusers"/>			<label for="allusers">所有用户</label>
            <input type="radio" name="list" id="commonusers"/>		<label for="commonusers">普通用户</label>
            <input type="radio" name="list" id="administrator"/>	<label for="administrator">管理用户</label>
        </div>
    </form>
		<div id="userslist"></div>
	</body>
</html>