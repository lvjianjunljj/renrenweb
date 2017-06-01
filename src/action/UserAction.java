package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sun.print.resources.serviceui;

import com.domain.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.UserService;

public class UserAction extends ActionSupport implements ModelDriven<User> {
	
	private String userselfname;
	private String visitor;
	private String informationusername;
	
	public String getInformationusername() {
		return informationusername;
	}

	public void setInformationusername(String informationusername) {
		this.informationusername = informationusername;
	}

	public String getUserselfname() {
		return userselfname;
	}

	public void setUserselfname(String userselfname) {
		this.userselfname = userselfname;
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	private User u = new User();

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// ModelDriven<User>的默认方法，可以将表单提交的数据封装成已经写好的User对象，直接被当做User对象调用
	public User getModel() {

		return u;
	}

	@Override
	public String execute() {
		return SUCCESS;
	}

	public String login() throws Exception {
		// 打印user会自动调用user类中的toString方法
		// System.out.println("测试Action是否连通"+u);

		// Struts2使用Spring管理的Bean
		// 1、直接使用ApplicationContext的对象
		// 缺点：每个Action类中，每次使用都要加载配置文件
		/*
		 * ApplicationContext ctx = new
		 * ClassPathXmlApplicationContext("applicationContext.xml"); UserService
		 * service = (UserService) ctx.getBean("userService"); service.show();
		 * return SUCCESS;
		 */

		// 2、在WEB容器（服务器）启动时，将Spring的配置加载好，放置到WEB容器中，获取时从WEB容器中获取
		// 优点：实现了整个WEB容器共享配置文件的特性
		/*
		 * //2.1在web.xml文件中添加监听器 //2.2获取servletcontext对象 ServletContext sc =
		 * ServletActionContext.getServletContext();
		 * //2.3使用webapplicationcontext对象获取spring的bean对象（用一个工具类获取）
		 * //需要传递servletcontext对象（因为ApplicationContext加载好以后在servletcontext放置）
		 * WebApplicationContext wctx =
		 * WebApplicationContextUtils.getWebApplicationContext(sc); UserService
		 * service = (UserService) wctx.getBean("userService"); service.show();
		 */

		// 3、将需要操作的bean注入到Action中（使用struts2的自动装配模式加载bean）
		// 3.1将要使用的Bean提供给Struts2的Action,并提供对应的setter方法（自动装配按名字装配，名字必须一致）
		/*
		 * 优点：开发格式简单明了，符合Spring注入Bean的格式
		 * 缺点：此模式由struts2管理，而不是spring管理（仅仅是让spring帮忙注入一些东西）
		 * 
		 * userService.show();
		 */

		// 4、将Struts的Action交给Spring管理（需要配置表现层的bean）
		// 4.1将Action配置成Spring的Bean
		// 4.2在Struts2的核心配置文件中，action的class配置使用Spring中的Bean的名称(伪类名)
		// 一般都是用第四种方式
		// userService.show(u);

		return userService.login(u.getUsername());
	}

	public String register() {
		u.setCompetence(0);
		userService.add(u);
		return SUCCESS;
	}

	public void loginVerify() throws Exception {
		userService.loginverify(u.getUsername(), u.getPassword());
	}

	public void getallusers() throws Exception {
		userService.getallusers();
	}
	
	public String inquery(){
		HttpServletRequest request = ((ServletActionContext) RequestContextHolder.getRequestAttributes()).getRequest();  
		request.setAttribute("userselfname",userselfname);
		request.setAttribute("visitor",visitor);
		return SUCCESS;
	}
	
	public void getuserinformation() throws Exception{
		userService.getuserinformation(informationusername);
	}
	
	public void getalljournals() throws Exception{
		userService.getalljournals(informationusername);
	}
	
	public void updateinformation(){
		//DAO层封装的更新数据方法是getHibernateTemplate的自带update方法，其参数u的各个private属性必须设置内容，而getModel方法得到的u只设置了jsp中传过来的属性值
		String newusername = u.getUsername();
		String newpassword = u.getPassword();
		Integer newage = u.getAge();
		u = userService.getByUsername(userselfname);
		u.setUsername(newusername);
		u.setPassword(newpassword);
		u.setAge(newage);
		userService.update(u);
	}
	
	public void getfriendslist() throws Exception{
		userService.getfriendslist(informationusername);
	}

}
