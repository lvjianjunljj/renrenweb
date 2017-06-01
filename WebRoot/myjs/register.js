$(document).ready(function (){
	var mark;
	$("button").button();
	$("input[type='submit'],input[type='reset'],input[type='button']").button();
	
	$("input[type='button']").click(function() {
		self.location='register.jsp'; 
	});
	
	$("input[type='submit']").click(function() {
		if(($("input[name='username']").val().length)<6 || ($("input[name='username']").val().length)>10)
		{
			alert("账号注册请输入6到10位数字或字母");
			return false;
		}
		
		if(($("input[name='password']").val() == ""))
		{
			alert("密码不能为空");
			return false;
		}
		
		if(!mark){return false}
		
	});
	
	/* 基本操作可见jquery中的13-3*/
	function handleFormBlur() {
		
		$.post("loginVerifyAction", 
				{
					username : $("input[name='username'").val(),
					password : $("input[name='password'").val()
				},
				function(returnedData, status)
				{
					msg = $(returnedData).find("users").text();
					/*需要将传入的信息进行过滤才能使用（一般是过滤掉字符串前后的空格） */
					msg = msg.replace(/(^\s*)|(\s*$)/g, "");
					if(msg != "usernameerror")
					{
						var html = "<span id='errormark' style='color: red'>对不起，用户名已存在</span>";
						$("br:nth(1)").after(html);
						mark = false;
					}
				});				
    }
	
	function handleFormFocus() {
		if(!mark)
		{
			$("#errormark").remove();
			mark = true;
		}
	}
	
    $("input[name='username']").blur(handleFormBlur).focus(handleFormFocus);
	
    $("input[type='submit']").click(function(){
		if(!mark){return false}
	});
    
	$("#checknum").click(function() {
	});
})