package com.stepDefinition;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LMSPage;
import pages.HolidayList;

public class TestSteps {

	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	Logger log = Logger.getLogger(TestSteps.class);
	Properties props;
	FileReader reader;
	HolidayList holidayListObj;
	LMSPage lmspageObj;

	@Given("User is on EY LMS page")
	public void user_is_on_ey_lms_page() throws IOException {

		// Setting up
		props = new Properties();
		reader = new FileReader("src\\test\\resources\\data.properties");
		props.load(reader);

		// Setting up webdriver
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();

		report = new ExtentReports();
		report.setSystemInfo("OS", System.getProperty("os.name"));
		report.setSystemInfo("Java Version", System.getProperty("java.version"));
		report.setSystemInfo("URL", "https://lms.ey.net/");

		ExtentSparkReporter spark = new ExtentSparkReporter("target/report.html");
		report.attachReporter(spark);
		test = report.createTest("HolidaysList");

		// Instantiating PF
		holidayListObj = new HolidayList(driver);
		lmspageObj = new LMSPage(driver);

		// Configuring log4j
		PropertyConfigurator.configure(props.getProperty("log4jPath"));

		driver.get(props.getProperty("url"));
		driver.manage().window().maximize();

		test.log(Status.PASS, "LMS Webpage opened");
		log.info("LMS Webpage opened");

	}

	@When("User navigates to the Holiday List Page")
	public void user_navigates_to_the_holiday_list_page() {
		lmspageObj.clickOnHolidaysLink();
		test.log(Status.PASS, "Clicked on Holidays List link");
		log.info("Clicked on Holidays link");
	}

	@Then("Validate if the public holiday count is equal to or greater than {int}")
	public void validate_if_the_public_holiday_count_is_equal_to_or_greater_than(Integer int1) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

//		 Validating by checking size of list

		log.info("Checking if public holidays are greater than or equal to 10");
		if (holidayListObj.checkSize() >= 10) {
			test.info("Public holidays are greater than or equal to 10");
			log.info("Public holidays are greater than or equal to 10");

		} else {
			test.info("Public holidays less than 10");
			log.info("Public holidays less than 10");

		}

	}

	@Then("User is able to split the holiday details as per Holiday Type")
	public void user_is_able_to_split_the_holiday_details_as_per_holiday_type() {

		List<String> optionalHols = new ArrayList<String>();

		// Iterating through list of web elements to retrieve optional holidays
		for (WebElement wb : holidayListObj.retrieveHols()) {
			if (wb.getText().contains("Optional Holiday")) {
				optionalHols.add(wb.getText().replace("Optional Holiday", ""));
			}
		}

		test.pass(MarkupHelper.createLabel("List of Optional Holidays", ExtentColor.GREEN));
		test.info(MarkupHelper.createOrderedList(optionalHols));
		log.info("Printed list of optional holidays in report");

		List<String> publicHols = new ArrayList<String>();

		for (WebElement wb : holidayListObj.retrieveHols()) {
			if (wb.getText().contains("Public Holiday")) {
				publicHols.add(wb.getText().replace("Public Holiday", ""));
			}
		}

		test.pass(MarkupHelper.createLabel("List of Public Holidays", ExtentColor.GREEN));
		test.info(MarkupHelper.createOrderedList(publicHols));
		log.info("Printed list of Public holidays in report");


	}

	@After
	public void after_Steps() {
		driver.quit();
		log.info("Browser closed.");
		test.log(Status.INFO, "Browser Closed");
		report.flush();
	}

}
