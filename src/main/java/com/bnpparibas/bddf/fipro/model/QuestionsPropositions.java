package com.bnpparibas.bddf.fipro.model;

import java.util.List;


public class QuestionsPropositions 
{

	private List<String> questionProposition;
	
	public QuestionsPropositions()
	{
		
	}
	public QuestionsPropositions(List<String> questionProposition)
	{
		this.questionProposition = questionProposition;
	}
	
	public List<String> getQuestionProposition()
	{
		return questionProposition;
	}
	
	public void setQuestionProposition(List<String> questionProposition)
	{
		this.questionProposition = questionProposition;
	}
	
	
}
