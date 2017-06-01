package com.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private Integer age;
	private Integer competence;
	//其实在这里没有必要设置onetomany或者manytomany的many的set的数据类型
	private Set journals = new HashSet(0);
	private Set users = new HashSet(0);

	// Constructors

	public Set getUsers() {
		return users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String username, String password, Integer age,
			Integer competence, Set journals, Set userUsersForUser1Id,
			Set userUsersForUser2Id) {
		this.username = username;
		this.password = password;
		this.age = age;
		this.competence = competence;
		this.journals = journals;

	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getCompetence() {
		return this.competence;
	}

	public void setCompetence(Integer competence) {
		this.competence = competence;
	}

	public Set getJournals() {
		return this.journals;
	}

	public void setJournals(Set journals) {
		this.journals = journals;
	}



}