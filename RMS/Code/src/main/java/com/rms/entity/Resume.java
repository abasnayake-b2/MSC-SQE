package com.rms.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "resume")

public class Resume {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "userid")
	private  long userid;
	
	
	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;
	
	@Column(name = "teleno")
	private String teleno;

	@Column(name = "birthday")
	private String birthday;

	@Column(name = "gender")
	private String gender;

	@Column(name = "profession")
	private String profession;

	
	//years of Experience
	@Column(name = "yoe")
	private String yoe;

	
	@Column(name = "sector")
	private String sector;
	
	@Column(name = "elevel")
	private String elevel;
	

	
	@Column(name = "summary")
	private String summary;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "resum_education", joinColumns = @JoinColumn(name = "resum_id"), inverseJoinColumns = @JoinColumn(name = "edu_id"))
	List<Education> educations = new ArrayList<>();

	public void addEducation(Education education) {

		this.educations.add(education);
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "resum_job", joinColumns = @JoinColumn(name = "resum_id"), inverseJoinColumns = @JoinColumn(name = "job_id"))
	List<Job> jobs = new ArrayList<>();

	public void addJob(Job job) {

		this.jobs.add(job);
	}

	// @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "resume_skill", joinColumns = @JoinColumn(name = "resum_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private List<Skill> skills = new ArrayList<>();

	public void addSkills(Skill skill) {

		this.skills.add(skill);
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "resume_ol", joinColumns = @JoinColumn(name = "resum_id"), inverseJoinColumns = @JoinColumn(name = "ol_id"))
	private List<GCEOL> ols = new ArrayList<>();

	public void addOl(GCEOL ol) {

		this.ols.add(ol);
	}
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "resume_al", joinColumns = @JoinColumn(name = "resum_id"), inverseJoinColumns = @JoinColumn(name = "al_id"))
	private List<GCEAL> als = new ArrayList<>();

	public void addAl(GCEAL al) {

		this.als.add(al);
	}
	
//
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinTable(name = "resum_profq", joinColumns = @JoinColumn(name = "resum_id"), inverseJoinColumns = @JoinColumn(name = "profq_id"))
//	List<Professionalqualifications> professionalQualifications = new ArrayList<>();
//
//	public void addProfessionalQualifications(Professionalqualifications professionalQualifications) {
//
//		this.professionalQualifications.add(professionalQualifications);
//	}
}
