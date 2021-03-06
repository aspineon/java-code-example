package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OcUser generated by hbm2java
 */
@Entity
@Table(name = "oc_user", catalog = "opencart")
public class User implements java.io.Serializable {

	private Integer userId;
	private int userGroupId;
	private String username;
	private String password;
	private String salt;
	private String firstname;
	private String lastname;
	private String email;
	private String code;
	private String ip;
	private boolean status;
	private Date dateAdded;

	public User() {
	}

	public User(int userGroupId, String username, String password,
			String salt, String firstname, String lastname, String email,
			String code, String ip, boolean status, Date dateAdded) {
		this.userGroupId = userGroupId;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.code = code;
		this.ip = ip;
		this.status = status;
		this.dateAdded = dateAdded;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_group_id", nullable = false)
	public int getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	@Column(name = "username", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 40)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "salt", nullable = false, length = 9)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "firstname", nullable = false, length = 32)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "lastname", nullable = false, length = 32)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "email", nullable = false, length = 96)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "code", nullable = false, length = 40)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "ip", nullable = false, length = 40)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_added", nullable = false, length = 19)
	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

}
