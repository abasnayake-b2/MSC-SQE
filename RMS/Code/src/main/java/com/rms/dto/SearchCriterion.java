package com.rms.dto;

import lombok.Data;

@Data
public class SearchCriterion {

	private String sjobSector;
	private String seducationLevel;
	private String sskill;
	private String sdesignation;
	private String sprofQaualification;
	private String snumberOfOLPasses;
	private String snumberOfALPasses;
	private int snumberOfOLPassesIn;
	private int snumberOfALPassesIn;
}
