package com.rms.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "User_First_Name", nullable = false, unique = true, length = 50)
	private String firstName;

	@Column(name = "User_Last_Name", nullable = false, unique = true, length = 50)
	private String lastName;

	@Column(name = "User_Name", nullable = false, unique = true, length = 20)
	private String userName;

	@Column(name = "User_Password", nullable = false, length = 64)
	private String password;

	@Column(name = "email_Id", nullable = false, unique = true, length = 50)
	private String emailId;

	@Column(name = "User_Status")
	private String status;

	@Column(name = "User_Create_by")
	private String createdBy;

	@Column(name = "User_Gender")
	private String gender;

	@Column(name = "User_Created_Date")
	private Date createdDate;

	@Column(name = "User_Last_updated_by")
	private String lastUpdatedBy;

	@Column(name = "User_Last_updated_Date")
	private Date lastUpdatedDate;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public void addRole(Role role) {

		this.roles.add(role);

	}

}
