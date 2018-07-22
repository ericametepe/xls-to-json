package com.bnpparibas.bddf.fipro.model;

public class QuestionLabels {
	
	private String code;
	private String label;
	
	public QuestionLabels(String code, String label)
	{
		this.code = code;
		this.label = label;
	}
	
	public String getCode()
	{
		return this.code;
	}
	
	public String getLabel()
	{
		return this.label;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	

}
