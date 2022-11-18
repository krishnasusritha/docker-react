package supportlibraries;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import supportlibraries.com.ncr.framework.*;
import supportlibraries.com.ncr.framework.ReportThemeFactory.Theme;
import supportlibraries.com.ncr.framework.selenium.*;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.*;


/**
 * Driver script class which encapsulates the core logic of the framework
 * @author NCR
 */
public class DriverScript
{
	private List<String> businessFlowData;
	private int currentIteration, currentSubIteration;
	private Date startTime, endTime;
	
	private CraftDataTable dataTable;
	private ReportSettings reportSettings;
	private SeleniumReport report;
	private WebDriver driver;
	//private IMobileDevice device;
	private ScriptHelper scriptHelper;
	
	private Properties properties;
	private ExecutionMode executionMode;
	private final FrameworkParameters frameworkParameters =
										FrameworkParameters.getInstance();
	private Boolean testExecutedInUnitTestFramework = true;
	private Boolean linkScreenshotsToTestLog = true;
	private String testStatus;
	
	private final SeleniumTestParameters testParameters;
	private String reportPath;
	
	/**
	 * Function to indicate whether the test is executed in JUnit/TestNG or not
	 * @param testExecutedInUnitTestFramework Boolean variable indicating whether the test is executed in JUnit/TestNG
	 */
	public void setTestExecutedInUnitTestFramework(Boolean testExecutedInUnitTestFramework)
	{
		this.testExecutedInUnitTestFramework = testExecutedInUnitTestFramework;
	}
	
	/**
	 * Function to confugure the linking of screenshots to the corresponding test log
	 * @param linkScreenshotsToTestLog Boolean variable indicating whether screenshots should be linked to the corresponding test log
	 */
	public void setLinkScreenshotsToTestLog(Boolean linkScreenshotsToTestLog)
	{
		this.linkScreenshotsToTestLog = linkScreenshotsToTestLog;
	}
	
	/**
	 * Function to get the status of the test case executed
	 * @return The test status
	 */
	public String getTestStatus()
	{
		return testStatus;
	}
	
	
	/**
	 * DriverScript constructor
	 * @param testParameters A {@link SeleniumTestParameters} object
	 */
	public DriverScript(SeleniumTestParameters testParameters)
	{
		this.testParameters = testParameters;
	}
	
	/**
	 * Function to execute the given test case
	 * @throws AWTException 
	 */
	public void driveTestExecution() throws AWTException
	{
		startUp();
		System.out.println("driveTestExecution : 1");
		initializeTestIterations();
		System.out.println("driveTestExecution : 2");
		initializeWebDriver();
		System.out.println("driveTestExecution : 3");
		initializeTestReport();
		System.out.println("driveTestExecution : 4");
		initializeDatatable();
		System.out.println("driveTestExecution : 5");
		initializeTestScript();
		System.out.println("driveTestExecution : 6");
		executeTestIterations();
		System.out.println("driveTestExecution : 7");
		quitWebDriver();
		System.out.println("driveTestExecution : 8");
		wrapUp();
		System.out.println("driveTestExecution : 9");
	}
	
	private void startUp()
	{
		startTime = Util.getCurrentTime();
		
		properties = Settings.getInstance();
		
		setDefaultTestParameters();
	}
	
	private void setDefaultTestParameters()
	{
		if (testParameters.getIterationMode() == null) {
			testParameters.setIterationMode(IterationOptions.RunAllIterations);
		}
		
		if(System.getProperty("Browser") != null) {
			testParameters.setBrowser(Browser.valueOf(System.getProperty("Browser")));
		} else {
			if (testParameters.getBrowser() == null) {
				testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));
			}
		}
		
		if(System.getProperty("BrowserVersion") !=null) {
			testParameters.setBrowserVersion(System.getProperty("BrowserVersion"));
		}
		
		if(System.getProperty("Platform") != null) {
			testParameters.setPlatform(Platform.valueOf(System.getProperty("Platform")));
		} else {
			if (testParameters.getPlatform() == null) {
				testParameters.setPlatform(Platform.valueOf(properties.getProperty("DefaultPlatform")));
			}
		}
		
		/*if(System.getProperty("PerfectoDeviceId") != null) { 
			testParameters.setPerfectoDeviceId(System.getProperty("PerfectoDeviceId")); 
		} else { 
			if (testParameters.getPerfectoDeviceId() == null) { 
				testParameters.setPerfectoDeviceId(properties.getProperty("DefaultDeviceId")); 
			}
		}*/
	}
	
	private void initializeTestIterations()
	{
		System.out.println("starting initializeTestIterations");
		System.out.println(testParameters.getIterationMode());
		switch(testParameters.getIterationMode()) {
		case RunAllIterations:
		
			System.out.println("Case 1");
			String datatablePath = frameworkParameters.getRelativePath() +
									Util.getFileSeparator() + "Datatables";
			System.out.println("datatablePath : "+datatablePath);
			System.out.println("current scenario : "+testParameters.getCurrentScenario());
			System.out.println(properties.getProperty("DefaultDataSheet"));
			
			
		/*	ExcelDataAccess testDataAccess =
					new ExcelDataAccess(datatablePath, testParameters.getCurrentScenario());*/
			
			ExcelDataAccess testDataAccess =
					new ExcelDataAccess(datatablePath, "Booking");
			testDataAccess.setDatasheetName(properties.getProperty("DefaultDataSheet"));
			
			int startRowNum = testDataAccess.getRowNum(testParameters.getCurrentTestcase(), 0);
			int nTestcaseRows = testDataAccess.getRowCount(testParameters.getCurrentTestcase(), 0, startRowNum);
			int nSubIterations = testDataAccess.getRowCount("1", 1, startRowNum);	// Assumption: Every test case will have at least one iteration
			int nIterations = nTestcaseRows / nSubIterations;
			testParameters.setEndIteration(nIterations);
			
			currentIteration = 1;
			break;
			
		case RunOneIterationOnly:
			System.out.println("Case 2");
			currentIteration = 1;
			break;
			
		case RunRangeOfIterations:
			System.out.println("Case 3");
			if(testParameters.getStartIteration() > testParameters.getEndIteration()) {
				throw new FrameworkException("Error","StartIteration cannot be greater than EndIteration!");
			}
			currentIteration = testParameters.getStartIteration();
			break;
			
		default:
			System.out.println("Case 4");
			throw new FrameworkException("Unhandled Iteration Mode!");
		}
	}
	
	private void initializeWebDriver()
	{
		executionMode = ExecutionMode.valueOf(properties.getProperty("ExecutionMode"));
		System.out.println("Execution Mode : "+ executionMode);
		switch(executionMode) {
		case Local:
			
			System.out.println("Local");
			driver = WebDriverFactory.getDriver(testParameters.getBrowser());
			break;
			
		case Remote:
			System.out.println("Remote");
			driver = WebDriverFactory.getDriver(testParameters.getBrowser(),
													properties.getProperty("RemoteUrl"));
			break;
			
		case Grid:
			driver = WebDriverFactory.getDriver(testParameters.getBrowser(),
													testParameters.getBrowserVersion(),
													testParameters.getPlatform(),
													properties.getProperty("RemoteUrl"));
			break;
			
		/*case Perfecto:
			IMobileDevice device =
						WebDriverFactory.getDevice(testParameters.getPerfectoDeviceId());
			driver = device.getDOMDriver();
			break;*/
			
		default:
			throw new FrameworkException("Unhandled Execution Mode!");
		}
		
		driver.manage().window().maximize();
	}
	
	private void initializeTestReport()
	{
		
			System.out.println("Test1");
		initializeReportSettings();
		System.out.println("Test2");
		ReportTheme reportTheme =
				ReportThemeFactory.getReportsTheme(Theme.valueOf(properties.getProperty("ReportsTheme")));
		System.out.println("Test3");
		report = new SeleniumReport(reportSettings, reportTheme);
		System.out.println("Test4");
		report.initialize();
		System.out.println("Test5");	
		report.setDriver(driver);
		System.out.println("Test6");
		report.initializeTestLog();
		System.out.println("Test7");
		createTestLogHeader();
	
	}
	
	private void initializeReportSettings()
	{
		if(System.getProperty("ReportPath") != null) {
			reportPath = System.getProperty("ReportPath");
		} else {
			reportPath = TimeStamp.getInstance();
		}
		
		reportSettings = new ReportSettings(reportPath,
											testParameters.getCurrentScenario() +
											"_" + testParameters.getCurrentTestcase());
		
		reportSettings.setDateFormatString(properties.getProperty("DateFormatString"));
		reportSettings.setLogLevel(Integer.parseInt(properties.getProperty("LogLevel")));
		reportSettings.setProjectName(properties.getProperty("ProjectName"));
		reportSettings.generateExcelReports =
				Boolean.parseBoolean(properties.getProperty("ExcelReport"));
		reportSettings.generateHtmlReports =
				Boolean.parseBoolean(properties.getProperty("HtmlReport"));
		reportSettings.takeScreenshotFailedStep =
				Boolean.parseBoolean(properties.getProperty("TakeScreenshotFailedStep"));
		reportSettings.takeScreenshotPassedStep =
				Boolean.parseBoolean(properties.getProperty("TakeScreenshotPassedStep"));
		reportSettings.consolidateScreenshotsInWordDoc = 
				Boolean.parseBoolean(properties.getProperty("ConsolidateScreenshotsInWordDoc"));
		if (testParameters.getBrowser().equals(Browser.HtmlUnit)) {
			// Screenshots not supported in headless mode
			reportSettings.linkScreenshotsToTestLog = false;
		} else {
			reportSettings.linkScreenshotsToTestLog = this.linkScreenshotsToTestLog;
		}
	}
	
	private void createTestLogHeader()
	{
		report.addTestLogHeading(reportSettings.getProjectName() +
									" - " + reportSettings.getReportName() +
									" Automation Execution Results");
		report.addTestLogSubHeading("Date & Time",
										": " + Util.getCurrentFormattedTime(properties.getProperty("DateFormatString")),
										"Iteration Mode", ": " + testParameters.getIterationMode());
		report.addTestLogSubHeading("Start Iteration", ": " + testParameters.getStartIteration(),
									"End Iteration", ": " + testParameters.getEndIteration());
		
		switch(executionMode) {
		case Local:
			report.addTestLogSubHeading("Browser", ": " + testParameters.getBrowser(),
					"Executed on", ": " + "Local Machine");
			break;
			
		case Remote:
			report.addTestLogSubHeading("Browser", ": " + testParameters.getBrowser(),
					"Executed on", ": " + properties.getProperty("RemoteUrl"));
			break;
			
		case Grid:
			String browserVersion = testParameters.getBrowserVersion();
			if (browserVersion == null) {
				browserVersion = "Not specified";
			}
			report.addTestLogSubHeading("Browser", ": " + testParameters.getBrowser(),
					"Version", ": " + browserVersion);
			report.addTestLogSubHeading("Platform", ": " + testParameters.getPlatform().toString(),
					"Executed on", ": " + "Grid @ " + properties.getProperty("RemoteUrl"));
			break;
			
		/*case Perfecto: 
            report.addTestLogSubHeading("Device ID", ": " + testParameters.getPerfectoDeviceId(), 
                            "Executed on", ": " + "Perfecto MobileCloud"); 
            report.addTestLogSubHeading("Perfecto Host", ": " + properties.getProperty("PerfectoHost"), 
                            "Perfecto User", ": " + properties.getProperty("PerfectoUser")); 
            break;*/
            
		default:
			throw new FrameworkException("Unhandled Execution Mode!");
		}
		
		report.addTestLogTableHeadings();
	}
	
	private void initializeDatatable()
	{
		try {
		String datatablePath = frameworkParameters.getRelativePath() +
									Util.getFileSeparator() + "Datatables";
		
		String runTimeDatatablePath;
		Boolean includeTestDataInReport =
				Boolean.parseBoolean(properties.getProperty("IncludeTestDataInReport"));
		if (includeTestDataInReport) {
			runTimeDatatablePath = reportPath + Util.getFileSeparator() + "Datatables";
			
			File runTimeDatatable = new File(runTimeDatatablePath + Util.getFileSeparator() +
												"Booking" + ".xls");
			if (!runTimeDatatable.exists()) {
				File datatable = new File(datatablePath + Util.getFileSeparator() +
											"Booking" + ".xls");
				
				try {
					FileUtils.copyFile(datatable, runTimeDatatable);
				} catch (IOException e) {
					e.printStackTrace();
					throw new FrameworkException("Error in creating run-time datatable: Copying the datatable failed...");
				}
			}
			
			File runTimeCommonDatatable = new File(runTimeDatatablePath +
													Util.getFileSeparator() +
													"Common Testdata.xls");
			if (!runTimeCommonDatatable.exists()) {
				File commonDatatable = new File(datatablePath +
										Util.getFileSeparator() + "Common Testdata.xls");
				
				try {
					FileUtils.copyFile(commonDatatable, runTimeCommonDatatable);
				} catch (IOException e) {
					e.printStackTrace();
					throw new FrameworkException("Error in creating run-time datatable: Copying the common datatable failed...");
				}
			}
		} else {
			runTimeDatatablePath = datatablePath;
		}
		
		dataTable = new CraftDataTable(runTimeDatatablePath, "Booking");
		dataTable.setDataReferenceIdentifier(properties.getProperty("DataReferenceIdentifier"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initializeTestScript()
	{
		scriptHelper = new ScriptHelper(dataTable, report, driver);
		
		businessFlowData = getBusinessFlow();
	}
	
	private List<String> getBusinessFlow()
	{
		try {
		/*ExcelDataAccess businessFlowAccess =
				new ExcelDataAccess(frameworkParameters.getRelativePath() +
										Util.getFileSeparator() + "Datatables",
										testParameters.getCurrentScenario());*/
		
		ExcelDataAccess businessFlowAccess =
				new ExcelDataAccess(frameworkParameters.getRelativePath() +
										Util.getFileSeparator() + "Datatables",
										"Booking");
		
		businessFlowAccess.setDatasheetName("Business_Flow");
		
		int rowNum = businessFlowAccess.getRowNum(testParameters.getCurrentTestcase(), 0);
		if (rowNum == -1) {
			throw new FrameworkException("The test case \"" + testParameters.getCurrentTestcase() + "\" is not found in the Business Flow sheet!");
		}
		
		String dataValue;
		businessFlowData = new ArrayList<String>();
		int currentColumnNum = 1;
		while (true) {
			dataValue = businessFlowAccess.getValue(rowNum, currentColumnNum);
			if (dataValue.equals("")) {
				break;
			}
			businessFlowData.add(dataValue);
			currentColumnNum++;
		}
		
		if (businessFlowData.isEmpty()) {
			throw new FrameworkException("No business flow found against the test case \"" + testParameters.getCurrentTestcase() + "\"");
		}
		
		return businessFlowData;
		}catch(Exception e) {
			System.out.println(e);
		}
		return businessFlowData;
	}
	
	private void executeTestIterations() throws AWTException
	{
		System.out.println("starting executeTestIterations");
		while(currentIteration <= testParameters.getEndIteration()) {
			report.addTestLogSection("Iteration: " + Integer.toString(currentIteration));
			
			// Evaluate each test iteration for any errors
			try {
				executeTestcase(businessFlowData);
			} catch (FrameworkException fx) {
				exceptionHandler(fx, fx.errorName);
			} catch (InvocationTargetException ix) {
				exceptionHandler((Exception)ix.getCause(), "Error");
			} catch (Exception ex) {
				exceptionHandler(ex, "Error");
			}
			
			currentIteration++;
		}
		System.out.println("ending executeTestIterations");
	}
	
	private void executeTestcase(List<String> businessFlowData)
			throws IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, InstantiationException
	{
		
		try {
		System.out.println("Starting executeTestcase");
		HashMap<String, Integer> keywordDirectory = new HashMap<String, Integer>();
		
		for (int currentKeywordNum = 0; currentKeywordNum < businessFlowData.size(); currentKeywordNum++) {
			System.out.println("Entered 1st for loop"+currentKeywordNum);
			String[] currentFlowData = businessFlowData.get(currentKeywordNum).split(",");
			String currentKeyword = currentFlowData[0];
			
			int nKeywordIterations;
			if(currentFlowData.length > 1) {
				nKeywordIterations = Integer.parseInt(currentFlowData[1]);
			} else {
				nKeywordIterations = 1;
			}
			
			for (int currentKeywordIteration = 0; currentKeywordIteration < nKeywordIterations; currentKeywordIteration++) {
				System.out.println("Entered 2nd for loop"+currentKeywordIteration);
				if(keywordDirectory.containsKey(currentKeyword)) {
					System.out.println("executeTestcase : 1");
					keywordDirectory.put(currentKeyword, keywordDirectory.get(currentKeyword) + 1);
				} else {
					System.out.println("executeTestcase : 2");
					keywordDirectory.put(currentKeyword, 1);
				}
				System.out.println("executeTestcase : 3");
				currentSubIteration = keywordDirectory.get(currentKeyword);
				System.out.println("executeTestcase : 4");
				System.out.println(testParameters.getCurrentTestcase());
				System.out.println(currentIteration);
				System.out.println(currentSubIteration);
				System.out.println("Data table : " + dataTable);
				dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration, currentSubIteration);
				System.out.println("executeTestcase : 5");
				if (currentSubIteration > 1) {
					System.out.println("executeTestcase : 6");
					report.addTestLogSubSection(currentKeyword + " (Sub-Iteration: " + currentSubIteration + ")");
				} else {
					System.out.println("executeTestcase : 7");
					report.addTestLogSubSection(currentKeyword);
				}
				System.out.println("executeTestcase : 8");
				invokeBusinessComponent(currentKeyword);
			}
		}
		}catch(Exception e) {
			
			System.out.println(e);
			
		}
	}
	
	private void invokeBusinessComponent(String currentKeyword)
			throws IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, InstantiationException
	{
		try {
		System.out.println("Entering invokeBusinessComponent");
		Boolean isMethodFound = false;
		final String CLASS_FILE_EXTENSION = ".class";
		File[] packageDirectories = {
			new File(frameworkParameters.getRelativePath() +
						Util.getFileSeparator() + "businesscomponents"),
			new File(frameworkParameters.getRelativePath() +
						Util.getFileSeparator() + "componentgroups") };
		
		for (File packageDirectory : packageDirectories)
		{
			File[] packageFiles = packageDirectory.listFiles();
			String packageName = packageDirectory.getName();
			
			for (int i = 0; i < packageFiles.length; i++) {
				File packageFile = packageFiles[i];
				String fileName = packageFile.getName();
				
				// We only want the .class files
				if (fileName.endsWith(CLASS_FILE_EXTENSION)) {
					// Remove the .class extension to get the class name
					String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION.length());
					
					Class<?> reusableComponents = Class.forName(packageName+"." + className);
					Method executeComponent;
					
					try {
						// Convert the first letter of the method to lowercase (in line with java naming conventions)
						currentKeyword = currentKeyword.substring(0, 1).toLowerCase() + currentKeyword.substring(1);
						executeComponent = reusableComponents.getMethod(currentKeyword, (Class<?>[]) null);
					} catch(NoSuchMethodException ex) {
						// If the method is not found in this class, search the next class
						continue;
					}
					
					isMethodFound = true;
					
					Constructor<?> ctor = reusableComponents.getDeclaredConstructors()[0];
					Object businessComponent = ctor.newInstance(scriptHelper);
					
					executeComponent.invoke(businessComponent, (Object[]) null);
					
					break;
				} 
			}
		}
		
		if(!isMethodFound) {
			throw new FrameworkException("Keyword " + currentKeyword + 
											" not found within any class " +
											"inside the businesscomponents package");
		}
		
		System.out.println("Ending invokeBusinessComponent");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private void exceptionHandler(Exception ex, String exceptionName) throws AWTException
	{
		// Error reporting
		String exceptionDescription = ex.getMessage();
		if(exceptionDescription == null) {
			exceptionDescription = ex.toString();
		}
		
		if(ex.getCause() != null) {
			report.updateTestLog(exceptionName, exceptionDescription + " <b>Caused by: </b>" +
																		ex.getCause(), Status.FAIL);
		} else {
			report.updateTestLog(exceptionName, exceptionDescription, Status.FAIL);
		}
		ex.printStackTrace();
		
		// Error response
		if (frameworkParameters.getStopExecution()) {
			report.updateTestLog("CRAFT Info",
					"Test execution terminated by user! All subsequent tests aborted...",
					Status.DONE);
			currentIteration = testParameters.getEndIteration();
		} else {
			OnError onError = OnError.valueOf(properties.getProperty("OnError"));
			switch(onError) {
			// Stop option is not relevant when run from QC
			case NextIteration:
				report.updateTestLog("CRAFT Info",
						"Test case iteration terminated by user! Proceeding to next iteration (if applicable)...",
						Status.DONE);
				break;
				
			case NextTestCase:
				report.updateTestLog("CRAFT Info",
						"Test case terminated by user! Proceeding to next test case (if applicable)...",
						Status.DONE);
				currentIteration = testParameters.getEndIteration();
				break;
				
			case Stop:
				frameworkParameters.setStopExecution(true);
				report.updateTestLog("CRAFT Info",
						"Test execution terminated by user! All subsequent tests aborted...",
						Status.DONE);
				currentIteration = testParameters.getEndIteration();
				break;
				
			default:
				throw new FrameworkException("Unhandled OnError option!");
			}
		}
	}
	
	private void quitWebDriver()
	{
		driver.quit();
		
		/*if (executionMode.equals(ExecutionMode.Perfecto)) { 
			device.close(); 
		}*/
	}
	
	private void wrapUp()
	{
		endTime = Util.getCurrentTime();
		closeTestReport();
		
		testStatus = report.getTestStatus();
		
		if(testExecutedInUnitTestFramework && testStatus.equalsIgnoreCase("Failed")) {
			Assert.fail(report.getFailureDescription());
		}
	}
	
	private void closeTestReport()
	{
		String executionTime = Util.getTimeDifference(startTime, endTime);
		report.addTestLogFooter(executionTime);
		
		if (reportSettings.consolidateScreenshotsInWordDoc) {
			report.consolidateScreenshotsInWordDoc();
		}
	}
}