<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
<head>
		<script src="js/jquery.js"></script>
        <script src="js/handlebars.js"></script>
    	<script src="js/handlebars-jquery.js"></script>
    	<script src="js/jquery-ui-1.10.3.custom.js"></script> 
    	<script src="myjs/login.js"></script>       
		<link href="mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" />
</head>
  
  
  <br/>
	<form action="registerAction" method="post" align="center">
		账号：	&nbsp;&nbsp;&nbsp;	<input type="text" name="username" autofocus="true" placeholder="请输入6到10位数字或字母"/><br/><br/>
		密码：	&nbsp;&nbsp;&nbsp;	<input type="password" name="password" placeholder="请输入密码"/><br/><br/>
		年龄：	&nbsp;&nbsp;&nbsp;	<input type="text" name="age" placeholder="请输入年龄"/><br/><br/>
		<br/><br/>
		<input type="submit" value="注册"/>
		<input type="reset" value="重置"/>
		
	</form>       
  </body>
</html>
