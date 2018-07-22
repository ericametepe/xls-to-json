package com.bnpparibas.bddf.fipro.utils;

import java.util.EnumSet;

public enum GuaranteesCodeEnum {
	G1("1","Financement possible sans garantie"), 
	G2("2","Caution Possible"), 
	G3("3","Caution adaptée"), 
	G4("4","Nantissement de véhicule"),
	G5("5","France Active"),
	G6("6","Nantissement de matériel/ outillage"),
	G7("7","Nantissement de Comptes de Titres"),
	G8("8","Nantissement de fonds de Commerce"),
	G9("9","BPI"),
	G10("10","SIAGI"),
	G11("11","Hypotèque (si travaux)"),
	G12("12","PPD (Privilège Préteur de Deniers)"),
	G13("13","Subrogation Privilège Vendeur FDC"),
	G14("14","Nantissement de parts sociales"),
	G15("15","Nantissement de contrat d'assurance"),
	G16("16","Subrogation dans le privilège du vendeur d'immeuble");
	

	private String code;
	private String label;

	private GuaranteesCodeEnum(String code, String label) {
		this.code = code;
		this.label = label;
	}


	public String getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}

	public static String getLabelByCode(String code) {
		return EnumSet.allOf(GuaranteesCodeEnum.class).stream().filter(e -> e.getCode().toString().equals(code))
				.findFirst()
				.map(GuaranteesCodeEnum::getLabel)
				.orElseThrow(() -> new IllegalStateException("Unsupported GuaranteesCodeEnum = " + code));
	}
	
	public static String getCodeByLabel(String label) {
		return EnumSet.allOf(GuaranteesCodeEnum.class).stream().filter(e -> e.getLabel().toString().equals(label))
				.findFirst()
				.map(e -> e.getCode().toString())
				.orElseThrow(() -> new IllegalStateException("Unsupported GuaranteesCodeEnum = " + label));
	}


	

}