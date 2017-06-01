package com.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import com.dao.JournalDAO;
import com.domain.Journal;
import com.domain.User;

//HibernateDaoSupport默认为事务，需要在service层加载事务
@Transactional
public class JournalService {
	private JournalDAO journalDAO;

	
	public void setJournalDAO(JournalDAO journalDAO) {
		this.journalDAO = journalDAO;
	}


	public void add(Journal j){
		journalDAO.add(j);
	}
	
	public Journal getById(Integer id){
		Journal j = journalDAO.getById(id);
		//把数据初始化，变成立即加载对象
		Hibernate.initialize(j);
		
		return j;
	}
	
	public Journal getByTitle(String journal){
		Journal j = journalDAO.getByTitle(journal);
		//把数据初始化，变成立即加载对象
		Hibernate.initialize(j);
		
		return j;
	}
	
	public List<Journal> getAll(){
		List<Journal> j = journalDAO.getAll();
		Hibernate.initialize(j);
		return j;
	}
	
	public void delete(Journal j){
		journalDAO.delete(j);
	}
	
	public void update(Journal j){
		journalDAO.update(j);
	}
}
