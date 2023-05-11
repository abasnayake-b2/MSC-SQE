package com.rms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name= "roles")
public class Role {
	
	
	public Role(long id) {
		this.id = id;
	}
	
	public Role(String roleName) {
		this.roleName = roleName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="role_name" , nullable = false, unique = true, length = 50)
	private String roleName;
	
	@Column(name="role_description")
	private String roledescription;
	
	
	@Override
	public String toString() {
		
		return this.roleName;
	}
}
