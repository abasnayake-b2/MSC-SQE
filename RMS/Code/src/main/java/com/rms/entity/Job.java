package com.rms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "job")
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "company")
	private String company;

	@Column(name = "designation")
	private String designation;

	@Column(name = "startDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@Column(name = "endDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	@Column(name = "isCurrentJob")
	private boolean isCurrentJob;
	
	@Column(name = "summary")
	private String summary;
	
}
