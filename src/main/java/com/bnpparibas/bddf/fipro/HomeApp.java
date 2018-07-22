package com.bnpparibas.bddf.fipro;

import com.bnpparibas.bddf.fipro.storage.StorageProperties;
import com.bnpparibas.bddf.fipro.storage.StorageService;
import com.bnpparibas.bddf.fipro.utils.ExcelQuestionsTrees;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class HomeApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SpringApplication.run(HomeApp.class,args);

		//ExcelQuestionsTrees excelQuestionsTrees = new ExcelQuestionsTrees("QuestionnaireCadrage T1.2 Corrige.xlsx", 3, 10);
		//ExcelQuestionsTrees excelQuestionsTrees = new ExcelQuestionsTrees("/Users/badache/eclipse-workspace/ExcelToJson/src/main/resources/Excels/QuestionnaireCadrage T1.2 CorrigeÌ�.xlsx", 3, 10);
		
		//ExcelQuestionsTrees excelQuestionsTrees = new ExcelQuestionsTrees("/xlsxfile/QuestionnaireCadrage T1.2 Corrige.xlsx", 3, 10,11,14);
		//ExcelQuestionsTrees excelQuestionsTrees = new ExcelQuestionsTrees("/xlsxfile/QuestionnaireCadrage T1.3 Garantie_Versionned.xlsx", 3, 16);
	/*
		String[] noParsingColumn = {"2","4","6","8","9", "11", "18"};
		ExcelQuestionsTrees excelQuestionsTrees = new ExcelQuestionsTrees("/xlsxfile/QuestionnaireCadrage T1.3 Garantie2.xlsx", 3, 16,17,21, 25, 32, noParsingColumn);
		
		
		try 
		{
			excelQuestionsTrees.readExeclFile();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}*/
		
		/*ExcelQuestionsTrees excelQuestionsTrees = new ExcelQuestionsTrees();
		
		excelQuestionsTrees.test();*/

	}


	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}