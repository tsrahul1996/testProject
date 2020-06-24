package com.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserSignUp {
	
	@Id
	private int id ;
	private String username;
	private String status;
	private String onetimehash;
	
	public UserSignUp(int id, String username, String status, String onetimehash) {
		super();
		this.id = id;
		this.username = username;
		this.status = status;
		this.onetimehash = onetimehash;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOnetimehash() {
		return onetimehash;
	}
	public void setOnetimehash(String onetimehash) {
		this.onetimehash = onetimehash;
	}
	
}
