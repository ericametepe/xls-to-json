package com.bnpparibas.bddf.fipro.model;

import java.util.List;


public class PropositionNode 
{

	private String typeProposition = ""; 
	private String idProposition = "";
	private String propositionLabel = "";
	private String productCode = "";
	private String additionalInfo = "";
	private String additionalInfo2 = "";
	private String additionalInfo3 = "";
	
	public PropositionNode(String propositionLabel)
	{
		this.propositionLabel = propositionLabel;
	}
	
	public PropositionNode(String idProposition, String propositionLabel)
	{
		this.idProposition = idProposition;
		this.propositionLabel = propositionLabel;
	}
	
	public String getTypeProposition()
	{
		return typeProposition;
	}
	
	public void setTypeProposition(String typeProposition)
	{
		this.typeProposition = typeProposition;
	}
	
	public String getIdProposition()
	{
		return idProposition;
	}
	
	public void setIdProposition(String idProposition)
	{
		this.idProposition = idProposition;
	}
	
	public String getPropositionLabel()
	{
		return propositionLabel;
	}
	
	public void setPropositionLabel(String propositionLabel)
	{
		this.propositionLabel = propositionLabel;
	}
	
	public String getProductCode()
	{
		return productCode;
	}
	
	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}
	
	
	public String getAdditionalInfo()
	{
		return additionalInfo;
	}
	
	public void  setAdditionalInfo(String additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}
	
	public String getAdditionalInfo2()
	{
		return additionalInfo2;
	}
	
	public void  setAdditionalInfo2(String additionalInfo2)
	{
		this.additionalInfo2 = additionalInfo2;
	}
	
	public String getAdditionalInfo3()
	{
		return additionalInfo3;
	}
	
	public void  setAdditionalInfo3(String additionalInfo3)
	{
		this.additionalInfo3 = additionalInfo3;
	}

}
