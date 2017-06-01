package com.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.domain.User;

public class UserDAO extends HibernateDaoSupport{

	public void show(User u) {
		System.out.println("dao层运行");
		this.getHibernateTemplate().save(u);
	}
	
	//添加方法
	public void add(User u){
		this.getHibernateTemplate().save(u);
	}
	
	//删除方法
	public void delete(User u){
		this.getHibernateTemplate().delete(u);
	}
		
	//修改方法
	public void update(User u){
		this.getHibernateTemplate().update(u);
	}
	
	//按id查询实体（get是立即加载，load是延迟加载，会出现no session问题，load和get方法只能是通过主键查询）
	public User getById(Integer id){
		return this.getHibernateTemplate().load(User.class, id);
	}
	
	//按username查询实体（get是立即加载，load是延迟加载，会出现no session问题）
	public User getByUsername(String username){
		return (User) this.getHibernateTemplate().find("from User u where u.username=?",username).get(0);
	}
	
	//返回全部数据集合
	public List<User> getAll(){
		return this.getHibernateTemplate().find("from User");
	}
	
	//返回分页集合数据
	public List<User> getAll(int pageNum,int prePageNum){
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		return this.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*prePageNum,prePageNum);
	}
	
	

}
