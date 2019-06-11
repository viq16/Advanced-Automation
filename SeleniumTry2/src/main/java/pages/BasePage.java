package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import UsefulInstructions.ByShadow;
import data.BaseData;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

public class BasePage {
	
	private static RemoteWebDriver rwb;
	private static String currentWindow;
	static BasePage basePage;
	
	@FindBy(xpath = "//a[@href='Prolog1.pptx']")
	private static WebElement prologDownloadLabel;
	
	public WebElement getPrologDownloadLabel() {
		return prologDownloadLabel;
	}
	
	public void prepareBrowser(RemoteWebDriver tempRwb) {
		rwb = tempRwb;
		rwb.manage().window().maximize();
		currentWindow = rwb.getWindowHandle();
	}
	
	public void getOnGoogleHomePage() {
		rwb.get(BaseData.defaultGoogleUrl);
		String googleUrl = rwb.getCurrentUrl();
		assertThat(googleUrl).withFailMessage(
		"BASE URL IS NOT CORRECT, WAS: " 
		+ googleUrl +
		" EXPECTED: " + BaseData.defaultGoogleUrl).contains("google.com");
	}
	
	public void performClick(WebElement elem) {
		waitForClickabilityOfElementByWebElement(elem, 5);
		elem.click();
	}
	
	public void openURLDestination(String url) {
		rwb.get(url);
		waitForPageFullyLoaded(10);
	}
	
	public static void cleanUpBrowser() {
		waitForNumberOfWindowsToBe(1,5);
		rwb.switchTo().window(currentWindow).close();
	}
	
	public static void waitForPageFullyLoaded(int timeout) {
		rwb.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
	}
	
	public static void waitForNumberOfWindowsToBe(int numberOfWindows, int timeout) {
		WebDriverWait wait = new WebDriverWait(rwb, timeout);
		wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
	}
	
	public static void waitForVisibilityOfElementByXpath(String xpath, int timeout) {
		WebDriverWait wait = new WebDriverWait(rwb, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
	
	public static void waitForVisibilityOfElementByWebElement(WebElement elem, int timeout) {
		WebDriverWait wait = new WebDriverWait(rwb, timeout);
		wait.until(ExpectedConditions.visibilityOf(elem));
	}
	
	public static void waitForClickabilityOfElementByXpath(String xpath, int timeout) {
		WebDriverWait wait = new WebDriverWait(rwb, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	public static void waitForClickabilityOfElementByWebElement(WebElement elem, int timeout) {
		WebDriverWait wait = new WebDriverWait(rwb, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(elem));
	}
	
	public static void waitForInvisibilityOfElementByXpath(String xpath, int timeout) {
		WebDriverWait wait = new WebDriverWait(rwb, timeout);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
	}
	
	public static void waitForInvisibilityOfElementByElement(WebElement elem, int timeout) {
		WebDriverWait wait = new WebDriverWait(rwb, timeout);
		wait.until(ExpectedConditions.invisibilityOf(elem));
	}

	public void goToChromeDownloads() throws InterruptedException {
		waitForPageFullyLoaded(10);
		if (rwb.getCurrentUrl().startsWith("chrome://downloads")) {}
		else {
			rwb.get("chrome://downloads");
			waitForPageFullyLoaded(10);
			Thread.sleep(3000);
		}
	}
	
	public void determineIfFileIsDownloaded() {
		waitForVisibilityOfElementByXpath("//downloads-manager", 5);
		
		waitForVisibilityOfElementByWebElement(rwb.findElement(ByShadow.css(
		        "downloads-manager >>> downloads-item >>> #show")),5);
		
		WebElement dateInfo = rwb.findElement(ByShadow.css(
			        "downloads-manager >>> downloads-item >>> #date"));
		
		WebElement fileNameInfo = rwb.findElement(ByShadow.css(
		        "downloads-manager >>> downloads-item >>> #file-link"));
		
		WebElement showInFolder = rwb.findElement(ByShadow.css(
		        "downloads-manager >>> downloads-item >>> #show"));
		
		assertThat("Dzisiaj").withFailMessage("FILE WAS NOT DOWNLOADED TODAY").isEqualTo(dateInfo.getText());
		assertThat(fileNameInfo.getText()).withFailMessage("FILE WAS NOT DOWNLOADED TODAY").contains("Prolog");
		assertThat("Pokaż w folderze").withFailMessage("DOWNLOADING WAS NOT COMPLETED CORRECTLY").isEqualTo(showInFolder.getText());
	}
	
}
