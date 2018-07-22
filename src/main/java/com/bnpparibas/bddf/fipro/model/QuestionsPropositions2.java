package com.bnpparibas.bddf.fipro.model;

import java.util.List;


public class QuestionsPropositions2 
{

	private List<String> questions;
	private List<String> propositions;
	
	public QuestionsPropositions2()
	{
		
	}
	public QuestionsPropositions2(List<String> questions, List<String> propositions)
	{
		this.questions = questions;
		this.propositions = propositions;
	}
	
	public List<String> getQuestions()
	{
		return questions;
	}
	
	public void setQuestions(List<String> questions)
	{
		this.questions = questions;
	}
	
	public List<String> getPropositions()
	{
		return propositions;
	}
	
	public void setPropositions(List<String> propositions)
	{
		this.propositions = propositions;
	}
	
	
}
