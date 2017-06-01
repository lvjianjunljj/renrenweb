$(document).ready(function() {
	
	var size;
	var usernames = new Array();
	var passwords = new Array();
	var competences = new Array();
	
	$("#radioDiv").buttonset();
	$("button").button();
	
	$("input[name='list']").change(function(){
		cleanusers();
		listusers();
	});
	
	$("button[name='clean'").click(function(){cleanusers();});
	
	$("button[name='find'").click(function(){
		cleanusers();
		findbyusername();
	});
	
	/*live() 方法为被选元素附加一个或多个事件处理程序，并规定当这些事件发生时运行的函数（live方法被移除，用on方法代替）
	 *这里第一个选中的jquery元素不能使后添加的DOM元素，而是其已经存在的父元素（因为是动态添加的，所以提前不能选中，而第二个参数就是对应其中的触发事件的子元素） 
	 */
	$("#userslist").on("click","a", function(e){
		e.preventDefault();
		showstatus($(this).attr("name"));
		});
	
	$.post("getallusersAction", function(returnedData, status) {
		size = $(returnedData).find("size").text();
		$("body").append()
		for(var i=0;i<size;i++)
		{
			var username = $(returnedData).find("user"+i).find("username").text();
			var password = $(returnedData).find("user"+i).find("password").text();
			var competence = $(returnedData).find("user"+i).find("competence").text();
			usernames.push(username);
			passwords.push(password);
			competences.push(competence);
		}
	});
	
	function cleanusers(){
		$("#users").remove();
	}
	
	function listusers(){
		var mark = $("input:radio[name='list']:checked").attr("id");
		list(mark);
	}
	
	function list(mark){
		var message = "";
		
		if(mark == "allusers")
		{
			for(var i=0;i<size;i++)
			{
				if(competences[i] == 1){
					message += "<tr><td>"+(i+1)+"</td><td>"+usernames[i]+"</td><td>"+passwords[i]+"</td><td>管理员</td><td><a href='#' name='" + usernames[i] + "'>状态</a></td></tr>";
				}else{
					message += ("<tr><td>"+(i+1)+"</td><td>"+usernames[i]+"</td><td>"+passwords[i]+"</td><td>普通用户</td><td><a href='#' name='" + usernames[i] + "'>状态</a></td></tr>");
				}
			}
		}else if(mark == "commonusers"){
			for(var i=0;i<size;i++)
			{
				if(competences[i] == 0){
					message += ("<tr><td>"+(i+1)+"</td><td>"+usernames[i]+"</td><td>"+passwords[i]+"</td><td>普通用户</td><td><a href='#' name='" + usernames[i] + "'>状态</a></td></tr>");
				}
			}
		}else if(mark == "administrator"){
			for(var i=0;i<size;i++)
			{
				if(competences[i] == 1){
					message += "<tr><td>"+(i+1)+"</td><td>"+usernames[i]+"</td><td>"+passwords[i]+"</td><td>管理员</td><td><a href='#' name='" + usernames[i] + "'>状态</a></td></tr>";
				}
			}
		}
		showtable(message);
	}
	
	function findbyusername(){
		var message = "";
		var usernamefind = $("input[name='usernamefind'").val();
		for(var i=0;i<size;i++)
		{
			if(usernames[i] == usernamefind){
				if(competences[i] == 1){
					message += "<tr><td>"+(i+1)+"</td><td>"+usernames[i]+"</td><td>"+passwords[i]+"</td><td>管理员</td><td><a href='#' name='" + usernames[i] + "'>状态</a></td></tr>";
				}else{
					message += ("<tr><td>"+(i+1)+"</td><td>"+usernames[i]+"</td><td>"+passwords[i]+"</td><td>普通用户</td><td><a href='#' name='" + usernames[i] + "'>状态</a></td></tr>");
				}
			}
		}
		showtable(message);
	}
	
	function showtable(message){
		var table = "<table id='users'><tr><th>编号</th><th>用户名</th><th>密码</th><th>备注</th><th>详细资料</th></tr>";
		table += message;
		table += "</table>";
		$("#userslist").append(table);
	}
	
	function showstatus(message){
		var content = "<form action='inqueryAction' method='post' style='display:none' id='showstatus'><input type='text' name='userselfname' value='" +message+ "'/><input type='text' name='visitor' value='administrator'/></form>";
		$("#showstatus").remove();
		$("#userslist").append(content);
		$("#showstatus").submit();
	}
})