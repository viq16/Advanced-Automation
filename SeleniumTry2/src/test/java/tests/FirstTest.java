package tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.BasePage;

public class FirstTest {
	
	static BasePage basePage;
	private static RemoteWebDriver rwb;
	

	@BeforeSuite
	public static void PrepareBrowser() {
		System.out.println("BEFORE SUITE STARTED");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\MEDION\\Desktop\\Selenium\\chromedriver.exe");
		rwb = new ChromeDriver();
		basePage = PageFactory.initElements(rwb, BasePage.class);
		basePage.prepareBrowser(rwb);
		System.out.println("BEFORE SUITE ENDED");
	}
	
	@BeforeTest
	public static void InitializeHomePage() {
		basePage.getOnGoogleHomePage();
	}
	
	@Test
	public static void TestGoogleSearchEngine() throws InterruptedException{
		basePage.openURLDestination("http://math.uni.lodz.pl/~kowalcr/PPD/");
		basePage.performClick(basePage.getPrologDownloadLabel());
		basePage.goToChromeDownloads();
		basePage.determineIfFileIsDownloaded();
	}
	
	@AfterSuite
	public static void CleanUpBrowser() {
		System.out.println("AFTER SUITE STARTED");
		BasePage.cleanUpBrowser();
		System.out.println("AFTER SUITE ENDED");
	}
}
