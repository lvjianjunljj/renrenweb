/* $("#data").css("display","inline");none属性会使其隐藏并不占具空间，默认是inline 
 * 注意：在div中新添加的元素的id最好各不相同，不然调用对应方法的时候容易出现混乱
 * */
$(document).ready(function() {
	//个人信息变量
	var username;
	var password;
	var age;
	var competence;
	
	//日志列表变量
	var size = 0;
	var journaltitles = new Array();
	var journalcontents = new Array();
	
	//好友列表变量
	var friendsize = 0;
	var friendusernames = new Array();
	var friendages = new Array();
	
	pageshowcontent();
	
	
	$.post("getalljournalsAction", 
			{
				informationusername : $("#usernameData").val()
			},
			function(returnedData, status) {
			var maxid = $(returnedData).find("maxid").text();
			for ( var i = 0; i < maxid; i++) {
				var journaltitle = $(returnedData).find("journal" + i).find("journaltitle").text();
				var journalcontent = $(returnedData).find("journal" + i).find("journalcontent").text();
				if(!(journaltitle == "")){
					journaltitles.push(journaltitle);
					journalcontents.push(journalcontent);
				}
			}
			size = journaltitles.length;
		});
	
	
	$.post("getuserinformationAction", 
			{
				informationusername : $("#usernameData").val()
			},
			function(returnedData, status) {
				username = $(returnedData).find("user").find("username").text();
				password = $(returnedData).find("user").find("password").text();
				age = $(returnedData).find("user").find("age").text();
				competence = $(returnedData).find("user").find("competence").text();
				if(competence == 0){
					competence = "普通用户";
				}else{
					competence = "管理员";
				}
		});
	
	$.post("getfriendslistAction", 
			{
				informationusername : $("#usernameData").val()
			},
			function(returnedData, status) {
				var maxid = $(returnedData).find("friendmaxid").text();
				for ( var i = 0; i < maxid; i++) {
					var friendusername = $(returnedData).find("friend"+i).find("friendusername").text();
					var friendage = $(returnedData).find("friend"+i).find("friendage").text();
					if(!(friendusername == "")){
						friendusernames.push(friendusername);
						friendages.push(friendage);
					}
				}
				friendsize = friendusernames.length;
		});
	
	
	function pageshowcontent() {
		$("body").prepend("<p>用户名："+$("#usernameData").val()+"</p>");
		if ($("#visitorData").val() == "userself") {
			$("body").prepend("<p>欢迎用户本人登录</p>");
			//jouqery的on方法一般用来给新添加的元素绑定动态功能（例如点击鼠标悬浮等），而load方法一般用来给新添加的元素绑定样式（一开始便需要被使用）
			$("#function").load("html/userlogin.html",userlogin)
			} else if ($("#visitorData").val() == "administrator") {
				$("body").prepend("<p>欢迎管理员查看 用户" + $("#usernameData").val() + "</p>");
				$("#function").load("html/administratorinquiry.html",administratorlogin);
			}
	
	}
	
	function userlogin(){
		$("button").button();
		$("#newjournal").css("background-color","yellow");
		$("#userinformation").click(userinformation_user);
		$("#journalslist").click(journalslist_user);
		$("#friendslist").click(friendslist_user);
		$("#findfriend").click(findfriend_user);
		$("#newjournal").click(newjournal_user);
	}
	
	function administratorlogin(){
		$("button").button();
		$("#newjournal").css("background-color","yellow");
		$("#userinformation").click(userinformation_administrator);
		$("#journalslist").click(journalslist_administrator);
		$("#friendslist").click(friendslist_administrator);
		$("#findfriend").click(findfriend_administrator);
	}

	function userinformation_user(){
		var content = "<br/>账号：" + username + "<br/><br/>密码：" + password + "<br/><br/>年龄：" + age + "<br/><br/>权限：" + competence + "</br></br><a href='#' id='alterinformation'>编辑</a>";
		$("#lists").empty();
		$("#lists").append(content);
		$("#lists").on("click","#alterinformation",alterinformation);
	}
	
	function journalslist_user(){
		$("#lists").empty();
		var content = "<br/><table><tr><th>序号</th><th>日志标题</th><th>日志详情</th></tr>";
		for(var i=0;i<size;i++){
			content += "<tr><td>" + (i+1) + "</td><td>" + journaltitles[i] + "</td><td><a href='#' id='journalnumber" +i+ "' journalnumber='" + i + "'>日志内容</a></td></tr>";
		}
		content += "</table>";
		$("#lists").append(content);
		for(var i=0;i<size;i++){
			/*1、jquery的on方法不能直接绑定自定义的function，好像只能这样
			 * 2、这里不知道为什么不能将$(this).attr("journalnumber")直接替换成i，这样输入会一直是5而不会随着点击发生变化
			 * */
			$("#lists").on("click","#journalnumber"+i,function(){showjournalcontent($(this).attr("journalnumber"))});
		}
		/*不能这样写，不然运行完这个方法以后页面未刷新之前这里对之后添加的元素的a标签页绑定了相应地方法（注：自己犯的错，跪着也要找到改过来，一开始为了图方便这么写，到后来改死了才发现错误在这里）。
		$("#lists").on("click","a",function(){showjournalcontent($(this).attr("journalnumber"))});*/
	}
	
	function friendslist_user(){
		$("#lists").empty();
		var content = "<br/><table><tr><th>序号</th><th>用户名</th><th>年龄</th><th>详细资料</th></tr>";
		for(var i=0;i<friendsize;i++){
			content += "<tr><td>" + (i+1) + "</td><td>" + friendusernames[i] + "</td><td>&nbsp;" + friendages[i] + "&nbsp;</td><td><a href='#' id='friendnumber" +i+ "' friendnumber='" + i + "'>状态</a></td></tr>";
		}
		content += "</table>";
		$("#lists").append(content);
		for(var i=0;i<friendsize;i++){
			$("#lists").on("click","#friendnumber"+i,function(){showfriend($(this).attr("friendnumber"))});
		}
	}
	
	function findfriend_user(){
		$("#lists").empty();
		var findfriendusername = $("#findfriendusername").val();
		alert(findfriendusername);
		for(var i=0;i<friendsize;i++)
		{
			if(friendusernames[i] == findfriendusername){
				var content = "<tr><td>" + (i+1) + "</td><td>" + friendusernames[i] + "</td><td>&nbsp;" + friendages[i] + "&nbsp;</td><td><a href='#' id='friendnumber" +i+ "' friendnumber='" + i + "'>状态</a></td></tr></table>";
				$("#lists").append(content);
				$("#lists").on("click","#friendnumber"+i,function(){showfriend($(this).attr("friendnumber"))});
			}
		}
	}
	
	function newjournal_user(){
		var content = "<br/>标题：<textarea id='newjournaltitle' cols='80' rows='1'></textarea><br/>内容：<textarea id='newjournalcontent' style='vertical-align:top' cols='90' rows='30'></textarea><br/><br/>&nbsp;&nbsp;&nbsp;<a href='#' id='establishjournal'>新建</a>&nbsp;<a href='#' id='returnoriginal'>返回</a>";
		$("#lists").empty();
		$("#lists").append(content);
		$("#lists").on("click","#establishjournal",function(){establishjournal($("#newjournaltitle").val(),$("#newjournalcontent").val())});
		$("#lists").on("click","#returnoriginal",function(){$("#lists").empty()});
	}
	
	function alterinformation(){
		var content = "<br/>账号：<input id='username' value='" + username + "'/><br/><br/>密码：<input id='password' value='" + password + "'/></br></br>年龄：<input id='age' value='" + age + "'/></br></br><a href='#' id='informationupdate'>保存</a>&nbsp;&nbsp;&nbsp;<a href='#' id='returnuserinformation_user'>返回</a>";
		$("#lists").empty();
		$("#lists").append(content);
		$("#lists").on("click","#informationupdate",function(){informationupdate($("#username").val(),$("#password").val(),$("#age").val())});
		$("#lists").on("click","#returnuserinformation_user",function(){userinformation_user()});
	}
	
	function informationupdate(username,password,age){
		/*既然要刷新页面，也可以直接提交表单，不需要异步提交数据*/
		$.post("updateinformationAction", 
				{
					userselfname : $("#usernameData").val(),
					username : username,
					password : password,
					age : age
				},
				function(returnedData, status) {
					alert("信息保存成功");
					window.location.reload();
					/*页面刷新方法的后面的函数不会执行，暂时这里没找到什么好的办法可以直接按显示新的用户信息
					 * 这里加入一个userselfname是为了可以更改账号，后台得到对应的user对象时是通过userselfname而不是输入框中的username（问题是账号改了以后session中的username没有变，刷新页面会出错,所以最后功能实现还是不要修改账号了，后期会加上昵称属性——如果老子还想做的话）。
					 */
					
			});
			
	}
	
	function showjournalcontent(journalnumber){
		var content = "<br/><h1>" + journaltitles[journalnumber] + "</h1><p>" + journalcontents[journalnumber] +"</p><a href='#' id='returnjournalslist_user'>返回</a>&nbsp;<a href='#' id='editjournal'>编辑</a>&nbsp;<a href='#' id='deletejournal'>删除</a>";
		$("#lists").empty();
		$("#lists").append(content);
		$("#lists").on("click","#returnjournalslist_user",function(){journalslist_user()});
		$("#lists").on("click","#editjournal",function(){journaledit(journaltitles[journalnumber],journalcontents[journalnumber],journalnumber)});
		$("#lists").on("click","#deletejournal",function(){ deletejournal(journaltitles[journalnumber])});
	}
	
	function showfriend(friendnumber){
		alert(friendnumber);
	}
	
	function journaledit(journaltitle,journalcontent,journalnumber){
		var content = "<br/>标题：<textarea id='journaltitle' cols='80' rows='1'>" + journaltitle + "</textarea><br/>内容：<textarea id='journalcontent' style='vertical-align:top' cols='90' rows='30'>" + journalcontent + "</textarea><br/><br/>&nbsp;&nbsp;&nbsp;<a href='#' id='updatejournal'>保存</a>&nbsp;<a href='#' id='returnshowjournalcontent'>返回</a>";
		$("#lists").empty();
		$("#lists").append(content);
		$("#lists").on("click","#updatejournal",function(){updatejournal(journaltitle,$("#journaltitle").val(),$("#journalcontent").val())});
		$("#lists").on("click","#returnshowjournalcontent",function(){showjournalcontent(journalnumber)});
	}
	
	function deletejournal(originaltitle){
		$.post("deletejournalAction", 
				{
					originaltitle : originaltitle,
				},
				function(returnedData, status) {
					alert("日志删除成功");
					window.location.reload();
					//页面刷新方法的后面的函数不会执行，暂时这里没找到什么好的办法可以直接按显示新的用户信息
					
			});
	}
	
	function updatejournal(originaltitle,journaltitle,journalcontent){
		$.post("updatejournalAction", 
				{
					originaltitle : originaltitle,
					title : journaltitle,
					content : journalcontent
				},
				function(returnedData, status) {
					alert("日志内容保存成功");
					window.location.reload();
					//页面刷新方法的后面的函数不会执行，暂时这里没找到什么好的办法可以直接按显示新的用户信息
					
			});
	}
	
	function establishjournal(newjournaltitle,newjournalcontent){
		if(newjournaltitle.replace(/(^\s*)|(\s*$)/g, "") ==""){
			alert("标题不能为空");
		}else if(newjournalcontent.replace(/(^\s*)|(\s*$)/g, "") ==""){
			alert("内容不能为空");
		}else{
			addnewjournal(newjournaltitle,newjournalcontent);
		}
	}
	
	function addnewjournal(newjournaltitle,newjournalcontent){
		$.post("addjournalAction", 
		{
			userselfname : $("#usernameData").val(),
			title : newjournaltitle,
			content : newjournalcontent
		},
		function(returnedData, status) {
			alert("日志内容保存成功");
			window.location.reload();
			//页面刷新方法的后面的函数不会执行，暂时这里没找到什么好的办法可以直接按显示新的用户信息
			
		});
	}
	
	function userinformation_administrator(){
		var content = "<br/>账号：" + username + "<br/><br/>密码：" + password + "<br/><br/>年龄：" + age + "<br/><br/>权限：" + competence;
		$("#lists").empty();
		$("#lists").append(content);
	}
	
	function journalslist_administrator(){
		$("#lists").empty();
		var content = "<br/><table><tr><th>序号</th><th>日志标题</th><th>日志详情</th></tr>";
		for(var i=0;i<size;i++){
			content += "<tr><td>" + (i+1) + "</td><td>" + journaltitles[i] + "</td><td><a href='#' journalnumber='" + i + "'>日志内容</a></td></tr>";
		}
		content += "</table>";
		$("#lists").append(content);
		/*jquery的on方法不能直接绑定自定义的function，好像只能这样*/
		$("#lists").on("click","a",function(){inqueryjournalcontent($(this).attr("journalnumber"))});
	}
	
	function friendslist_administrator(){
		$("#lists").empty();
		$("#lists").append("1234");
	}
	
	function findfriend_administrator(){
		$("#lists").empty();
		var friendusername = $("#friendusername").val();
		$("#lists").append(friendusername);
	}
	
	function inqueryjournalcontent(journalnumber){
		var content = "<br/><h1>" + journaltitles[journalnumber] + "</h1><p>" + journalcontents[journalnumber] +"</p><a href='#' id='returnjournalslist_administrator'>返回</a>";
		$("#lists").empty();
		$("#lists").append(content);
		$("#lists").on("click","#returnjournalslist_administrator",function(){journalslist_administrator()});
	}
});
