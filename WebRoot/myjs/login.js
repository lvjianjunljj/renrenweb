$(document).ready(function (){
	var msg;
	$("button").button();
	$("input[type='submit'],input[type='reset'],input[type='button']").button();
	
	$("input[type='button']").click(function() {
		self.location='register.jsp'; 
	});
	
	$("input[type='text'],input[type='password']").blur(function() {
		
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
				});	
	});
	$("input[type='submit']").click(function(){
		var mark;
		if(msg == "success")
		{
			mark = true;
		}else if(msg == "usernameerror"){
			alert("用户名错误");
			mark = false;
			
		}else if(msg == "passworderror"){
			alert("密码错误");
			mark = false;
			
		}else{
			alert("未知登录错误");
			mark = false;
		}
		return mark;
	});
	
	
	$("#checknum").click(function() {
	});
	
	
	
	
	
})