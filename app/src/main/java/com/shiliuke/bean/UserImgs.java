package com.shiliuke.bean;


public class UserImgs {

	public int id;
	public String name;
	public String urls;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrls() {
		return urls;
	}
	public void setUrls(String urls) {
		this.urls = urls;
	}

	public UserImgs(int id, String name, String urls) {
		this.id = id;
		this.name = name;
		this.urls = urls;
	}

	public UserImgs() {
	}
}
