package com.domain;

/**
 * Journal entity. @author MyEclipse Persistence Tools
 */

public class Journal implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String title;
	private String content;

	// Constructors

	/** default constructor */
	public Journal() {
	}

	/** minimal constructor */
	public Journal(User user) {
		this.user = user;
	}

	/** full constructor */
	public Journal(User user, String title, String content) {
		this.user = user;
		this.title = title;
		this.content = content;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}