import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

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
	String label, path, phone, email, name, sutUrl, link;
	boolean flag = true;
	int index;
	private ArrayList<String> timeSlots;

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
	void test() throws InterruptedException {
		driver.get(sutUrl);
		while (flag) {
			LocalTime currentTime = LocalTime.now();
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
			String time = currentTime.format(timeFormat);
			String parts[] = time.split(":");
			int sleepInterval = 1000;
			try {
				if (parts[0].equals("17") && parts[1].equals("58") && parts[2].equals("00")) {
					sleepInterval = 0;
				} else if (parts[0].equals("18") && parts[1].equals("00") && parts[2].equals("01")) {
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

					int i = timeSlots.size() - 1;
					boolean looping = true;
					while (looping) {
						try {
							label = timeSlots.get(i) + " " + bookingDate;
							path = "//a[contains(@aria-label, \"" + label + "\") ]";
							driver.findElement(By.xpath(path)).click();
							looping = false;
						} catch (Exception e) {
							if (i-- < 0) {
								looping = false;
							}
						}
					}

					driver.findElement(By.name("PhoneNumber")).click();
					driver.findElement(By.name("PhoneNumber")).sendKeys(phone);
					driver.findElement(By.name("Email")).click();
					driver.findElement(By.name("Email")).sendKeys(email);
					driver.findElement(By.name("field2021")).click();
					driver.findElement(By.name("field2021")).sendKeys(name);
					for (int j = 0; j < 5; j++) {
						try {
							driver.findElement(By.className("mdc-button__ripple")).click();
							Thread.sleep(200);
						}
						catch (Exception e) {}
					}
					System.out.println("Browser Will Close in 5 Minutes");
					Thread.sleep(300000);
					flag = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				driver.quit();
				flag = false;
			}
			Thread.sleep(sleepInterval);
		}
		driver.quit();
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

	public void setTimeSlots(ArrayList<String> timeSlots) {
		this.timeSlots = timeSlots;
	}
}
