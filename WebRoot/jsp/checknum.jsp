<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.awt.Graphics"%>
<%@page import="java.awt.Color"%>
<%@page import="java.awt.Font"%>
<%@page import="com.sun.org.apache.commons.digester.rss.Image"%>
<%@page import="javax.imageio.ImageIO"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'checknum.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%!
	Color getRandColor(int fc,int bc){	//给定范围获取随机颜色
		Random random = new Random();
		if(fc>255) fc = 255;
		if(bc>255) bc = 255;
		int r = fc+random.nextInt(bc-fc);
		int g = fc+random.nextInt(bc-fc);
		int b = fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
		}
		
	 %>
	<%
	response.setHeader("Pragma","No-cache");	//设置页面不缓存
	response.setHeader("Cache-control","no-cache");
	response.setDateHeader("Expires",0);
	int width = 80, height = 30;
	BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	Graphics g = image.getGraphics();	//获取图形上下文
	Random random = new Random();	//生成随机对象
	g.setColor(getRandColor(200,250));	//设定背景颜色
	g.fillRect(0,0,width,height);
	g.setFont(new Font("Times New Roman",Font.PLAIN,18));	//设定字体
	//随机产生155条干扰线，使图像中的验证码不易被其他程序探测到
	g.setColor(getRandColor(160,200));
	for(int i=0;i<155;i++)
	{
		int x1 = random.nextInt(width);
		int y1 = random.nextInt(height);
		int x = random.nextInt(12);
		int y = random.nextInt(12);
		g.drawLine(x1,y1,x1+x,y1+y);
	}
	//随机产生的验证码（4位数组）
	String sRand = "";
	for(int i=0;i<4;i++)
	{
		String rand = String.valueOf(random.nextInt(10));
		sRand += rand;
		//将验证码显示到图像中
		g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
		//设置显示随机字符的颜色
		g.drawString(rand,13*i+6,16);
	}
	session.setAttribute("rand",sRand);	//将验证码存入session
	g.dispose();	//图像生效
	//输出图像到页面
	ImageIO.write(image,"JPEG",response.getOutputStream());
	out.clear();
	out = pageContext.pushBody();
	 %>


  </head>
  
  <body>
    
  </body>
</html>
