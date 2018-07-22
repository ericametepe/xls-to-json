package com.bnpparibas.bddf.fipro.model;

import java.util.List;

public class QuestionNode 
{

	private String questionCode;
	private List<QuestionNode> questions;
	private String additionalInformation;
	private String answerId;
	
	public QuestionNode(String questionCode)
	{
		this.questionCode = questionCode;
	}
	
	
	public String getQuestionCode()
	{
		return questionCode;
	}
	
	public List<QuestionNode> getQuestions()
	{
		return questions;
	}
	
	public String getAdditionalInformation()
	{
		return additionalInformation;
	}
	
	public String getAnswerId()
	{
		return answerId;
	}
	
	
	public void setQuestionCode(String questionCode)
	{
		this.questionCode = questionCode;
	}
	
	public void setQuestions(List<QuestionNode> questions)
	{
		this.questions = questions;
	}
	
	public void  setAdditionalInformation(String additionalInformation)
	{
		this.additionalInformation = additionalInformation;
	}
	
	public void setAnswerId(String answerId)
	{
		this.answerId = answerId;
	}

}
