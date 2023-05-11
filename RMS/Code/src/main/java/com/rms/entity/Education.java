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
@Table(name= "education")
public class Education {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name="college")
	private String college;
	
	@Column(name="qualification")
	private String qualification;
	
		
	@Column(name="year")
	private String year;

	@Column(name="type")
	private String type;
	
	
	@Column(name="summary")
	private String summary;
	
	@Column(name="isReading")
	private String isReading;
	
}
