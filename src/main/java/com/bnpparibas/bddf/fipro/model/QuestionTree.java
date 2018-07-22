package com.bnpparibas.bddf.fipro.model;

import java.util.List;

public class QuestionTree {

	private String requirementCode;
	private List<QuestionNode> questions;
	private String additionalInformation;
	
	
	public QuestionTree(String requirementCode)
	{
		this.requirementCode = requirementCode;
	}
	
	
	public String getrequirementCode()
	{
		return requirementCode;
	}
	
	public List<QuestionNode> getquestions()
	{
		return questions;
	}
	
	public String getadditionalInformation()
	{
		return additionalInformation;
	}
	
	
	public void setrequirementCode(String requirementCode)
	{
		this.requirementCode = requirementCode;
	}
	
	public void setquestions(List<QuestionNode> questions)
	{
		this.questions = questions;
	}
	
	
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
		
	}

}
