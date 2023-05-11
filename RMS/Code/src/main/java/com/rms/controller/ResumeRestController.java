package com.rms.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rms.dto.SearchCriterion;
import com.rms.entity.Education;
import com.rms.entity.EducationLevel;
import com.rms.entity.GCEAL;
import com.rms.entity.GCEOL;
import com.rms.entity.Job;
import com.rms.entity.JobSector;
import com.rms.entity.NumberOfPasses;
import com.rms.entity.Resume;
import com.rms.entity.Skill;
import com.rms.entity.SkillLevel;
import com.rms.entity.User;
import com.rms.repository.ResumeRepository;
import com.rms.repository.SkillLevelRepository;
import com.rms.repository.UserRepository;
import com.rms.service.ResumeService;

import com.rms.utils.FormData;
import com.rms.utils.PdfGenaratorUtil;
import com.rms.utils.ResumeSpecifications;

@RestController
@RequestMapping("/resume")
@CrossOrigin
public class ResumeRestController {
	
	// autowired beans
	
	@Autowired
	private ResumeService resumeService;

	@Autowired
	private UserRepository repo;

	@Autowired
	private ResumeRepository rrepo;

	@Autowired
	private SkillLevelRepository sklrepo;

	@Autowired

	private PdfGenaratorUtil pdfGenaratorUtil;

	// instantiate form data to populate form drop downs
	FormData formData = new FormData();

	
	//Get List of resume without filter Criterion
	@GetMapping("/list_resume")
	public ModelAndView viewResumeList(Model model) {

		List<Resume> listResume = resumeService.findAll();
		model.addAttribute("listResume", listResume);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/resume/resumelist");
		return modelAndView;

	}

	
	
	// get user resume
	@GetMapping("/getmyresume")
	public ModelAndView getMyResum(Model model, Principal principal) {
		Resume resume = null;
		String flag ="Y";
		String userId = principal.getName();
		User user = repo.findByEmail(userId);

		try {

			resume = resumeService.getResumeByUserID(user.getId());
			if (resume == null) {
				
				flag="N";
			}
			model.addAttribute("flag", flag);
			model.addAttribute("listResume", resume);

		} catch (Exception e) {

		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/resume/myresume");
		return modelAndView;
	}
	
	// get All Resume
	@GetMapping("/getresumebyid/{id}")
	public Resume getResumeByID(@PathVariable Long id) {

		return resumeService.getResumeByID(id);
	}

	// Save  Resume
	@PostMapping("/saveResume")
	public ModelAndView saveResume(Resume resume) {
		List<Skill> listSkill = resume.getSkills();
		List<Job> listJob = resume.getJobs();
		List<Education> listEducation = resume.getEducations();
		List<GCEOL> listOls = resume.getOls();
		List<GCEAL> listAls = resume.getAls();

		for (int i = 0; i < listSkill.size(); i++) {

			if ("".equals(listSkill.get(i).getSkill())) {
				listSkill.remove(i);
			}
		}

		for (int i = 0; i < listJob.size(); i++) {

			if ("".equals(listJob.get(i).getCompany())) {
				listJob.remove(i);
			}
		}

		for (int i = 0; i < listEducation.size(); i++) {

			if ("".equals(listEducation.get(i).getCollege())) {
				listEducation.remove(i);
			}
		}
		for (int i = 0; i < listOls.size(); i++) {

			if ("".equals(listOls.get(i).getCollege())) {
				listOls.remove(i);
			}
		}

		for (int i = 0; i < listAls.size(); i++) {

			if ("".equals(listAls.get(i).getCollege())) {
				listAls.remove(i);
			}
		}
		resume.setSkills(listSkill);
		resume.setJobs(listJob);
		resume.setEducations(listEducation);
		resume.setOls(listOls);
		resume.setAls(listAls);

		resumeService.save(resume);
		return new ModelAndView("redirect:/resume/createResume");
	}
	
	
	// Create resume
	@GetMapping("/createResume")
	public ModelAndView getResumNewFile(Model model, Principal principal) {
		Resume resume = null;
		String userId = principal.getName();
		User user = repo.findByEmail(userId);

		try {

			resume = resumeService.getResumeByUserID(user.getId());
			// setting up new resume for the user
			if (resume == null) {
				resume = new Resume();
				resume.setName(user.getFirstName() + " " + user.getLastName());
				resume.setEmail(user.getEmailId());
				resume.setUserid(user.getId());
				resume = resumeService.save(resume);

			}

			// set defult values to populate form
			resume.getSkills().add(new Skill());
			resume.getJobs().add(new Job());
			resume.getEducations().add(new Education());

			// to restrict the one input option
			if (resume.getOls().size() < 1)
				resume.getOls().add(new GCEOL());
			if (resume.getAls().size() < 1)
				resume.getAls().add(new GCEAL());

			model.addAttribute("resume", resume);

		} catch (Exception e) {

		}

		// setting Skill levels
		List<SkillLevel> skillLevels = new FormData().getSkillLevels();
		model.addAttribute("skillLevels", skillLevels);
		// setting Education levels
		List<EducationLevel> educationLevels = new FormData().getEducationLevels();
		model.addAttribute("educationLevels", educationLevels);
		// setting Job sectors
		List<JobSector> jobSectors = new FormData().getJobSector();
		model.addAttribute("jobSectors", jobSectors);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/resume/resumecreation");
		return modelAndView;
	}
	
	// Delete job, education and skill from the list by Index in the list of resume list window
	@GetMapping("/delete")
	public ModelAndView delete(Model model, Principal principal, @RequestParam String type, @RequestParam int index) {
		Resume resume = null;
		String userId = principal.getName();
		User user = repo.findByEmail(userId);

		try {

			resume = resumeService.getResumeByUserID(user.getId());
		} catch (Exception e) {

		}

		if ("job".equals(type)) {
			resume.getJobs().remove(index);
		} else if ("education".equals(type)) {
			resume.getEducations().remove(index);
		} else if ("skill".equals(type)) {
			resume.getSkills().remove(index);
		}

		resumeService.save(resume);
		return new ModelAndView("redirect:/resume/createResume");

	}

	// Delete Resume by Id
	@GetMapping("/deleteResume/{id}")
	public ModelAndView deleteResume(@PathVariable Long id) {
		resumeService.deleteResumeByID(id);
		return new ModelAndView("redirect:/resume/list_resume");
	}

	
	// Delete my Resume by Id
	@GetMapping("/deletemyresume/{id}")
	public ModelAndView deleteMyResume(@PathVariable Long id) {
		resumeService.deleteResumeByID(id);
		return new ModelAndView("redirect:/resume/getmyresume");
	}
//	// Delete search Resume by Id
//	@GetMapping("/deletemyresume/{id}")
//	public ModelAndView deleteSearchResume(@PathVariable Long id) {
//		resumeService.deleteResumeByID(id);
//		return new ModelAndView("redirect:/resume/search_resume");
//	}

	
	// Edit Resume by ID
	@GetMapping("/editResume/{id}")
	public ModelAndView getResumCreationForm(Model model, @PathVariable Long id) {
		Resume resume = resumeService.getResumeByID(id);
		model.addAttribute("resume", resume);

		// model.addAttribute("listRoles", userService.getRoles());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/resume/resumecreation");
		return modelAndView;
	}

	// View Resume by ID
	@GetMapping("/viewResume/{id}")
	public ModelAndView viewResumeByID(Model model, @PathVariable Long id) {
		Resume resume = null;
		resume = resumeService.getResumeByID(id);
		model.addAttribute("resume", resume);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/resume/resumeview");
		return modelAndView;
	}

	
	// Create Resume PDF by ID
	@GetMapping("createpdf/{id}")
	public ResponseEntity getResumPdf(@PathVariable Long id) {

		Resume resume = null;

		try {

			resume = resumeService.getResumeByID(id);
			if (resume == null)

				throw new Exception("resume not present");
		} catch (Exception e) {

		}

		Map<String, Object> resumeMap = new HashMap<>();

		resumeMap.put("ID", id);
		resumeMap.put("resume", resume);

		Resource resource = null;

		try {

			String property = "java.io.tmpdir";

			String tempDir = System.getProperty(property);
			System.out.println("tempDir-" + tempDir);

			String fileNameUrl = pdfGenaratorUtil.createPdf("resumepdf", resumeMap);
			// String fileNameUrl = pdfGenaratorUtil.createPdf("studant", studentMap);

			Path path = Paths.get(tempDir + "/" + fileNameUrl);

			resource = new UrlResource(path.toUri());

		} catch (Exception e) {

			e.printStackTrace();

		}

		return ResponseEntity.ok()

				.contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))

				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resume.getName() + ".pdf" + "\"")

				.body(resource);

	}

	
	//Search Resume with Criterion
	@GetMapping("/search_resume")
	public ModelAndView viewSearchResumeList(Model model, SearchCriterion searchCriterion) {

		String sector = null;
		String elevel = null;
		String skill = null;
		String designation = null;
		String profQaualification = null;
		String numberOfOLPasses = null;
		int olpasses = 0;
		String numberOfALPasses = null;
		int alpasses = 0;

		
		//get Job Sector
		sector = searchCriterion.getSjobSector();
		if (sector != null && sector.equals("*{sjobSector}")) {
			sector = null;
			searchCriterion.setSjobSector(null);
		}

		//Get Education Level
		elevel = searchCriterion.getSeducationLevel();
		if (elevel != null && elevel.equals("*{seducationLevel}")) {
			elevel = null;
			searchCriterion.setSeducationLevel(null);
		}

		
		//Get number of OL passes
		numberOfOLPasses = searchCriterion.getSnumberOfOLPasses();
		if (numberOfOLPasses != null && !numberOfOLPasses.equals("*{snumberOfOLPasses}")) {

			olpasses = Integer.valueOf(numberOfOLPasses);

		} else if (numberOfOLPasses != null && numberOfOLPasses.equals("*{snumberOfOLPasses}")) {
			olpasses=0;
			numberOfOLPasses = null;
			searchCriterion.setSnumberOfOLPasses(null);
		}

		
		//Get number of AL passes
		numberOfALPasses = searchCriterion.getSnumberOfALPasses();
		if (numberOfALPasses != null && !numberOfALPasses.equals("*{snumberOfALPasses}")) {

			alpasses = Integer.valueOf(numberOfALPasses);

		} else if (numberOfALPasses != null && numberOfALPasses.equals("*{snumberOfALPasses}")) {
			alpasses=0;
			numberOfALPasses = null;
			searchCriterion.setSnumberOfALPasses(null);
		}
		
		
		//Get  Professional Qualification	
		profQaualification = searchCriterion.getSprofQaualification();
		if ((profQaualification != null && profQaualification.equals("*{sdesignation}"))
				|| (profQaualification != null && profQaualification.equals(""))) {
			profQaualification = null;
			searchCriterion.setSprofQaualification(null);
		}

		
		//Get Job /experience/designation
		designation = searchCriterion.getSdesignation();
		if ((designation != null && designation.equals("*{sdesignation}"))
				|| (designation != null && designation.equals(""))) {
			designation = null;
			searchCriterion.setSdesignation(null);
		}

		
		//Get Skill
		skill = searchCriterion.getSskill();
		if ((skill != null && skill.equals("*{sskill}")) || (skill != null && skill.equals(""))) {
			skill = null;
			searchCriterion.setSskill(null);
		}
		
	


		Specification<Resume> specification = Specification
				// sector from parameter
				.where(sector == null ? null : sectorContains(sector))
				// elevel from parameter
				.and(elevel == null ? null : elevelContains(elevel))
				// job from parameter
				.and(designation == null ? null : jobContains(designation))
				// Profesional fQaualification from parameter
				.and(profQaualification == null ? null : profQaualificationContains(profQaualification))
				// number Of OL Passes from parameter
				.and(olpasses == 0 ? null : numberOfOLPassesContainsIn(olpasses))
				// number Of OL Passes from parameter
				.and(alpasses == 0 ? null : numberOfALPassesContainsIn(alpasses))
				// skill from parameter
				.and(skill == null ? null : skillContains(skill));

		List<Resume> listResume = rrepo.findAll(specification);

		// setting Education levels
		List<EducationLevel> educationLevels = new FormData().getEducationLevels();
		model.addAttribute("educationLevels", educationLevels);
		// setting Job sectors
		List<JobSector> jobSectors = new FormData().getJobSector();
		model.addAttribute("jobSectors", jobSectors);

		model.addAttribute("searchCriterion", searchCriterion);

		// setting NumberOf OL Passes
		List<NumberOfPasses> numberofOlPasses = new FormData().getOlPasses();
		model.addAttribute("numberofOlPasses", numberofOlPasses);
		// setting Number Of ALPasses
		List<NumberOfPasses> numberofAlPasses = new FormData().getAlPasses();
		model.addAttribute("numberofAlPasses", numberofAlPasses);

		model.addAttribute("listResume", listResume);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/resume/searchresumelist");
		return modelAndView;

	}
//	  public static Specification<Resume> hasSectorLike(String name) {
//	        return (root, query, criteriaBuilder) ->
//	          criteriaBuilder.like(root.<String>get("sector"), "%" + name + "%");
//	    }

//	    public static Specification<Resume> hasElevelLike(String name) {
//	        return (root, query, criteriaBuilder) ->
//	        criteriaBuilder.equal(root.<String>get("elevel"), name);
//	    }
//	    public static Specification<Resume> hasResumeWithSkill(String skill) {
//	        return (root, query, criteriaBuilder) -> {
//	            Join<Skill, Resume> resumeSkill = root.join("skills");
//	            return criteriaBuilder.equal(resumeSkill.get("skill"), skill);
//	        };
//	    }
//	    

	public static Specification<Resume> sectorContains(String expression) {
		return (root, query, builder) -> builder.like(root.get("sector"), contains(expression));
	}

	public static Specification<Resume> elevelContains(String expression) {
		return (root, query, builder) -> builder.like(root.get("elevel"), contains(expression));
	}

	public static Specification<Resume> skillContains(String expression) {
		return (root, query, builder) -> {
			Join<Skill, Resume> resumeSkill = root.join("skills");
			return builder.like(resumeSkill.get("skill"), contains(expression));

		};
	}

	public static Specification<Resume> jobContains(String expression) {
		return (root, query, builder) -> {
			Join<Job, Resume> resumeJob = root.join("jobs");
			return builder.like(resumeJob.get("designation"), contains(expression));

		};
	}

	public static Specification<Resume> profQaualificationContains(String expression) {
		return (root, query, builder) -> {
			Join<Education, Resume> resumeProfQaualification = root.join("educations");
			return builder.like(resumeProfQaualification.get("qualification"), contains(expression));

		};
	}

//	public static Specification<Resume> numberOfOLPassesContains(String expression) {
//		return (root, query, builder) -> {
//			Join<GCEOL, Resume> resumeOLPasses = root.join("ols");
//			return builder.like(resumeOLPasses.get("numberofpasses"), contains(expression));
//
//		};
//	}

	public static Specification<Resume> numberOfOLPassesContainsIn(int expression) {
		return (root, query, builder) -> {
			Join<GCEOL, Resume> resumeOLPasses = root.join("ols");
			return builder.greaterThanOrEqualTo(resumeOLPasses.get("nop"), expression);

		};
	}
	public static Specification<Resume> numberOfALPassesContainsIn(int expression) {
		return (root, query, builder) -> {
			Join<GCEAL, Resume> resumeALPasses = root.join("als");
			return builder.greaterThanOrEqualTo(resumeALPasses.get("nop"), expression);

		};
	}
//	public static Specification<Resume> numberOfALPassesContains(int expression) {
//		return (root, query, builder) -> {
//			Join<GCEAL, Resume> resumeALPasses = root.join("als");
//			return builder.greaterThanOrEqualTo(resumeALPasses.get("numberofpasses"), expression);
//
//		};
//	}

	private static String contains(String expression) {
		return MessageFormat.format("%{0}%", expression);
	}

}
