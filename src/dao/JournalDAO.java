package com.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.domain.Journal;


public class JournalDAO extends HibernateDaoSupport{

	//添加方法
	public void add(Journal j){
		this.getHibernateTemplate().save(j);
	}
	
	//删除方法
	public void delete(Journal j){
		this.getHibernateTemplate().delete(j);
	}
		
	//修改方法
	public void update(Journal j){
		this.getHibernateTemplate().update(j);
	}
	
	//按id查询实体（get是立即加载，load是延迟加载，会出现no session问题，load和get方法只能是通过主键查询）
	public Journal getById(Integer id){
		return this.getHibernateTemplate().load(Journal.class, id);
	}
	
	//按username查询实体（get是立即加载，load是延迟加载，会出现no session问题）
	public Journal getByTitle(String title){
		return (Journal) this.getHibernateTemplate().find("from Journal j where j.title=?",title).get(0);
	}
	
	//返回全部数据集合
	public List<Journal> getAll(){
		return this.getHibernateTemplate().find("from Journal");
	}
	
	//返回分页集合数据
	public List<Journal> getAll(int pageNum,int prePageNum){
		DetachedCriteria dc = DetachedCriteria.forClass(Journal.class);
		return this.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*prePageNum,prePageNum);
	}
	
	

}
