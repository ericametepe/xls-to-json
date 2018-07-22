package com.bnpparibas.bddf.fipro.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bnpparibas.bddf.fipro.model.PropositionNode;
import com.bnpparibas.bddf.fipro.model.QuestionLabels;
import com.bnpparibas.bddf.fipro.model.QuestionNode;
import com.bnpparibas.bddf.fipro.model.QuestionTree;
import com.bnpparibas.bddf.fipro.model.QuestionsPropositions;
import com.bnpparibas.bddf.fipro.model.QuestionsPropositions2;
import com.bnpparibas.bddf.fipro.model.RecommendedGuarantee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;


@Component
public class ExcelQuestionsTrees 
{
	 String pathFile = "";
	 int rowMinExcel = 3;
	 int columnMaxExcel = 16;
	 String[] noParsingColumn = {"2","4","6","8","9", "11", "18"};
	 
	 int columnPropositionMinExcel = 17;//11;//17;
	 int columnPropositionMaxExcel = 21;//14;//21;
	 
	 int columnGuaranteeMinExcel = 25;//11;//17;
	 int columnGuaranteeMaxExcel = 31;//14;//21;
	
	 String colorAdditionalInfo = "FF4BACC6";
	 String colorPropositionProduct = "FFFFFF99";
	 String colorPropositionNote = "FFC0504D";
	
	 XSSFSheet mySheet = null;
	
	 
	 int REQCompteur = 0; //number of Requirements found
	 int QUESTCompteur = 0; //number of questions found
	 int ADDCompteur = 0; //number of response found
	 int PROPCompteur = 0;
	 int PRODCompteur = 0;
	 int NOTECompteur = 0;
	 int ANSWERCompteur = 0;
	
	 
	 List<QuestionTree> questionsTreees = new ArrayList<QuestionTree>(); //list that contain all the trees of questions (root is the requirement)
	 List<QuestionTree> questionsTreeesCode = new ArrayList<QuestionTree>(); //list that contains all the trees of code questions (root is the requirement code)
	 List<QuestionLabels> listReqLabels = new ArrayList<QuestionLabels>();  //list of all possible requirements with their codes
	 List<QuestionLabels> listQuestionLabels = new ArrayList<QuestionLabels>();  //list of all possible questions with their codes
	 List<QuestionLabels> listAddInfoLabels = new ArrayList<QuestionLabels>(); //list of all possible additional informations with their code
	 
	 Map<List<String>, List<String>> mapQuestionsProducts = new HashMap<List<String>, List<String>>();
	 //List<QuestionLabels> listProposedProducts = new ArrayList<QuestionLabels>(); //list of all possible additional informations with their code
	 
	 List<List<String>> allListProprities = new ArrayList<List<String>>();
	 List<List<String>> allListPropritiesCode = new ArrayList<List<String>>();
	 
	 List<PropositionNode> listProposedProducts = new ArrayList<PropositionNode>();  //list of all possible questions with their codes
	 List<PropositionNode> listProposedNotes = new ArrayList<PropositionNode>();
	 List<PropositionNode> listAllProposed = new ArrayList<PropositionNode>();
	 
	 List<List<String>> allListProposedProprities = new ArrayList<List<String>>();
	 List<List<String>> allListProposedCodeProprities = new ArrayList<List<String>>();
	 
	 List<RecommendedGuarantee> allRecommendedGuaranteesLabel = new ArrayList<RecommendedGuarantee>();
	 List<RecommendedGuarantee> allRecommendedGuarantees = new ArrayList<RecommendedGuarantee>();
	 
	 
	
	 
	 //List<QuestionLabels> listAddInfoProductLabels = new ArrayList<QuestionLabels>(); //list of all possible additional informations with their code
	 
	 
	 public ExcelQuestionsTrees()
	 {
		 
	 }
	 
	 public ExcelQuestionsTrees(String pathFile, int rowMinExcel, int columnMaxExcel, int columnPropositionMinExcel, 
			 int columnPropositionMaxExcel, int columnGuaranteeMinExcel, int columnGuaranteeMaxExcel, String[] noParsingColumn)
	 {
		 this.pathFile = pathFile;
		 this.rowMinExcel = rowMinExcel;
		 this.columnMaxExcel = columnMaxExcel;
		 this.columnPropositionMinExcel = columnPropositionMinExcel;
		 this.columnPropositionMaxExcel = columnPropositionMaxExcel;
		 this.noParsingColumn = noParsingColumn;
		 this.columnGuaranteeMinExcel = columnGuaranteeMinExcel;
		 this.columnGuaranteeMaxExcel = columnGuaranteeMaxExcel;
	 }
	
	
	 //get the content of the excel file 
	public  void readExeclFile() throws IOException
	{
		URL url = getClass().getResource(pathFile);
		String path = URLDecoder.decode(url.getPath(), "UTF-8");
		File myFile = new File(path);
		FileInputStream fis = new FileInputStream(myFile);
        
        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
       
        // Return first sheet from the XLSX workbook
        mySheet = myWorkBook.getSheet("besoin garantie");
       
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();
        
        extractExcel(rowIterator);
	}
	
	
	//to check if the list of QuestionTree contains the new requirement found
	public  boolean containsTree(final List<QuestionTree> list, final String requirementCode)
	{
	    return list.stream().filter(node -> node.getrequirementCode().equals(requirementCode)).findFirst().isPresent();
	}
	
	//to check if the list of QuestionTree contains the new question found
	public  boolean containsQuestion(final List<QuestionNode> list, final String questionCode)
	{
	    return list.stream().filter(node -> node.getQuestionCode().equals(questionCode)).findFirst().isPresent();
	}
	
	//to check if the list of QuestionTree contains the new question found
	public  boolean containsProposition(final List<PropositionNode> list, final String propositionLabel)
	{
		return list.stream().filter(node -> node.getPropositionLabel().equals(propositionLabel)).findFirst().isPresent();
	}
	
	//to check if the list of QuestionLabels contains the new questionLabel found
	/*public  boolean containsQuestionLabel(final List<QuestionLabels> list, final String questionLabel)
	{
	    return list.stream().filter(element -> element.getLabel().toLowerCase().equals(questionLabel.toLowerCase())).findFirst().isPresent();
	}*/
	
	//to check if the list of QuestionLabels contains the new questionLabel found 
	//if it already exists then return correspondent code else return ""
	public  String containsGetQuestionLabel(final List<QuestionLabels> list, final String questionLabel)
	{
		String result ="";
		Optional<QuestionLabels> ql = list.stream().filter(element -> element.getLabel().toLowerCase().equals(questionLabel.toLowerCase())).findFirst();
		if (ql.isPresent())
		{
			result = ql.get().getCode();
		}
		
		return result;
	}
	
	
	//to check if the cell has the same code color wanted
	public  boolean checkColor(int row, int column, String codeColor)
	{
		 boolean compare = false;
		 
		 Row rowCell = mySheet.getRow(row);
		 Cell cell = rowCell.getCell(column);
		 System.out.println("column = " + column);
		 System.out.println("*cell = " + cell);
		 CellStyle cellStyle = cell.getCellStyle();
	     Color color = cellStyle.getFillForegroundColorColor();  //getFillBackgroundColorColor();
	     if (color != null) 
	     {
		      if (color instanceof XSSFColor) 
		      {
		    	  String coloru = ((XSSFColor)color).getARGBHex().toString();
		    	  if (((XSSFColor)color).getARGBHex().toString().equals(codeColor))// "FFEAEAEA" && !((XSSFColor)color).getARGBHex().toString().equals("FF4BACC6") )
		    	  {
		    		  compare = true;
		    	  }
		      } 
		      /*else if (color instanceof HSSFColor) 
		      {
		    	  if (! (color instanceof HSSFColor.AUTOMATIC))
		    	  {
		    		  System.out.println("cellStyle 2 = "+cell.getAddress() + ": " + ((HSSFColor)color).getHexString());
		    	  }
		      }*/
	     }
	     
	     return compare;
	}
	
	//verify if the next column corresponding to children of the selected rows has the same color and text
	//verify if it's the same additional information
	public  boolean checkNextColumnColorText(int column,int minRow, int maxRow)
	{
		int row=minRow;
		boolean sameColorText = true;
		
		Row rowContent = mySheet.getRow(row);
		column = moveColumn(column);
		Cell cell = rowContent.getCell(column);
		System.out.println("column = " + column);
		System.out.println("*cell = " + cell);
		String lastCell = cell.getStringCellValue();
		while (row<=maxRow && sameColorText  )
		{
			rowContent = mySheet.getRow(row);
			column = moveColumn(column);
			cell = rowContent.getCell(column);
			System.out.println("column = " + column);
			System.out.println("*cell = " + cell);
			if (!checkColor(row,column, colorAdditionalInfo) || !lastCell.equals(cell.getStringCellValue()))
			{
				sameColorText = false;
			}
			row++;
		}
		
		return sameColorText;
	}
	
	public int moveColumn(int oldColumn){
		if(Arrays.asList(noParsingColumn).contains(Integer.toString(oldColumn))){
			System.out.println("noParsingColumn = " + oldColumn);
			if (oldColumn == 8){
				oldColumn = oldColumn+1;
			}
			return oldColumn+1;
		}
		return oldColumn;
		
	}
	
	
	//start extracting excel file
	//extract requirements and its additional informations (if exist) and call the process of build the rest of the tree
	public  void extractExcel(Iterator<Row> rowIterator) throws JsonProcessingException
	{
		
		int rowMin = 0;
		int rowMax = 0;
		String lastCell = "";
		String newCell = "";
		int i = 0;
		int currentColumn = 1;
		
		while (rowIterator.hasNext()) 
        {
            Row row = rowIterator.next();
            //System.out.println("row.getRowNum() = " + row.getRowNum());
            if (row.getRowNum() >= rowMinExcel)
            {
            	currentColumn = moveColumn(currentColumn);
            	Cell cell = row.getCell(currentColumn);
            	System.out.println("currentColumn = " + currentColumn);
            	System.out.println("*cell = " + cell);
                if (cell != null)
                {
                	newCell = cell.getStringCellValue();
                	
                	if (!newCell.equals(""))
                	{
                		if (newCell.equals(lastCell))
    		            {
    		            	rowMax = i;
    		            }
    		            else
    		            {
    		            	List<String> listProprities = new ArrayList<String>();
    		            	List<String> listPropritiesCode = new ArrayList<String>();
    		            	checkCreateQuestionTree(lastCell, rowMin, rowMax, currentColumn, listProprities, listPropritiesCode);
    		            	
    		            	rowMin = i;
    		            	rowMax = i;
    		            }
                    }
                	lastCell = cell.getStringCellValue();
                }	
            }
            i++;	
        }
		
		List<String> listProprities = new ArrayList<String>();
		List<String> listPropritiesCode = new ArrayList<String>();
		checkCreateQuestionTree(lastCell, rowMin, rowMax, currentColumn, listProprities, listPropritiesCode);
		
		
		PrintWriter writer;
		PrintWriter writerCode;
		//Path file = Paths.get("C:\\Workspace\\allQuestions.txt");
		
		List<QuestionsPropositions> allProprities = new ArrayList<QuestionsPropositions>();
		List<QuestionsPropositions2> allProprities2 = new ArrayList<QuestionsPropositions2>();
		//List<List<String>> allProprities = new ArrayList<List<String>>();
		
		
		try {
			writer = new PrintWriter("src/main/resources/resultfiles/proposedproductsLabel.properties", "UTF-8");
			writerCode = new PrintWriter("src/main/resources/resultfiles/proposedproducts.properties", "UTF-8");
			
			for(int j=0;j<allListProprities.size();j++)
			{
				//System.out.println(" allListProprities" + allListProprities);
				
				//writer.println("");
				String allString = "QuestionsProducts.";
				String allStringCode = "QuestionsProducts.";
				
				
				List<String> allCode = new ArrayList<String>();
				
				for (int k=0;k<allListProprities.get(j).size();k++)
				{
					allCode.add(allListProprities.get(j).get(k));
					
					allString = allString+allListProprities.get(j).get(k);
					if(k != allListProprities.get(j).size()-1)
					{
						allString = allString+".";
					}
					
					allStringCode = allStringCode+allListPropritiesCode.get(j).get(k);
					if(k != allListPropritiesCode.get(j).size()-1)
					{
						allStringCode = allStringCode+".";
					}
				}
				
				allString = allString+"=";
				allStringCode = allStringCode+"=";
				for(int k=0; k < allListProposedProprities.get(j).size(); k++)
				{
					allCode.add(allListProposedProprities.get(j).get(k));
					
					allString = allString + allListProposedProprities.get(j).get(k);
					if(k != allListProposedProprities.get(j).size()-1)
					{
						allString = allString+", ";
					}
					
					allStringCode = allStringCode + allListProposedCodeProprities.get(j).get(k);
					if(k != allListProposedCodeProprities.get(j).size()-1)
					{
						allStringCode = allStringCode+",";
					}
					
				}
				
				mapQuestionsProducts.put(allListProprities.get(j), allListProposedProprities.get(j));
				
				QuestionsPropositions questionsPropositions = new QuestionsPropositions (allCode);
				allProprities.add(questionsPropositions);
				
				QuestionsPropositions2 questionsPropositions2 = new QuestionsPropositions2 (allListProprities.get(j),allListProposedProprities.get(j));
				allProprities2.add(questionsPropositions2);
				
				writer.println(allString);
				writerCode.println(allStringCode);
			}
			writer.close();
			writerCode.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		//String payloadJson = mapper.writeValueAsString(questionsTreees);
		try {
			
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/questionsTreeesCode.json"),questionsTreeesCode);
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/questionsTreees.json"),questionsTreees);
			
			System.out.println("questionsTreees.size() = "+questionsTreees.size());
			System.out.println("ANSWERCompteur = "+ANSWERCompteur);
			
			
			List<QuestionLabels> listReqQueAdd = new ArrayList<>();
			listReqQueAdd.addAll(listReqLabels);
			listReqQueAdd.addAll(listQuestionLabels);
			listReqQueAdd.addAll(listAddInfoLabels);
			
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/listReqQueAdd.json"),listReqQueAdd);
			
			
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/listProposedProducts.json"),listProposedProducts);
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/listProposedNotes.json"),listProposedNotes);
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/listAllProposed.json"),listAllProposed);

			
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/allProprities.json"),allProprities);
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/mapQuestionsProducts.json"),mapQuestionsProducts);
			
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/allProprities2.json"),allProprities2);
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/listAllProposed.json"),listAllProposed);
			
			
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/allRecommendedGuaranteesLabel.json"),allRecommendedGuaranteesLabel);
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/resultfiles/allRecommendedGuarantees.json"),allRecommendedGuarantees);
			
			System.out.println("allRecommendedGuarantees.size() = " + allRecommendedGuarantees.size());
			System.out.println("allRecommendedGuaranteesLabel.size() = " + allRecommendedGuaranteesLabel.size());
			System.out.println("listProposedNotes.size() = " + listProposedNotes.size());
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//System.out.println("");
		//System.out.println(" Tree  = "+payloadJson);
		
		
		//ObjectMapper mapper2 = new ObjectMapper();
		//String payloadJson2 = mapper2.writeValueAsString(questionsTreeesCode);
		//System.out.println("");
	    //System.out.println(" TreeCode = "+payloadJson2);
		
	    
	    
		//ObjectMapper mapper3 = new ObjectMapper();
		//String payloadJson3 = mapper3.writeValueAsString(list);
		//System.out.println("");
	    //System.out.println(" ListCode = "+payloadJson3);
	    
	    //ObjectMapper mapper4 = new ObjectMapper();
		//String payloadJson4 = mapper4.writeValueAsString(listProposedProducts);
	    //System.out.println(" listProposedProducts = "+payloadJson4);
	    
	    //ObjectMapper mapper5 = new ObjectMapper();
		//String payloadJson5 = mapper5.writeValueAsString(listProposedNotes);
	    //System.out.println(" listProposedNotes = "+payloadJson5);
	    
	   
	    //ObjectMapper mapper6 = new ObjectMapper();
		//String payloadJson6 = mapper6.writeValueAsString(listAllProposed);
		//System.out.println("");
	    //System.out.println(" listAllProposed = "+payloadJson6);
	    
	    
		//String payloadJson7 = mapper7.writeValueAsString(allProprities);
	    //System.out.println("");
	    //System.out.println(" allProprities = ");
	    
	    System.out.println("");
	    System.out.println(" PROPCompteur = "+PROPCompteur);
	    
	    
	    
	}
	
	
	//check if new requirement is found and create QuestionTree (root) with the children questions
	public  void checkCreateQuestionTree(String lastCell, int rowMin, int rowMax, int currentColumn, List<String> listProprities, List<String> listPropritiesCode)
	{
		
		if (!lastCell.equals(""))
    	{
			
					
    		if (!containsTree(questionsTreees, lastCell))
            {
        		QuestionTree questionTree = new QuestionTree(lastCell);//+ " "+ rowMin+ " "+rowMax
        		
        		String codeREQ = containsGetQuestionLabel(listReqLabels, lastCell);
        		
        		if (codeREQ.equals(""))
        		{
        			REQCompteur++;
        			String formatREQCompteur = String.format("%04d", REQCompteur);
        			QuestionLabels questionLabels = new QuestionLabels("REQ"+formatREQCompteur,lastCell);
        			listReqLabels.add(questionLabels);
        			codeREQ = "REQ"+formatREQCompteur;
        		}
        		
        		listProprities.add(lastCell);
        		listPropritiesCode.add(codeREQ);
        		List<String> listProprities2 = new ArrayList<String>(listProprities);
        		List<String> listPropritiesCode2 = new ArrayList<String>(listPropritiesCode);
        		
        		QuestionTree questionTree2 = new QuestionTree(codeREQ);
        		
            	int newColumn = currentColumn + 1;
            	newColumn = moveColumn(newColumn);
            	if (newColumn < columnMaxExcel )
            	{
            		if (checkNextColumnColorText(newColumn, rowMin, rowMax))	
	   				{
            			Row rowAddInfo = mySheet.getRow(rowMin);
            			newColumn = moveColumn(newColumn);
            			Cell cellAddInfo = rowAddInfo.getCell(newColumn);
            			
            			String codeAdditional = containsGetQuestionLabel(listAddInfoLabels, cellAddInfo.getStringCellValue());
                		
                		if (codeAdditional.equals(""))
                		{
                			ADDCompteur++;
                			String formatREQCompteur = String.format("%04d", ADDCompteur);
                			QuestionLabels questionLabels = new QuestionLabels("ADINFO"+formatREQCompteur,cellAddInfo.getStringCellValue());
                			listAddInfoLabels.add(questionLabels);
                			
                			codeAdditional = "ADINFO"+formatREQCompteur;
                		}
                		
            			questionTree.setAdditionalInformation(cellAddInfo.getStringCellValue());
            			questionTree2.setAdditionalInformation(codeAdditional);
            			
            			newColumn = newColumn + 1;
            			newColumn = moveColumn(newColumn);
		            	if (newColumn < columnMaxExcel )
		            	{
		            		List<List<QuestionNode>> qNs  = extractQuestions(rowMin, rowMax, newColumn, listProprities2, listPropritiesCode2);
		            		
		            		List<QuestionNode> qNs1 = qNs.get(0);
		            		List<QuestionNode> qNs2 = qNs.get(1);
		            		questionTree.setquestions(qNs1);
		            		questionTree2.setquestions(qNs2);
		            	}
		            	questionsTreees.add(questionTree);
		            	questionsTreeesCode.add(questionTree2);
            			
	   				}
            		else
            		{
            			List<List<QuestionNode>> qNs  = extractQuestions(rowMin, rowMax, newColumn, listProprities2, listPropritiesCode2);
	            		List<QuestionNode> qNs1 = qNs.get(0);
	            		List<QuestionNode> qNs2 = qNs.get(1);
	            		
	            		questionTree.setquestions(qNs1);
	            		questionTree2.setquestions(qNs2);
	            		
						questionsTreees.add(questionTree);
						questionsTreeesCode.add(questionTree2);
            		}
            	}
            }
    	}
	}
	
	
	
	public void extractGuarantees(int row, String answerId, String productCode){
		
		List<String> listGuaranteesLabel = new ArrayList<String>();
		List<String> listGuarantees = new ArrayList<String>();
		
		for (int column=columnGuaranteeMinExcel; column<columnGuaranteeMaxExcel; column++){
			
			Row rowGuarantee = mySheet.getRow(row);
			Cell cellGuarantee = rowGuarantee.getCell(column);
			if (cellGuarantee != null && !cellGuarantee.getStringCellValue().equals("")){
				listGuaranteesLabel.add(cellGuarantee.getStringCellValue());
				listGuarantees.add(GuaranteesCodeEnum.getCodeByLabel(cellGuarantee.getStringCellValue()));
			}
			
		}
		
		RecommendedGuarantee recommendedGuaranteeLabel = new RecommendedGuarantee(answerId, productCode, listGuaranteesLabel);
		RecommendedGuarantee recommendedGuarantee = new RecommendedGuarantee(answerId, productCode, listGuarantees);
		
		allRecommendedGuaranteesLabel.add(recommendedGuaranteeLabel);
		allRecommendedGuarantees.add(recommendedGuarantee);
		
	}
	
	
	//check if new product is found and create node with the children questions
		public  void checkCreatePropositionNode(List<PropositionNode> propositionsNode, String answerId,int column, int rowMin, int rowMax)
		{
			List<String> proposedPropritie = new ArrayList<String>();
			List<String> proposedPropritieCode = new ArrayList<String>();
			
			for (int i = rowMin; i<=rowMax; i++)
			{
				 Row row = mySheet.getRow(i);
				 column = moveColumn(column);
				 Cell Cell = row.getCell(column);
				 String lastCell = Cell.getStringCellValue();
				 
				 if (!lastCell.equals(""))
				 {
					 PropositionNode propositionNode = new PropositionNode(lastCell);
					 
					 if(checkColor(i, column, colorPropositionNote))
					 {
						 NOTECompteur++;
			    		 String formatNOTECompteur = String.format("%04d", NOTECompteur);
			    		 propositionNode.setIdProposition("NOTE"+formatNOTECompteur); //+ " "+ rowMinFils+ " "+rowMaxFils
			    		 propositionNode.setTypeProposition("NOTE");
			    		 proposedPropritieCode.add("NOTE"+formatNOTECompteur);
					 }
					 else // if (checkColor(i, column, colorPropositionNote))
					 {
			    		 PRODCompteur++;
			    		 String formatPRODCompteur = String.format("%04d", PRODCompteur);
			    		 propositionNode.setIdProposition("PROD"+formatPRODCompteur); //+ " "+ rowMinFils+ " "+rowMaxFils
			    		 propositionNode.setTypeProposition("PRODUCT");
			    		 propositionNode.setProductCode("MLT01");
			    		 proposedPropritieCode.add("PROD"+formatPRODCompteur);
			    		 
			    		 //if (!answerId.equals(""))
			    			 extractGuarantees(i, answerId, propositionNode.getProductCode());
			    		 
					 }
					 
					 PROPCompteur++;
					 
		    		 proposedPropritie.add(lastCell);
		    		 
		    		 
		    			
		        		
		             int newColumn = column + 1;
		            	if (newColumn < columnPropositionMaxExcel )
		            	{
		            		Row rowAddInfo = mySheet.getRow(i);
		            		newColumn = moveColumn(newColumn);
		            		Cell cellAddInfo = rowAddInfo.getCell(newColumn);
		            		System.out.println("newColumn = " + newColumn);
		         			System.out.println("*cellAddInfo = " + cellAddInfo);
		            		if (cellAddInfo != null)
		            		{
		            			if (!cellAddInfo.equals(""))
		            			{
		            				propositionNode.setAdditionalInfo(cellAddInfo.getStringCellValue());
		            			}
		            		}
		            		
		            		newColumn = column + 2;
				            if (newColumn < columnPropositionMaxExcel )
				            {
				            	rowAddInfo = mySheet.getRow(i);
				            	newColumn = moveColumn(newColumn);
			            		cellAddInfo = rowAddInfo.getCell(newColumn);
			            		System.out.println("newColumn = " + newColumn);
			            		System.out.println("*cellAddInfo = " + cellAddInfo);
			            		if (cellAddInfo != null)
			            		{
			            			if (!cellAddInfo.equals(""))
			            			{
			            				propositionNode.setAdditionalInfo2(cellAddInfo.getStringCellValue());
			            			}
			            		}
			            		
			            		newColumn = column + 3;
					            if (newColumn < columnPropositionMaxExcel )
					            {
					            	rowAddInfo = mySheet.getRow(i);
					            	newColumn = moveColumn(newColumn);
				            		cellAddInfo = rowAddInfo.getCell(newColumn);
				            		System.out.println("newColumn = " + newColumn);
				            		System.out.println("*cellAddInfo = " + cellAddInfo);
				            		if (cellAddInfo != null)
				            		{
				            			if (!cellAddInfo.equals(""))
				            			{
				            				propositionNode.setAdditionalInfo3(cellAddInfo.getStringCellValue());
				            			}
				            		}
					            }
				            }
			   			}
		            	
		            	propositionsNode.add(propositionNode);
		            	if (propositionNode.getTypeProposition().equals("PRODUCT"))
		            	{
		            		listProposedProducts.add(propositionNode);
		            	}
		            	else
		            	{
		            		listProposedNotes.add(propositionNode);
		            	}
		            	
		            	listAllProposed.add(propositionNode);
		            	
		    		
				 }
			}
			
			allListProposedProprities.add(proposedPropritie);
        	allListProposedCodeProprities.add(proposedPropritieCode);
	}
	
	
	
	//check if new question is found and create node with the children questions
	public  void checkCreateQuestionNode(List<QuestionNode> questionNodes, List<QuestionNode> questionNodesCode, String lastCell, int column, int rowMinFils, int rowMaxFils, List<String> listProprities, List<String> listPropritiesCode)
	{
		if (!lastCell.equals(""))
    	{
			
			
			
    		if (!containsQuestion(questionNodes, lastCell))
            {
    			
    			
            	QuestionNode questionNode = new QuestionNode(lastCell); //+ " "+ rowMinFils+ " "+rowMaxFils
            	
            	String codeQUEST = containsGetQuestionLabel(listQuestionLabels, lastCell);
        		
            
        		if (codeQUEST.equals(""))
        		{
        			QUESTCompteur++;
            		String formatQUESTCompteur = String.format("%04d", QUESTCompteur);//QUESTCompteur+"";
        			QuestionLabels questionLabels = new QuestionLabels("QUEST"+formatQUESTCompteur,lastCell);
        			listQuestionLabels.add(questionLabels);
        			
        			codeQUEST = "QUEST"+formatQUESTCompteur;
        		}
        		
        		listProprities.add(lastCell);
            	listPropritiesCode.add(codeQUEST);
    			List<String> listProprities2 = new ArrayList<String>(listProprities);
    			List<String> listPropritiesCode2 = new ArrayList<String>(listPropritiesCode);
    		
        		
        		QuestionNode questionNode2 = new QuestionNode(codeQUEST);
        		
            	int newColumn = column + 1;
            	newColumn = moveColumn(newColumn);
            	if (newColumn < columnMaxExcel )
            	{
            		if (checkNextColumnColorText(newColumn, rowMinFils, rowMaxFils))	
	   				{
            			Row rowAddInfo = mySheet.getRow(rowMinFils);
            			newColumn = moveColumn(newColumn);
            			Cell cellAddInfo = rowAddInfo.getCell(newColumn);
            			String codeAdditional = containsGetQuestionLabel(listAddInfoLabels, cellAddInfo.getStringCellValue());
                		
                		if (codeAdditional.equals(""))
                		{
                			ADDCompteur++;
                			String formatREQCompteur = String.format("%04d", ADDCompteur);
                			QuestionLabels questionLabels = new QuestionLabels("ADINFO"+formatREQCompteur,cellAddInfo.getStringCellValue());
                			listAddInfoLabels.add(questionLabels);
                			
                			codeAdditional = "ADINFO"+formatREQCompteur;
                		}
            			
            			questionNode.setAdditionalInformation(cellAddInfo.getStringCellValue());
            			questionNode2.setAdditionalInformation(codeAdditional);
            			
            			//newColumn = column + 2;
            			newColumn = newColumn + 1;
            			newColumn = moveColumn(newColumn);
		            	if (newColumn < columnMaxExcel )
		            	{
		            		List<List<QuestionNode>> qNs  = extractQuestions(rowMinFils, rowMaxFils, newColumn, listProprities2, listPropritiesCode2);
		            		
		            		
		            		List<QuestionNode> qNs1 = qNs.get(0);
		            		List<QuestionNode> qNs2 = qNs.get(1);
		            		
		            		if (qNs1 == null || qNs1.isEmpty()){
		            			
		            			ANSWERCompteur++;
	                			String formatREQCompteur = String.format("%04d", ANSWERCompteur);
	                			String answerId = "ANSW"+formatREQCompteur;
	                			questionNode.setAnswerId(answerId);
		            			questionNode2.setAnswerId(answerId);
		            		}
		            		
	            			questionNode.setQuestions(qNs1);
	            			questionNode2.setQuestions(qNs2);
		            	}
		            	else
		            	{
		            		allListProprities.add(listProprities);
		        			allListPropritiesCode.add(listPropritiesCode);
		        			
		        			ANSWERCompteur++;
                			String formatREQCompteur = String.format("%04d", ANSWERCompteur);
                			String answerId = "ANSW"+formatREQCompteur;
                			questionNode.setAnswerId(answerId);
	            			questionNode2.setAnswerId(answerId);
		        			
		        			List<PropositionNode> propositionsNode = new ArrayList<PropositionNode>();
		        			checkCreatePropositionNode(propositionsNode, questionNode.getAnswerId(),columnPropositionMinExcel, rowMinFils,  rowMaxFils );
		        			//System.out.println("propositionsNode = "+propositionsNode);
		        			
		        			
		            	}
		            	questionNodes.add(questionNode);
		            	questionNodesCode.add(questionNode2);
	   				}
            		else
            		{
            			
            			List<List<QuestionNode>> qNs  = extractQuestions(rowMinFils, rowMaxFils, newColumn, listProprities2, listPropritiesCode2);
            			
	            		List<QuestionNode> qNs1 = qNs.get(0);
	            		List<QuestionNode> qNs2 = qNs.get(1);
	            		
	            		if (qNs1 == null || qNs1.isEmpty()){
	            			
	            			ANSWERCompteur++;
                			String formatREQCompteur = String.format("%04d", ANSWERCompteur);
                			String answerId = "ANSW"+formatREQCompteur;
                			questionNode.setAnswerId(answerId);
	            			questionNode2.setAnswerId(answerId);
	            			
	            			//extractGuarantees(rowMinFils, answerId, "MLT011");
	            			//checkCreatePropositionNode(propositionsNode, questionNode.getAnswerId(),columnPropositionMinExcel, rowMinFils,  rowMaxFils );
	            		}
	            		
            			questionNode.setQuestions(qNs1);
            			questionNode2.setQuestions(qNs2);
		            	questionNodes.add(questionNode);
		            	questionNodesCode.add(questionNode2);
            		}
            	}
            }
    	}
		
	}
	
	
	//extract questions and its additional informations (if exist) and build the rest of the tree
	public  List<List<QuestionNode>> extractQuestions(int rowMin, int rowMax, int column, List<String> listProprities, List<String> listPropritiesCode)
	{
		List<List<QuestionNode>> questionALL  = new ArrayList<List<QuestionNode>>();
		List<QuestionNode> questionNodes = null;//new ArrayList<QuestionNode>();
		List<QuestionNode> questionNodesCode = null;
		int rowMinFils = 0;
		int rowMaxFils = 0;
		
		String lastCell = "";
		String newCell = "";
		
		questionNodes = new ArrayList<QuestionNode>();
		questionNodesCode = new ArrayList<QuestionNode>();

		int i = 0;
		
		for (i = rowMin; i<=rowMax; i++)
		{
			 List<String> listProprities2 = new ArrayList<String>(listProprities);
			 List<String> listPropritiesCode2 = new ArrayList<String>(listPropritiesCode);
			 Row row = mySheet.getRow(i);
			 column = moveColumn(column);
			 Cell cell = row.getCell(column);
			 if (cell != null)
	         {
				 newCell = cell.getStringCellValue();
				 
		         if (!newCell.equals(""))
		         {
		            if (newCell.equals(lastCell))
		            {
		            	rowMaxFils = i;
		            }
		            else
		            {
		            	checkCreateQuestionNode(questionNodes, questionNodesCode, lastCell, column, rowMinFils, rowMaxFils, listProprities2, listPropritiesCode2);
		            
		            	rowMinFils = i;
		            	rowMaxFils = i;
		            } 	
		         }
		         
		         lastCell = cell.getStringCellValue();
			 }
		}
		
		List<String> listProprities2 = new ArrayList<String>(listProprities);
		List<String> listPropritiesCode2 = new ArrayList<String>(listPropritiesCode);
		
			
		checkCreateQuestionNode(questionNodes, questionNodesCode, lastCell, column, rowMinFils, rowMaxFils, listProprities2, listPropritiesCode2);
       
		if (lastCell.equals(""))
		{
			allListProprities.add(listProprities);
			allListPropritiesCode.add(listPropritiesCode);
			
			List<PropositionNode> propositionsNode = new ArrayList<PropositionNode>();
			String formatREQCompteur = String.format("%04d", ANSWERCompteur+1);
			String answerId = "ANSW"+formatREQCompteur;
			checkCreatePropositionNode(propositionsNode, answerId, columnPropositionMinExcel, rowMin,  rowMax );
			//System.out.println("propositionsNode = "+propositionsNode);
		}
		
		questionALL.add(questionNodes);
		questionALL.add(questionNodesCode);
		
		return questionALL;
		
	}


}