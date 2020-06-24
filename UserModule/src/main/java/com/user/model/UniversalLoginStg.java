package com.user.model;


import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Entity
@Table(name = "universal_login_stg")
@Data
@ConfigurationProperties("app")
public class UniversalLoginStg {
	
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long stgUserId;
private String firstName;
private String lastName;
private String primaryMobile;
private String primaryEmail;
private String password;
private Date addDate;
private int tokenNo;
private String status;

public long getStgUserId() {
	return stgUserId;
}
public void setStgUserId(long stgUserId) {
	this.stgUserId = stgUserId;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getPrimaryEmail() {
	return primaryEmail;
}
public void setPrimaryEmail(String primaryEmail) {
	this.primaryEmail = primaryEmail;
}

public String getMobile() {
	return primaryMobile;
}
public void setMobile(String mobile) {
	this.primaryMobile = mobile;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Date getAddDate() {
	return addDate;
}
public void setAddDate(Date addDate) {
	this.addDate = addDate;
}
public int getTokenNo() {
	return tokenNo;
}
public void setTokenNo(int tokenNo) {
	this.tokenNo = tokenNo;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

public int generateTokenNo(){
Random random = new Random();
int tokenNo = 100000 + random.nextInt(900000);
return tokenNo;
 }

public UniversalLoginStg(long stgUserId, String firstName, String lastName, String primaryMobile, String primaryEmail,
		String passwordHash, Date addDate, long tokenNo, String status) {
	super();
	this.stgUserId = stgUserId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.primaryMobile = primaryMobile;
	this.primaryEmail = primaryEmail;
	this.password = passwordHash;
	this.addDate =  new Date();
	this.tokenNo =  generateTokenNo();
	this.status = status;
}

public UniversalLoginStg(UniversalLoginStg universalLoginStg) {
	this.addDate =  new Date();
	this.tokenNo = generateTokenNo();
	this.status	 = "P";
}

}
