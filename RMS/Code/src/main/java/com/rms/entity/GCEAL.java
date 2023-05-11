package com.rms.entity;

import java.time.LocalDate;

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
@Table(name = "gceal")
public class GCEAL {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name="college")
	private String college;
	
		
	@Column(name="year")
	private String year;
	
	@Column(name="numberofpasses")
	private String numberofpasses;
	
	@Column(name="nop")
	private int nop;
	
	@Column(name="a")
	private String a;
	
	@Column(name="b")
	private String b;
	
	@Column(name="c")
	private String c;
	
	

}
