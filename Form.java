package com.test.readImage;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class Form {
	public static void main(String args[]) throws Exception {
		int pageNo = 0;
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\rahsamya\\Documents\\Automation\\chromedriver.exe");
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(opt);

		driver.get(" http://survey-report.us/");
		driver.manage().window().maximize();
		driver.findElement(By.id("txt_Uname")).sendKeys("C230720199091");
		driver.findElement(By.id("txt_pass")).sendKeys("Jzag0");
		Thread.sleep(1000);
		driver.findElement(By.id("btnsubmit")).click();

		for (int i = 1422; i < 1495; i++) {
			System.out.println(i);
			try {

				WebElement pageDropdown = driver
						.findElement(By.xpath("//span[contains(@id,'current')]//following::select"));
				Select select = new Select(pageDropdown);
				select.selectByIndex(i);
				String currentPage = driver.findElement(By.xpath("//span[contains(text(),' Current Page :')]/span"))
						.getText();
				WebElement captcha = driver.findElement(By.xpath("//img[@id='ctl00_ContentPlaceHolder1_MainImg']"));
				File src = captcha.getScreenshotAs(OutputType.FILE);
				String path = System.getProperty("user.dir") + "/screenshots/captcha.png";
				FileHandler.copy(src, new File(path));
				ITesseract image = new Tesseract();
				String str = image.doOCR(new File(path));

				String[] data = str.split("\n");
				String uniqueNumber = data[0];
				uniqueNumber = uniqueNumber.replaceAll("^[A-Za-z0-9]", "");
				String fullName = data[1];
				fullName = fullName.replaceAll("^[A-Za-z0-9]", "");
				String contactDetail = data[2];
				contactDetail = contactDetail.replaceAll("^[A-Za-z0-9]", "");
				String monthlySalary = data[3];
				monthlySalary = monthlySalary.replaceAll("^[A-Za-z0-9]", "").trim();
				String postalAddress = data[4];
				postalAddress = postalAddress.replaceAll("^[A-Za-z0-9]", "");

				WebElement uniqueNumberw = driver
						.findElement(By.xpath("//label[contains(text(),'Unique No')]//following::input[1]"));
				uniqueNumberw.clear();
				uniqueNumberw.sendKeys(uniqueNumber);

				WebElement fullNamew = driver
						.findElement(By.xpath("//label[contains(text(),'Full Name')]//following::input[1]"));
				fullNamew.clear();
				fullNamew.sendKeys(fullName);

				WebElement contactDetailw = driver
						.findElement(By.xpath("//label[contains(text(),'Contact Detail')]//following::input[1]"));
				contactDetailw.clear();
				contactDetailw.sendKeys(contactDetail);

				WebElement monthlySalaryw = driver
						.findElement(By.xpath("//label[contains(text(),'Monthly Salary')]//following::input[1]"));
				monthlySalaryw.clear();
				monthlySalaryw.sendKeys(monthlySalary);

				WebElement postalAddressw = driver
						.findElement(By.xpath("//label[contains(text(),'Postal Address')]//following::input[1]"));
				postalAddressw.clear();
				postalAddressw.sendKeys(postalAddress);

				if (Integer.parseInt(currentPage) < 1500) {
					driver.findElement(By.xpath("//input[@type='image']")).click();

					/*
					 * driver.findElement(By.xpath("//a[text()='Logout']")).
					 * click(); driver.quit();
					 */

				}
			} catch (Exception e) {
				driver.findElement(By.xpath("//a[text()='Logout']")).click();
				driver.quit();
			}

		}
	}
}
