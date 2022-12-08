package com.stepDefinition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TestSteps {

	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	Logger log = Logger.getLogger(TestSteps.class);

	@Given("User is on EY LMS page")
	public void user_is_on_ey_lms_page() {
		System.setProperty("webdriver.edge.driver", "C:\\bb\\drivers\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();

		driver.get("https://lms.ey.net/");
		driver.manage().window().maximize();

		PropertyConfigurator
				.configure("C:\\Users\\FU276CJ\\eclipse-workspace\\lms-ey\\src\\test\\resources\\log4j.properties");
		report = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("target/spark.html");
		report.attachReporter(spark);
		test = report.createTest("HolidaysList");
		test.log(Status.PASS, "LMS Webpage opened");
		log.info("LMS Webpage opened");

	}

	@When("User navigates to the Holiday List Page")
	public void user_navigates_to_the_holiday_list_page() {
		driver.findElement(By.cssSelector(
				"#wrap > div.container-fluid.nonresponsive > div:nth-child(2) > div.row > div.col-sm-7 > div:nth-child(2) > div:nth-child(1) > div > div.row > div:nth-child(1) > a"))
				.click();
		test.log(Status.PASS, "Clicked on Holidays List link");
		log.info("Clicked on Holidays link");
	}

	@Then("Validate if the public holiday count is equal to or greater than {int}")
	public void validate_if_the_public_holiday_count_is_equal_to_or_greater_than(Integer int1) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		List<WebElement> s = driver.findElements(By.xpath("//*[contains(text(),'Public Holiday')]"));
		log.info("Checking if public holidays are greater or lesser than 10");
		if (s.size() >= 10) {
			System.out.println("Public Holidays are greater than or equal to 10");
			test.fail("Public holidays are greater than or equal to 10");
			log.info("Public holidays less than or equal to 10");

		} else {
			System.out.println("Public Holidays are less than 10");
			test.pass("Public holidays less than 10");
			log.info("Public holidays less than 10");
		}

	}

	@Then("User is able to split the holiday details as per Holiday Type")
	public void user_is_able_to_split_the_holiday_details_as_per_holiday_type() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		List<WebElement> Holidays = driver.findElements(By.tagName("tr"));

		List<String> optionalHols = new ArrayList<String>();

		for (WebElement wb : Holidays) {
			if (wb.getText().contains("Optional Holiday")) {
				optionalHols.add(wb.getText().replace("Optional Holiday", ""));
			}
		}

		test.pass(MarkupHelper.createLabel("List of Optional Holidays", ExtentColor.GREEN));
		test.info(MarkupHelper.createOrderedList(optionalHols));
		log.info("Printed list of optional holidays in report");

		List<String> publicHols = new ArrayList<String>();

		for (WebElement wb : Holidays) {
			if (wb.getText().contains("Public Holiday")) {
				publicHols.add(wb.getText().replace("Public Holiday", ""));
			}
		}

		test.pass(MarkupHelper.createLabel("List of Public Holidays", ExtentColor.GREEN));
		test.info(MarkupHelper.createOrderedList(publicHols));
		log.info("Printed list of Public holidays in report");
		report.flush();
		driver.quit();
		log.info("Browser closed.");

	}

}
