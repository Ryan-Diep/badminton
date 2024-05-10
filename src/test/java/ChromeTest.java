import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

class ChromeTest {

	static final Logger log = getLogger(lookup().lookupClass());
	String label, path, phone, email, name, sutUrl, link, timeSlot;
	String monLink, tueLink, wedLink, thuLink, friLink, satLink, sunLink;
	String monTime, tueTime, wedTime, thuTime, friTime, satTime, sunTime;
	boolean flag = true;
	int index;

	WebDriver driver;

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void test() {
		driver.get(sutUrl);

		while (flag) {
			LocalTime currentTime = LocalTime.now();
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
			String time = currentTime.format(timeFormat);
			String parts[] = time.split(":");

			try {
				if(parts[0].equals("18") && parts[1].equals("00") && parts[2].equals("01")) {
					LocalDate currentDate = LocalDate.now();
					LocalDate futureDate = currentDate.plus(2, ChronoUnit.DAYS);
					DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE MMMM d, yyyy");
					String bookingDate = futureDate.format(dateFormat);

					driver.findElement(By.xpath("//a[@href='" + link + "']")).click();
					driver.findElement(By.name("ReservationCount")).click();
					driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE, "2");
					driver.findElement(By.className("mdc-button__ripple")).click();
					driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a"))
							.click();
					label = timeSlot + " " + bookingDate;
					path = "//a[contains(@aria-label, \"" + label + "\") ]";
					driver.findElement(By.xpath(path)).click();
					driver.findElement(By.name("PhoneNumber")).click();
					driver.findElement(By.name("PhoneNumber")).sendKeys(phone);
					driver.findElement(By.name("Email")).click();
					driver.findElement(By.name("Email")).sendKeys(email);
					driver.findElement(By.name("field2021")).click();
					driver.findElement(By.name("field2021")).sendKeys(name);
					driver.findElement(By.className("mdc-button__ripple")).click();
				}
			} catch (Exception e) {
				e.printStackTrace();
				driver.quit();
				flag = false;
			}
		}
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String sutUrl) {
		this.sutUrl = sutUrl;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setTime(String timeSlot) {
		this.timeSlot = timeSlot;
	}
}
