package com.action;



import com.domain.Journal;
import com.domain.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.JournalService;
import com.service.UserService;

public class JournalAction extends ActionSupport implements ModelDriven<Journal> {
	
	private String originaltitle;
	private String userselfname;
	
	public String getUserselfname() {
		return userselfname;
	}

	public void setUserselfname(String userselfname) {
		this.userselfname = userselfname;
	}

	public String getOriginaltitle() {
		return originaltitle;
	}

	public void setOriginaltitle(String originaltitle) {
		this.originaltitle = originaltitle;
	}

	private Journal j = new Journal();

	private JournalService journalService;
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setJournalService(JournalService journalService) {
		this.journalService = journalService;
	}

	public Journal getModel() {

		return j;
	}

	@Override
	public String execute() throws Exception {
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

		// System.out.println(userService.getById(1));
		return SUCCESS;
	}
	
	public void journal(){
		
	}
	
	public void deletejournal(){
		Journal j = journalService.getByTitle(originaltitle);
		journalService.delete(j);
	}
	
	public void addjournal(){
		User u = userService.getByUsername(userselfname);
		j.setUser(u);
		journalService.add(j);
	}
	
	public void updatejournal(){
		String newtitle = j.getTitle();
		String newcontent = j.getContent();
		Journal j =journalService.getByTitle(originaltitle);
		j.setTitle(newtitle);
		j.setContent(newcontent);
		journalService.update(j);
	}
	
	

}
