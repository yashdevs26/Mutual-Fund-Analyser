package com.yashdevs.dataScraper.service;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yashdevs.dataScraper.constant.URIConstant;
import com.yashdevs.dataScraper.entity.MFAPIResponseDTO;
import com.yashdevs.dataScraper.entity.MutualFundDataScraperEntity;

@Service
public class FundScraperService {

	public MFAPIResponseDTO retrieveMfApi(String url) {
		try {
			String jsonData = ApiFetcher.fetchJson(url);

			if (jsonData != null) {
				return JsonParsor.mfApiResponseDTOParseJson(jsonData, new TypeReference<MFAPIResponseDTO>() {
				});
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public MutualFundDataScraperEntity scrapeGroww(String inputValue) {
		// Configure Firefox Driver
		System.setProperty("webdriver.gecko.driver", "C://softwares/geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--headless"); // Run in headless mode (optional)
		WebDriver driver = new FirefoxDriver(options);
		MutualFundDataScraperEntity entity = null;
		try {
			// Navigate to the target website
			driver.get(URIConstant.GROWW_MF_URI);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// Wait for the fund name to appear
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#globalSearch23-searchMainDivId")));
			/*
			 * // Locate and click the dropdown WebElement dropdown =
			 * wait.until(ExpectedConditions .elementToBeClickable(By.
			 * cssSelector(".io752ObjectiveConatiner > div:nth-child(1)")));
			 * dropdown.click();
			 */
			//
			// click the search box
			driver.findElement(By.cssSelector("#globalSearch23")).click();

			// check if search parent div is redendered
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.pill12Pill")));

			// click MF filter
			driver.findElement(By.cssSelector("div.pill12Pill:nth-child(3)")).click();

			// Locate the search bar and input the search value
			WebElement searchBar = driver.findElement(By.cssSelector("#globalSearch23"));
			String searchValue = inputValue;
			searchBar.click();
			searchBar.sendKeys(searchValue);
			// searchBar.sendKeys(Keys.RETURN);
			// wait.until(ExpectedConditions.urlContains(searchValue));
			// Wait for dropdown options to appear
			// driver.manage().timeouts().implicitlyWait(6,
			// java.util.concurrent.TimeUnit.SECONDS);
			Thread.sleep(2000);
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#globalSearch23-suggestions")));

			// Get the first hit in dropdown
			driver.findElement(By.cssSelector("div.fsAuto:nth-child(1)")).click();
			// TODO: optimise this
			/*
			 * List<WebElement> elements =
			 * driver.findElements(By.cssSelector("div.fsAuto:nth-child(1)"));
			 */

			/*
			 * if (elements.isEmpty()) { throw new
			 * RuntimeException("Web Scrapping Groww has been failed!"); } // Find and click
			 * the option containing the searchValue +
			 * "Direct Growth"div.fsAuto:nth-child(1) for (WebElement container : elements)
			 * { WebElement dynamicInnerElement = container.findElement( By.
			 * cssSelector("div.fsAuto:div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"
			 * ));
			 * 
			 * if (dynamicInnerElement.getText().contains(searchValue + " Direct Growth")) {
			 * dynamicInnerElement.click(); break; } }
			 */

			// wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			wait.until(jsDriver -> js.executeScript("return document.readyState").equals("complete"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mfh239SchemeName")));
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector("table.ha384Table:nth-child(1) > tr:nth-child(4) > td:nth-child(2)")));
			entity = new MutualFundDataScraperEntity();
			entity.setSchemeCode("0"); // will use an api later
			entity.setFundName(driver.findElement(By.cssSelector(".mfh239SchemeName")).getText());
			entity.setCurrentNav(driver
					.findElement(By.cssSelector(
							"div.fnd2TableDiv:nth-child(1) > table:nth-child(1) > tr:nth-child(1) > td:nth-child(2)"))
					.getText());
			entity.setCurrentAum(driver.findElement(
					By.cssSelector("div.l5:nth-child(3) > table:nth-child(1) > tr:nth-child(2) > td:nth-child(2)"))
					.getText());
			entity.setExpenseRatio(driver.findElement(By.cssSelector("div.mf320Heading:nth-child(4) > h3:nth-child(1)"))
					.getText().split(":", 2)[1]);
			entity.setTop5(driver
					.findElement(By.cssSelector("table.ha384Table:nth-child(1) > tr:nth-child(1) > td:nth-child(2)"))
					.getText());
			entity.setTop20(driver
					.findElement(By.cssSelector("table.ha384Table:nth-child(1) > tr:nth-child(2) > td:nth-child(2)"))
					.getText());
			entity.setPe(driver
					.findElement(By.cssSelector("table.ha384Table:nth-child(3) > tr:nth-child(1) > td:nth-child(2)"))
					.getText());
			entity.setPb(driver
					.findElement(By.cssSelector("table.ha384Table:nth-child(1) > tr:nth-child(4) > td:nth-child(2)"))
					.getText());
			entity.setAlpha(driver
					.findElement(By.cssSelector("table.ha384Table:nth-child(3) > tr:nth-child(1) > td:nth-child(2)"))
					.getText());
			entity.setBeta(driver
					.findElement(By.cssSelector("table.ha384Table:nth-child(3) > tr:nth-child(2) > td:nth-child(2)"))
					.getText());
			entity.setSharpie(driver
					.findElement(By.cssSelector("table.ha384Table:nth-child(3) > tr:nth-child(3) > td:nth-child(2)"))
					.getText());
			entity.setSortino(driver
					.findElement(By.cssSelector("table.ha384Table:nth-child(3) > tr:nth-child(4) > td:nth-child(2)"))
					.getText());
			driver.findElement(By.cssSelector(".io752ObjectiveConatiner > div:nth-child(1)")).click();
			entity.setFundBenchmark(driver
					.findElement(By.cssSelector(".io752TableWidth > tr:nth-child(1) > td:nth-child(2)")).getText());

			return entity;
			// to be taken from another scrapper
			/*
			 * entity.setStdDev("7.51"); entity.setInfoRatio("1.69");
			 * entity.setTreynorRatio("29.22"); entity.setJensenAlpha("10.61");
			 * entity.setrSqaured("84.85"); entity.setMarValue("4.98")// nifty 500 TRI, last
			 * 12 years avg
			 */
			// historical nav will be taken from an api, limit to 5 year rolling data

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the browser
			driver.quit();
		}
		return entity;
	}

	public String scrapeMfApiForUrl(String fundName) {
		// Configure Firefox Driver
		System.setProperty("webdriver.gecko.driver", "C://softwares/geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--headless"); // Run in headless mode (optional)
		WebDriver driver = new FirefoxDriver(options);
		String url = "";
		try {
			// Navigate to the target website
			driver.get(URIConstant.MFAPI_URI);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			// Wait for the search bar to appear
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#mfsearch")));

			// Locate the search bar and input the search value
			WebElement searchBar = driver.findElement(By.cssSelector("#mfsearch"));
			String searchValue = fundName;
			searchBar.click();
			searchBar.sendKeys(searchValue);

			// Wait for dropdown options to appear
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ui-id-1")));

			// Get the list of dropdown options
			driver.findElement(By.cssSelector("li.ui-menu-item:nth-child(1)")).click();

			// Find and click the option containing the searchValue
			/*
			 * for (WebElement element : elements) { if
			 * (element.getText().contains(searchValue)) { element.click(); break; } }
			 */
			/*
			 * // Simulate pressing Enter searchBar.sendKeys(Keys.RETURN);
			 */

			/*
			 * // Wait for 2 seconds Thread.sleep(2000);
			 */

			// Wait for url to be clickable
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#apiUrl")));

			url = driver.findElement(By.cssSelector("#apiUrl")).getText().trim();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the browser
			driver.quit();
		}
		return url;
	}

	public MutualFundDataScraperEntity scrapeData(String inputScheme) {
		MutualFundDataScraperEntity entity = scrapeGroww(inputScheme);
		String url = this.scrapeMfApiForUrl(entity.getFundName().trim());
		entity.setMfApiResponseDTO(this.retrieveMfApi(url));
		entity.setSchemeCode(entity.getMfApiResponseDTO().getMeta().getSchemeCode().toString());
		entity.setFundCategory(entity.getMfApiResponseDTO().getMeta().getSchemeCategory());

		return entity;
	}
}
