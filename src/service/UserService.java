package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import com.dao.UserDAO;
import com.domain.Journal;
import com.domain.User;

//HibernateDaoSupport默认为事务，需要在service层加载事务
@Transactional
public class UserService {
	private UserDAO userDAO;

	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	public void add(User u){
		userDAO.add(u);
	}
	
	public User getById(Integer id){
		User u = userDAO.getById(id);
		//把数据初始化，变成立即加载对象
		Hibernate.initialize(u);
		
		return u;
	}
	public void update(User u){
		userDAO.update(u);
	}
	
	public User getByUsername(String username){
		User u = userDAO.getByUsername(username);
		//把数据初始化，变成立即加载对象
		Hibernate.initialize(u);
		
		return u;
	}
	
	public List<User> getAll(){
		List<User> u = userDAO.getAll();
		//Hibernate.initialize(u);
		return u;
	}
	
	public void getuserinformation(String username) throws IOException{
		User u = userDAO.getByUsername(username);
		// 异步调用
		Document document = DocumentHelper.createDocument();

		Element e = document.addElement("user");

		Element usernameElement = e.addElement("username");
		Element passwordElement = e.addElement("password");
		Element ageElement = e.addElement("age");
		Element competenceElement = e.addElement("competence");

		usernameElement.setText(u.getUsername());
		passwordElement.setText(u.getPassword());
		ageElement.setText(u.getAge().toString());
		competenceElement.setText(u.getCompetence().toString());

		response(document);
	}
	
	public void getallusers() throws IOException{
		//异步调用
		Document document = DocumentHelper.createDocument();
		
		Element rootElement = document.addElement("users");
		Element size = rootElement.addElement("size");
		
		rootElement.addComment("This is comment!!");
		
		List<User> users = userDAO.getAll();
		size.setText(users.size()+"");
		for(int i=0;i<users.size();i++)
		{
			
			Element e = rootElement.addElement("user"+i);
			
			Element usernameElement = e.addElement("username");
			Element passwordElement = e.addElement("password");
			Element competenceElement = e.addElement("competence");
			
			usernameElement.setText(users.get(i).getUsername().toString());
			passwordElement.setText(users.get(i).getPassword().toString());
			competenceElement.setText(users.get(i).getCompetence().toString());
			
		}
		
		
		response(document);
	}
	
	public void loginverify(String username,String password) throws IOException{
		//异步调用
		Document document = DocumentHelper.createDocument();
		
		Element rootElement = document.addElement("users");
		
		List<User> users = userDAO.getAll();
		Element e = rootElement.addElement("user");
		
			for(int i=0;i<users.size();i++)
			{
				if(users.get(i).getUsername().equals(username))
				{
					if(users.get(i).getPassword().equals(password))
					{
						e.setText("success");
						break;
					}else{
						e.setText("passworderror");
						break;
					}
				}else{
					e.setText("usernameerror");
				}
			}
		
			response(document);
	}
	
	public String login(String username){
		Integer competence = userDAO.getByUsername(username).getCompetence();
		if(competence == 1)
		{
			return "administrator";
		}else if(competence == 0){
			HttpServletRequest request = ((ServletActionContext) RequestContextHolder.getRequestAttributes()).getRequest();  
			request.setAttribute("userselfname",username);
			request.setAttribute("visitor","userself");
			return "user";
		}else{
			return "error";
		}
	}
	
	public void getalljournals(String username) throws IOException{
		//设置的变量为每一个用户的日志数目
		int maxid = 0;
		//异步调用
		Document document = DocumentHelper.createDocument();
		
		Element rootElement = document.addElement("journals");
		Element size = rootElement.addElement("maxid");
		
		User user  = userDAO.getByUsername(username);
		Set<Journal> journals = user.getJournals();
		
		for(Journal journal : journals)
		{
			//这里不能用主键id作为排列序号，因为删除以后id会出现空缺，
			int journalid = journal.getId() - 1;
			
			Element e = rootElement.addElement("journal"+journalid);
				
			Element journaltitleElement = e.addElement("journaltitle");
			Element journalcontentElement = e.addElement("journalcontent");
			
			journaltitleElement.setText(journal.getTitle());
			journalcontentElement.setText(journal.getContent());
				
			maxid = (maxid > journalid)?(maxid):(journalid);
				
		}
		maxid += 1;
		size.setText(maxid+"");
		
		response(document);
	}
	
	public void getfriendslist(String username) throws IOException{
		//设置的变量为每一个用户的日志数目
		int maxid = 0;
		//异步调用
		Document document = DocumentHelper.createDocument();
		
		Element rootElement = document.addElement("friends");
		Element size = rootElement.addElement("friendmaxid");
		
		User user  = userDAO.getByUsername(username);
		Set<User> friends = user.getUsers();
		
		for(User friend : friends)
		{
			//这里不能用主键id作为排列序号，因为删除以后id会出现空缺，
			int friendid = friend.getId() - 1;
			Element e = rootElement.addElement("friend"+friendid);
			
			//这里是观看好友信息，不可能给出好友的密码
			Element usernameElement = e.addElement("friendusername");
			Element ageElement = e.addElement("friendage");
			
			usernameElement.setText(friend.getUsername());
			ageElement.setText(friend.getAge().toString());
				
			maxid = (maxid > friendid)?(maxid):(friendid);
				
		}
		maxid += 1;
		size.setText(maxid+"");
		response(document);
	}
	
	private void response(Document document) throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/xml; charset=utf-8");
		response.setHeader("cache-control", "no-cache");

		PrintWriter out = response.getWriter();

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");

		XMLWriter writer = new XMLWriter(out, format);

		writer.write(document);

		out.flush();
		out.close();
	}
	
}
