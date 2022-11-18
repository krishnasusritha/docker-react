package supportlibraries.Reports;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class Reports {

	static String REPORTS_PATH = "Reports";
	public static String EXECUTIONS_PATH = REPORTS_PATH + "\\Executions";
	static File reportsDirectory;
	File executionsDirectory;
	static int currentExecutionCount=1;
	static File logFile;
	public static File currentExecutionDirectory;
	public static void createReportsDirectory(){
		try{
			
			reportsDirectory = new File(REPORTS_PATH);
			
			if(!reportsDirectory.exists()){				
				reportsDirectory.mkdir();
				System.out.println("Reports Directory created successfully");
			}
			
			
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	public static void createExecutionsDirectory(){
		try{
			
			File executionsDirectory = new File(EXECUTIONS_PATH);
			
			if(!reportsDirectory.exists()){
				reportsDirectory.mkdir();
				//System.out.println("Reports Directory created successfully");
			}
			
			if(!executionsDirectory.exists()){				
				executionsDirectory.mkdir();
				System.out.println("Executions Directory created successfully");
			}
			
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	public static void createCurrentExecutionDirectory(){
		try{
			File[] files = new File(EXECUTIONS_PATH).listFiles();
					
		if(files.length==0){
			currentExecutionCount=1;				
		}else{
			List<Integer> ExecutionNumbers = new LinkedList<Integer>();
			for(File file:files){
				String fileName = file.getName();
				if(fileName.startsWith("Execution_")){			
				String[] a = fileName.split("_");
				ExecutionNumbers.add(Integer.parseInt(a[1]));
				}
			}
			
			Collections.sort(ExecutionNumbers);
			currentExecutionCount = ExecutionNumbers.get(ExecutionNumbers.size()-1)+1;		
		}
		currentExecutionDirectory = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount);
		currentExecutionDirectory.mkdir();
		
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static void writeToLog(String message){
		try{
			logFile = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\LogFile.txt");
			if(!logFile.exists()){
				logFile.createNewFile();
			}
			//Thread.sleep(2000);
			BufferedReader br = new BufferedReader(new FileReader(logFile));
			String message1="";
			String sCurrentLine;
			while((sCurrentLine = br.readLine()) != null){
				message1 = message1+sCurrentLine+"\n";
				}			
			message = message1+message;			
			BufferedWriter bw = new BufferedWriter(new FileWriter(logFile));			
			bw.write(message);
			bw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	
	
	public static void writeToHtmlReports(List<String> firefoxpassedTestCases, List<String> firefoxfailedTestCases,List<String> iepassedTestCases, List<String> iefailedTestCases,List<String> chromepassedTestCases, List<String> chromefailedTestCases){
		try{
		
			
			System.out.println("firefox"+ firefoxpassedTestCases);
			System.out.println("firefox"+ firefoxfailedTestCases);
			System.out.println("ie"+ iepassedTestCases);
			System.out.println("ie"+ iefailedTestCases);
			System.out.println("chrome"+ chromepassedTestCases);
			System.out.println("chrome"+ chromefailedTestCases);
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution reports</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Execution "+currentExecutionCount+" </p>");
	        builder.append("<table  align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	      
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">Total Test Cases</th>");
	        builder.append("<th align=\"center\">Passed Test Cases</th>");
	        builder.append("<th align=\"center\">Falied Test Cases</th>");
	        builder.append("</tr>");
	        int totalTestCasesCount = firefoxpassedTestCases.size()+firefoxfailedTestCases.size()+iepassedTestCases.size()+iefailedTestCases.size()+chromepassedTestCases.size()+chromefailedTestCases.size();
	        int passedTestCasesCount = firefoxpassedTestCases.size()+iepassedTestCases.size()+chromepassedTestCases.size();
	        int failedTestCasesCount = firefoxfailedTestCases.size()+iefailedTestCases.size()+chromefailedTestCases.size();
	        
	        builder.append("<tr>");
	        builder.append("<td align=\"center\"> " + totalTestCasesCount + "</td>"); 
	        if(passedTestCasesCount>0){
	        	writeToHtml_PassedTestCases(firefoxpassedTestCases,iepassedTestCases,chromepassedTestCases);
	        	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_PassedtestCases.html\">"+passedTestCasesCount+"</a> </td>");
	        }else{
	        	 builder.append("<td align=\"center\"> 0 </td>");
	        }
	        
	        if(failedTestCasesCount>0){
	        	writeToHtml_FailedTestCases(firefoxfailedTestCases,iefailedTestCases,chromefailedTestCases);
	        	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_FailedtestCases.html\">"+failedTestCasesCount+"</a> </td>");
	        }else{
	        	 builder.append("<td align=\"center\"> 0 </td>");
	        }
	       
	        
	        builder.append(" </tr>  "); 
	     
	        builder.append("</table>");
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	        bw.write(html);
	        bw.close();
	        
	        
	       

	}catch(Exception e){
		e.printStackTrace();
	}
}
	
	
	public static void testsDashBoard(List<String> totalTests, List<String> Failedtests){
		try{
			if(!(totalTests.isEmpty()&&Failedtests.isEmpty())){
			File file = new File("Test.xls");
			
			String allTests="";
			int count1=0;
			for(String test: totalTests){
				if(count1==0){
					allTests = test;
				}else{
					allTests = allTests+"&&"+test;
				}
				count1++;
				
			}
			
			String failedTests="";
			int count2=0;
			for(String test: Failedtests){
				String[] test1 = test.split("&&");
				
				if(count2==0){
					failedTests = test1[0];
				}else{
					failedTests = failedTests+"&&"+test1[0];
				}
				count2++;
			}
			
			if(!file.exists()){
				file.createNewFile();
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("Tests");
				HSSFRow row0 = sheet.createRow(0);
				row0.createCell(0).setCellValue("Execution No.");
				row0.createCell(1).setCellValue("Total Tests");
				row0.createCell(2).setCellValue("Failed Tests");	
				
				HSSFRow row1 = sheet.createRow(1);
				
				HSSFCell cell1 = row1.createCell(0);
				cell1.setCellValue(currentExecutionCount);
				HSSFCell cell2 = row1.createCell(1);
				cell2.setCellValue(allTests);
				HSSFCell cell3 = row1.createCell(2);
				cell3.setCellValue(failedTests);
			
				
				FileOutputStream out1 = new FileOutputStream (file);
				wb.write(out1);
				out1.close();
			}else{
				
				List<List<Object>> excelData = new LinkedList<List<Object>>();
				HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
				
				HSSFSheet sheet = wb.getSheetAt(0);
				
				String sheetName = sheet.getSheetName();
				int rowCount = sheet.getLastRowNum();
				
				for(int i=0;i<=rowCount;i++){
					List<Object> rowData = new LinkedList<Object>();
					HSSFRow row = sheet.getRow(i);
					int colCount = row.getLastCellNum();
					for(int j=0;j<colCount;j++){						
						HSSFCell cell = row.getCell(j);
						if(cell==null){
							rowData.add("");
						}else if(cell.getCellType()==Cell.CELL_TYPE_STRING){
						rowData.add(cell.getStringCellValue());
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
						int cellValue = (int) cell.getNumericCellValue();
						rowData.add(cell.getNumericCellValue());
					}
												
					}
					excelData.add(rowData);
				}
				
				System.out.println(excelData);
				
				file.delete();
				Thread.sleep(2000);
				//File file1 = new File("Test1.xls");
				file.createNewFile();
				FileOutputStream out2 = new FileOutputStream (file);
				HSSFWorkbook wb1 = new HSSFWorkbook();
				HSSFSheet sheet1 = wb1.createSheet(sheetName);
		
				int rowNum =0;
				
				for(List<Object> rowdata : excelData){
					
					System.out.println(rowdata);
					HSSFRow row = sheet1.createRow(rowNum);
					rowNum++;
										
					int cellNum =0;
					for(Object cellData : rowdata){	
						HSSFCell cell = row.createCell(cellNum);
						//cell.setCellType(Cell.CELL_TYPE_STRING);						
						cell.setCellValue(cellData.toString());	
						cellNum++;
					}
					
				}
				
				HSSFRow row = sheet1.createRow(rowNum);
				HSSFCell cell1 = row.createCell(0);
				//cell1.setCellType(Cell.CELL_TYPE_STRING);
				//String count = (String)currentExecutionCount;
				cell1.setCellValue(currentExecutionCount);
				HSSFCell cell2 = row.createCell(1);
				//cell2.setCellType(Cell.CELL_TYPE_STRING);
				cell2.setCellValue(allTests);
				HSSFCell cell3 = row.createCell(2);
				//cell3.setCellType(Cell.CELL_TYPE_STRING);
				cell3.setCellValue(failedTests);
							
				wb1.write(out2);
				out2.close();
				
			}
				
			}
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void writeToHtmlReports2(List<String> firefoxpassedTestCases, List<String> firefoxfailedTestCases,List<String> iepassedTestCases, List<String> iefailedTestCases,List<String> chromepassedTestCases, List<String> chromefailedTestCases){
		try{
		
			
			System.out.println("firefox"+ firefoxpassedTestCases);
			System.out.println("firefox"+ firefoxfailedTestCases);
			System.out.println("ie"+ iepassedTestCases);
			System.out.println("ie"+ iefailedTestCases);
			System.out.println("chrome"+ chromepassedTestCases);
			System.out.println("chrome"+ chromefailedTestCases);
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution reports</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Execution "+currentExecutionCount+" </p>");
	        builder.append("<table  align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	      
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">Total Test Cases</th>");
	        builder.append("<th align=\"center\">Passed Test Cases</th>");
	        builder.append("<th align=\"center\">Falied Test Cases</th>");
	        builder.append("</tr>");
	        int totalTestCasesCount = firefoxpassedTestCases.size()+firefoxfailedTestCases.size()+iepassedTestCases.size()+iefailedTestCases.size()+chromepassedTestCases.size()+chromefailedTestCases.size();
	        int passedTestCasesCount = firefoxpassedTestCases.size()+iepassedTestCases.size()+chromepassedTestCases.size();
	        int failedTestCasesCount = firefoxfailedTestCases.size()+iefailedTestCases.size()+chromefailedTestCases.size();
	        
	        builder.append("<tr>");
	        builder.append("<td align=\"center\"> " + totalTestCasesCount + "</td>"); 
	        if(passedTestCasesCount>0){
	        	writeToHtml_PassedTestCases(firefoxpassedTestCases,iepassedTestCases,chromepassedTestCases);
	        	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_PassedtestCases.html\">"+passedTestCasesCount+"</a> </td>");
	        }else{
	        	 builder.append("<td align=\"center\"> 0 </td>");
	        }
	        
	        if(failedTestCasesCount>0){
	        	writeToHtml_FailedTestCases(firefoxfailedTestCases,iefailedTestCases,chromefailedTestCases);
	        	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_FailedtestCases.html\">"+failedTestCasesCount+"</a> </td>");
	        }else{
	        	 builder.append("<td align=\"center\"> 0 </td>");
	        }	       
	        
	        builder.append(" </tr>  "); 	     
	        builder.append("</table>");
	        
	     //browser wise reporting
	        
	        builder.append("<p align=\"center\">  Browser Wise Execution Reports for Execution "+currentExecutionCount+" </p>");
	       
	        int firefoxTestCases = firefoxpassedTestCases.size()+firefoxfailedTestCases.size();
	        if(firefoxTestCases>0){
	        builder.append("<p align=\"center\"> Firefox </p>");
	        builder.append("<table  align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	      	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">Total Test Cases</th>");
	        builder.append("<th align=\"center\">Passed Test Cases</th>");
	        builder.append("<th align=\"center\">Falied Test Cases</th>");
	        builder.append("</tr>");
	    
	        builder.append("<tr>");
	        builder.append("<td align=\"center\"> " + firefoxTestCases + "</td>"); 
	        if(firefoxpassedTestCases.size()>0){
	        	writeToHtml_firefoxPassedTestCases(firefoxpassedTestCases);
	           	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_FirefoxPassedtestCases.html\">"+firefoxpassedTestCases.size()+"</a> </td>");
	        }else{
	        	 builder.append("<td align=\"center\"> 0 </td>");
	        }
	        
	        if(firefoxfailedTestCases.size()>0){
	        	writeToHtml_firefoxFailedTestCases(firefoxfailedTestCases);
	        	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_FirefoxFailedtestCases.html\">"+firefoxfailedTestCases.size()+"</a> </td>");
	        }else{
	        	 builder.append("<td align=\"center\"> 0 </td>");
	        }
	       
	        
	        builder.append(" </tr>  "); 
	     
	        builder.append("</table>");
	        }
	        
	        int ieTestCases = iepassedTestCases.size()+iefailedTestCases.size();
	        if(ieTestCases>0){
	        builder.append("<p align=\"center\"> Internet Explorer </p>");
	        builder.append("<table  align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	      	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">Total Test Cases</th>");
	        builder.append("<th align=\"center\">Passed Test Cases</th>");
	        builder.append("<th align=\"center\">Falied Test Cases</th>");
	        builder.append("</tr>");
	        builder.append("<tr>");
	        builder.append("<td align=\"center\"> " + ieTestCases + "</td>"); 
	        if(iepassedTestCases.size()>0){
	        	writeToHtml_iePassedTestCases(iepassedTestCases);
	           	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_iePassedtestCases.html\">"+iepassedTestCases.size()+"</a> </td>");
	        }else{
	        	 builder.append("<td align=\"center\"> 0 </td>");
	        }
	        
	        if(iefailedTestCases.size()>0){
	        	writeToHtml_ieFailedTestCases(iefailedTestCases);
	        	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_ieFailedtestCases.html\">"+iefailedTestCases.size()+"</a> </td>");
	        }else{
	        	 builder.append("<td align=\"center\"> 0 </td>");
	        }
	       
	        
	        builder.append(" </tr>  "); 
	     
	        builder.append("</table>");
	        }
	        
	        int chromeTestCases = chromepassedTestCases.size()+chromefailedTestCases.size();
	        if(chromeTestCases>0){
		        builder.append("<p align=\"center\"> Chrome </p>");
		        builder.append("<table  align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
		      	        
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">Total Test Cases</th>");
		        builder.append("<th align=\"center\">Passed Test Cases</th>");
		        builder.append("<th align=\"center\">Falied Test Cases</th>");
		        builder.append("</tr>");
		        builder.append("<tr>");
		        builder.append("<td align=\"center\"> " + chromeTestCases + "</td>"); 
		        if(chromepassedTestCases.size()>0){
		        	writeToHtml_chromePassedTestCases(chromepassedTestCases);
		           	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_chromePassedtestCases.html\">"+chromepassedTestCases.size()+"</a> </td>");
		        }else{
		        	 builder.append("<td align=\"center\"> 0 </td>");
		        }
		        
		        if(chromefailedTestCases.size()>0){
		        	writeToHtml_chromeFailedTestCases(chromefailedTestCases);
		        	 builder.append("<td align=\"center\"> <a href=\"../Execution_"+currentExecutionCount+"/htmlLog_chromeFailedtestCases.html\">"+chromefailedTestCases.size()+"</a> </td>");
		        }else{
		        	 builder.append("<td align=\"center\"> 0 </td>");
		        }
		       
		        
		        builder.append(" </tr>  "); 
		     
		        builder.append("</table>");
		        }
	        
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	        bw.write(html);
	        bw.close();
	        
	        
	       

	}catch(Exception e){
		e.printStackTrace();
	}
}


	
	public static void writeToHtml_PassedTestCases(List<String> firefoxTestCases,List<String> ieTestCases,List<String> chromeTestCases){
		try{
		
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog_PassedtestCases.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution Reports for Passed Test Cases</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Passed Test Cases  </p>");
	        builder.append("<table align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	        		       
	       
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">S.No.</th>");
	        builder.append("<th align=\"center\">Execution Date</th>");
	        builder.append("<th align=\"center\">Test Case Name</th>");
	        builder.append("<th align=\"center\">Execution Time</th>");
	        builder.append("<th align=\"center\">Browser Name</th>");
	        builder.append("</tr>");
	        
	    
	       
	        if(firefoxTestCases.size()>0){
	        	
	        	Iterator<String> passedIterator = firefoxTestCases.iterator();
		        int testCaseNumber=1;
		        while(passedIterator.hasNext()){
		        String testCaseName = passedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
				String ExecutionTime = str[2];
			
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append("<th align=\"center\">Firefox</th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        }
	        
	        if(ieTestCases.size()>0){
	        	
	        	Iterator<String> passedIterator = ieTestCases.iterator();
		        
	        	int testCaseNumber=1;
		        while(passedIterator.hasNext()){
		        String testCaseName = passedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
		    	String ExecutionTime = str[2];
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append("<th align=\"center\">Internet Explorer</th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        	
	        }
	        
	        if(chromeTestCases.size()>0){
	        	
	        	Iterator<String> passedIterator = chromeTestCases.iterator();
		        
	        	int testCaseNumber=1;
		        while(passedIterator.hasNext()){
		        String testCaseName = passedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
		    	String ExecutionTime = str[2];
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append("<th align=\"center\">Chrome</th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        	
	        }
	        
	        
	        builder.append("</table>");
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	      
	        bw.write(html);
	        
	        bw.close();

	}catch(Exception e){
		e.printStackTrace();
	}
}
	public static void writeToHtml_FailedTestCases(List<String> firefoxTestCases,List<String> ieTestCases,List<String> chromeTestCases){
		try{
		
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog_FailedtestCases.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution Reports for Failed Test Cases</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Failed Test Cases  </p>");
	        builder.append("<table align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	        		       
	       
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">S.No.</th>");
	        builder.append("<th align=\"center\">Execution Date</th>");
	        builder.append("<th align=\"center\">Test Case Name</th>");
	        builder.append("<th align=\"center\">Execution Time</th>");
	        builder.append("<th align=\"center\">Browser Name</th>");
	        builder.append("<th align=\"center\">Failure Reason</th>");
	        builder.append("</tr>");
	     
	        if(firefoxTestCases.size()>0){
	        	
	        	Iterator<String> failedIterator = firefoxTestCases.iterator();
		        
	        	int testCaseNumber=1;
		        while(failedIterator.hasNext()){
		        String testCaseName = failedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
		    	String ExecutionTime = str[2];
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append("<th align=\"center\">Firefox</th>");
		        builder.append("<th align=\"center\">Exception</th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        }
	        
	        if(ieTestCases.size()>0){
	        	
	        	Iterator<String> failedIterator = ieTestCases.iterator();
		        
	        	int testCaseNumber=1;
		        while(failedIterator.hasNext()){
		        String testCaseName = failedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				

		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
		    	String ExecutionTime = str[2];
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append("<th align=\"center\">Internet Explorer</th>");
		        builder.append("<th align=\"center\">Exception</th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        }
	        
	        if(chromeTestCases.size()>0){
	        	
	        	Iterator<String> failedIterator = chromeTestCases.iterator();
		        
	        	int testCaseNumber=1;
		        while(failedIterator.hasNext()){
		        String testCaseName = failedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				

		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
		    	String ExecutionTime = str[2];
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append("<th align=\"center\">Chrome</th>");
		        builder.append("<th align=\"center\">Exception</th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }		
	        	
	        }
	        builder.append("</table>");
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	      
	        bw.write(html);
	        
	        bw.close();

	}catch(Exception e){
		e.printStackTrace();
	}
}
	public static void writeToHtml_firefoxPassedTestCases(List<String> browserTestCases){
		try{
		
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog_FirefoxPassedtestCases.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution Reports for Passed Test Cases</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Passed Test Cases  </p>");
	        builder.append("<table align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	        		       
	       
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">S.No.</th>");
	        builder.append("<th align=\"center\">Execution Date</th>");
	        builder.append("<th align=\"center\">Test Case Name</th>");
	        builder.append("<th align=\"center\">Execution Time</th>");	       
	        builder.append("</tr>");
	        
	    
	       
	        if(browserTestCases.size()>0){
	        	
	        	Iterator<String> passedIterator = browserTestCases.iterator();
		        int testCaseNumber=1;
		        while(passedIterator.hasNext()){
		        String testCaseName = passedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
				String ExecutionTime = str[2];
			
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");		       
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        }
	    
	        builder.append("</table>");
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	      
	        bw.write(html);
	        
	        bw.close();

	}catch(Exception e){
		e.printStackTrace();
	}
}
	public static void writeToHtml_firefoxFailedTestCases(List<String> browserTestCases){
		try{
		
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog_FirefoxFailedtestCases.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution Reports for Failed Test Cases</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Failed Test Cases  </p>");
	        builder.append("<table align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	        		       
	       
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">S.No.</th>");
	        builder.append("<th align=\"center\">Execution Date</th>");
	        builder.append("<th align=\"center\">Test Case Name</th>");
	        builder.append("<th align=\"center\">Execution Time</th>");
	        builder.append("<th align=\"center\">Failure Reason</th>");
	        builder.append("</tr>");
	     
	        if(browserTestCases.size()>0){
	        	
	        	Iterator<String> failedIterator = browserTestCases.iterator();
		        
	        	int testCaseNumber=1;
		        while(failedIterator.hasNext()){
		        String testCaseName = failedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
		    	String ExecutionTime = str[2];
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append("<th align=\"center\">Exception</th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        }
	        
	    
	        builder.append("</table>");
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	      
	        bw.write(html);
	        
	        bw.close();

	}catch(Exception e){
		e.printStackTrace();
	}
}
	
	public static void writeToHtml_iePassedTestCases(List<String> browserTestCases){
		try{
		
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog_iePassedtestCases.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution Reports for Passed Test Cases</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Passed Test Cases  </p>");
	        builder.append("<table align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	        		       
	       
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">S.No.</th>");
	        builder.append("<th align=\"center\">Execution Date</th>");
	        builder.append("<th align=\"center\">Test Case Name</th>");
	        builder.append("<th align=\"center\">Execution Time</th>");
	        builder.append("</tr>");
	        
	    
	       
	        if(browserTestCases.size()>0){
	        	
	        	Iterator<String> passedIterator = browserTestCases.iterator();
		        int testCaseNumber=1;
		        while(passedIterator.hasNext()){
		        String testCaseName = passedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
				String ExecutionTime = str[2];
			
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        }
	    
	        builder.append("</table>");
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	      
	        bw.write(html);
	        
	        bw.close();

	}catch(Exception e){
		e.printStackTrace();
	}
}
	public static void writeToHtml_ieFailedTestCases(List<String> browserTestCases){
		try{
		
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog_ieFailedtestCases.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution Reports for Failed Test Cases</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Failed Test Cases  </p>");
	        builder.append("<table align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	        		       
	       
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">S.No.</th>");
	        builder.append("<th align=\"center\">Execution Date</th>");
	        builder.append("<th align=\"center\">Test Case Name</th>");
	        builder.append("<th align=\"center\">Execution Time</th>");
	        builder.append("<th align=\"center\">Failure Reason</th>");
	        builder.append("</tr>");
	     
	        if(browserTestCases.size()>0){
	        	
	        	Iterator<String> failedIterator = browserTestCases.iterator();
		        
	        	int testCaseNumber=1;
		        while(failedIterator.hasNext()){
		        String testCaseName = failedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
		    	String ExecutionTime = str[2];
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append("<th align=\"center\">Exception</th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        }
	        
	    
	        builder.append("</table>");
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	      
	        bw.write(html);
	        
	        bw.close();

	}catch(Exception e){
		e.printStackTrace();
	}
}
	
	public static void writeToHtml_chromePassedTestCases(List<String> browserTestCases){
		try{
		
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog_chromePassedtestCases.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution Reports for Passed Test Cases</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Passed Test Cases  </p>");
	        builder.append("<table align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	        		       
	       
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">S.No.</th>");
	        builder.append("<th align=\"center\">Execution Date</th>");
	        builder.append("<th align=\"center\">Test Case Name</th>");
	        builder.append("<th align=\"center\">Execution Time</th>");
	        builder.append("</tr>");
	        
	    
	       
	        if(browserTestCases.size()>0){
	        	
	        	Iterator<String> passedIterator = browserTestCases.iterator();
		        int testCaseNumber=1;
		        while(passedIterator.hasNext()){
		        String testCaseName = passedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
				String ExecutionTime = str[2];
			
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        }
	    
	        builder.append("</table>");
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	      
	        bw.write(html);
	        
	        bw.close();

	}catch(Exception e){
		e.printStackTrace();
	}
}
	public static void writeToHtml_chromeFailedTestCases(List<String> browserTestCases){
		try{
		
			File file = new File(EXECUTIONS_PATH+"\\Execution_"+currentExecutionCount+"\\htmlLog_chromeFailedtestCases.html");
	
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	        StringBuilder builder = new StringBuilder();
	        builder.append("<!DOCTYPE html>");
	        builder.append("<html lang=\"en\">");
	        builder.append("<head><title>Execution Reports for Failed Test Cases</title></head>"); 
	        builder.append("<body><h1>");
	        builder.append("<p align=\"center\">  Execution Reports for Failed Test Cases  </p>");
	        builder.append("<table align=\"center\" border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
	        		       
	       
	        
	        builder.append("<tr>");
	        builder.append("<th align=\"center\">S.No.</th>");
	        builder.append("<th align=\"center\">Execution Date</th>");
	        builder.append("<th align=\"center\">Test Case Name</th>");
	        builder.append("<th align=\"center\">Execution Time</th>");
	        builder.append("<th align=\"center\">Failure Reason</th>");
	        builder.append("</tr>");
	     
	        if(browserTestCases.size()>0){
	        	
	        	Iterator<String> failedIterator = browserTestCases.iterator();
		        
	        	int testCaseNumber=1;
		        while(failedIterator.hasNext()){
		        String testCaseName = failedIterator.next();
		        String[] str = testCaseName.split("\\&&");
				
		        String testCase = str[0];
		    	String ExecutionStartTime=str[1];
		    	String ExecutionTime = str[2];
				
		        builder.append("<tr>");
		        builder.append("<th align=\"center\">"+testCaseNumber+"</th>");
		        builder.append("<th align=\"center\">"+ExecutionStartTime+"</th>");
		        builder.append("<th align=\"center\">"+testCase+"</th>");
		        builder.append("<th align=\"center\">"+(Integer.parseInt(ExecutionTime)/1000)+" Sec </th>");
		        builder.append("<th align=\"center\">Exception</th>");
		        builder.append(" </tr>  "); 
		        testCaseNumber++;
		        }
	        	
	        }
	        
	    
	        builder.append("</table>");
	        builder.append("</h1></body>");
	        builder.append("</html>");
	        String html = builder.toString();
	      
	        bw.write(html);
	        
	        bw.close();

	}catch(Exception e){
		e.printStackTrace();
	}
}
}
