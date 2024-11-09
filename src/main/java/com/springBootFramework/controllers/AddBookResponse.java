package com.springBootFramework.controllers;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AddBookResponse {
	@Id
	private String id;
	private String msg;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}	
}
