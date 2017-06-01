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
	<form action="userAction" method="post" align="center">
		账号：	&nbsp;&nbsp;&nbsp;	<input type="text" name="username" autofocus="true"/><br/><br/>
		密码：	&nbsp;&nbsp;&nbsp;	<input type="password" name="password"/><br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		验证码：	&nbsp;&nbsp;		<input type="text" name="checknumid"  align="center"/>
		&nbsp;
		<img src="jsp/checknum.jsp" id="checknum" width="50" height="32"/>
		<br/><br/>
		<input type="submit" value="提交"/>
		<input type="reset" value="重置"/>
		<input type="button" id="register" value="注册"/>
	</form>   
  </body>
</html>
