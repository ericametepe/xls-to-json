package com.bnpparibas.bddf.fipro.model;

import java.util.List;



public class RecommendedGuarantee {

	private String answerId;  
	
	private String productCode;
	
	private List<String> guaranteesCode;
	
	public RecommendedGuarantee(String answerId, String productCode, List<String> guaranteesCode){
		this.answerId = answerId;
		this.productCode = productCode;
		this.guaranteesCode = guaranteesCode;
	}
	
	
	public String getAnswerId()
	{
		return answerId;
	}
	
	public String getProductCode()
	{
		return productCode;
	}
	
	public List<String> getGuaranteesCode()
	{
		return guaranteesCode;
	}
	
	
	public void setAnswerId(String answerId)
	{
		this.answerId = answerId;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
		
	}
	
	public void setGuaranteesCode(List<String> guaranteesCode)
	{
		this.guaranteesCode = guaranteesCode;
	}
	
	
	


	
}
