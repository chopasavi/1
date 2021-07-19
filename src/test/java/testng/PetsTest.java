package testng;

import library.PetPojo;
import library.Pets;
import library.SwaggerPageObject;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PetsTest {

	private static Logger logger = LoggerFactory.getLogger(PetsTest.class);
	private PetPojo petPojo;
	private String photoUrl = "https://www.petful.com/wp-content/uploads/2020/06/doggy-sunglasses.jpg";
	private String status = "available";
	
	WebDriver driver;

	@Test(enabled = true)
	public void createPet() throws Exception {
		List<String> listOfPhotoUrl = new ArrayList<>();
		listOfPhotoUrl.add(photoUrl);
		petPojo = Pets.postPet("Rex", listOfPhotoUrl);
		Assert.assertEquals(Pets.uploadImage(petPojo.id, System.getProperty("user.dir") + "/src/main/resources/diet.jpg"), 200);
	}

	@Test(enabled = true)
	public void fidnByStatus() throws Exception {
		List<PetPojo> petPojo = Pets.findByStatus(status);
		// we can iterate list and check each status:
		petPojo.get(0).status.equals(status);
		Assert.assertTrue(petPojo.size() > 10);
	}
	
	public void init() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setBrowserName("Chrome");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-web-security");
		options.addArguments("--allow-running-insecure-content");
		capability.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(options);
		driver.get("https://petstore.swagger.io/#/");
	}
	
	public void teardown() {
		driver.close();
		driver.quit();
	}
	
	@Test
	public void uiTest() {
		init();
		SwaggerPageObject sw = new SwaggerPageObject(driver);
		sw.clickSection(0);
		sw.clickSection(1);
		sw.clickSection(2);
		teardown();
	}
}
