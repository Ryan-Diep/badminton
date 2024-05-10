import java.util.Scanner;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

class ChromeTest {

	static final Logger log = getLogger(lookup().lookupClass());
	String label, path, phone, email, name;
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
		try {
			String webScraperPath = new File("src/main/resources/data.json").getAbsolutePath();

			try {
				ProcessBuilder processBuilder = new ProcessBuilder("node", webScraperPath);
				Process process = processBuilder.start();

				int exitCode = process.waitFor();

				System.out.println("webScraper.js script exited with code: " + exitCode);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

			String jsonFilePath = new File("src/main/resources/data.json").getAbsolutePath();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonData = objectMapper.readTree(new File(jsonFilePath));

			// Scanner in = new Scanner(System.in);

			// for (JsonNode court : jsonData) {
			// String location = court.get("courtName").asText();
			// System.out.println("---------------------------------------------------");
			// System.out.println("Location: " + location);
			// System.out.println("Times:");

			// JsonNode table = court.get("table");
			// table.fields().forEachRemaining(entry -> {
			// JsonNode details = entry.getValue();
			// details.fields().forEachRemaining(dayEntry -> {
			// String day = dayEntry.getKey();
			// if (!day.equals("link")) {
			// JsonNode times = dayEntry.getValue();
			// System.out.println(" " + day + ":");
			// times.elements().forEachRemaining(time -> {
			// System.out.println(" - " + time.asText());
			// });
			// }
			// });
			// });
			// System.out.println("---------------------------------------------------\n");
			// }

			// boolean info = true;
			// while (info) {
			// System.out.println("---------------------------------------------------");
			// System.out.println("Enter Phone Number: ");
			// phone = in.nextLine();
			// System.out.println("Enter Email Address: ");
			// email = in.nextLine();
			// System.out.println("Enter Name: ");
			// name = in.nextLine();
			// System.out.println("---------------------------------------------------");
			// info = false;
			// }

			// boolean booking = true;
			// while (booking) {
			// System.out.println("---------------------------------------------------");
			// index = 1;
			// for (JsonNode court : jsonData) {
			// String location = court.get("courtName").asText();
			// System.out.println(" " + index + ". " + location);
			// index++;
			// }
			// name = in.nextLine();
			// System.out.println("---------------------------------------------------");
			// info = false;
			// }

			// in.close();

			phone = "6138081681";
			email = "ryandiep5@gmail.com";
			name = "Ryan";

			/*
			 * Location = 0 - For Richcraft (Kanata)
			 * Location = 1 - For Nepean
			 */
			int location = 1;
			String sutUrl = jsonData.get(location).get("href").asText();
			driver.get(sutUrl);

			if (location == 0) {
				monLink = jsonData.get(0).get("table").get("Badminton doubles - adult").get("link").asText();
				tueLink = jsonData.get(0).get("table").get("Badminton doubles - adult").get("link").asText();
				wedLink = jsonData.get(0).get("table").get("Badminton doubles - adult").get("link").asText();
				thuLink = jsonData.get(0).get("table").get("Badminton - family").get("link").asText();
				friLink = jsonData.get(0).get("table").get("Badminton doubles – all ages").get("link").asText();
				satLink = jsonData.get(0).get("table").get("Badminton doubles - adult").get("link").asText();
				sunLink = jsonData.get(0).get("table").get("Badminton doubles – all ages").get("link").asText();

				monTime = formatTime(
						jsonData.get(0).get("table").get("Badminton doubles - adult").get("Wed").get(0).asText());
				tueTime = formatTime(
						jsonData.get(0).get("table").get("Badminton doubles - adult").get("Thu").get(0).asText());
				wedTime = formatTime(
						jsonData.get(0).get("table").get("Badminton doubles - adult").get("Fri").get(0).asText());
				thuTime = formatTime(jsonData.get(0).get("table").get("Badminton - family").get("Sat").get(1).asText());
				friTime = formatTime(
						jsonData.get(0).get("table").get("Badminton doubles – all ages").get("Sun").get(2).asText());
				satTime = formatTime(
						jsonData.get(0).get("table").get("Badminton doubles - adult").get("Mon").get(0).asText());
				sunTime = formatTime(
						jsonData.get(0).get("table").get("Badminton doubles – all ages").get("Tue").get(0).asText());
			} else {
				monLink = jsonData.get(1).get("table").get("Badminton").get("link").asText();
				tueLink = jsonData.get(1).get("table").get("Badminton").get("link").asText();
				wedLink = jsonData.get(1).get("table").get("Badminton").get("link").asText();
				thuLink = jsonData.get(1).get("table").get("Badminton").get("link").asText();
				friLink = jsonData.get(1).get("table").get("Badminton").get("link").asText();
				satLink = jsonData.get(1).get("table").get("Badminton").get("link").asText();
				sunLink = jsonData.get(1).get("table").get("Badminton").get("link").asText();

				monTime = formatTime(jsonData.get(1).get("table").get("Badminton").get("Sat").get(0).asText()); // currently
																												// no
																												// time
				tueTime = formatTime(jsonData.get(1).get("table").get("Badminton").get("Thu").get(0).asText());
				wedTime = formatTime(jsonData.get(1).get("table").get("Badminton").get("Fri").get(0).asText());
				thuTime = formatTime(jsonData.get(1).get("table").get("Badminton").get("Sat").get(0).asText());
				friTime = formatTime(jsonData.get(1).get("table").get("Badminton").get("Sun").get(0).asText());
				satTime = formatTime(jsonData.get(1).get("table").get("Badminton").get("Sat").get(0).asText()); // currently
																												// no
																												// time
				sunTime = formatTime(jsonData.get(1).get("table").get("Badminton").get("Tue").get(0).asText());
			}

			while (flag) {
				LocalTime currentTime = LocalTime.now();
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
				String time = currentTime.format(timeFormat);
				String parts[] = time.split(":");

				try {
					if(parts[0].equals("18") && parts[1].equals("00") && parts[2].equals("01")) {
						LocalDate currentDate = LocalDate.now();
						DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("EEE");
						String day = currentDate.format(dayFormat).replace(".", "");
						LocalDate futureDate = currentDate.plus(2, ChronoUnit.DAYS);
						DateTimeFormatter slotFormat = DateTimeFormatter.ofPattern("EEEE MMMM dd, yyyy");
						DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE MMMM d, yyyy");
						String slot = futureDate.format(slotFormat);
						String bookingDate = futureDate.format(dateFormat);

						switch (day) {
							case "Mon":
								driver.findElement(By.xpath("//a[@href='" + monLink + "']")).click();
								driver.findElement(By.name("ReservationCount")).click();
								driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE, "2");
								driver.findElement(By.className("mdc-button__ripple")).click();
								driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a"))
										.click();
								label = monTime + " " + bookingDate;
								path = "//a[contains(@aria-label, \"" + label + "\") ]";
								driver.findElement(By.xpath(path)).click();
								driver.findElement(By.name("PhoneNumber")).click();
								driver.findElement(By.name("PhoneNumber")).sendKeys(phone);
								driver.findElement(By.name("Email")).click();
								driver.findElement(By.name("Email")).sendKeys(email);
								driver.findElement(By.name("field2021")).click();
								driver.findElement(By.name("field2021")).sendKeys(name);
								driver.findElement(By.className("mdc-button__ripple")).click();
								Thread.sleep(1000000);
								break;

							case "Tue":
								driver.findElement(By.xpath("//a[@href='" + tueLink + "']")).click();
								driver.findElement(By.name("ReservationCount")).click();
								driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE, "2");
								driver.findElement(By.className("mdc-button__ripple")).click();
								driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a"))
										.click();
								label = tueTime + " " + bookingDate;
								path = "//a[contains(@aria-label, \"" + label + "\") ]";
								driver.findElement(By.xpath(path)).click();
								driver.findElement(By.name("PhoneNumber")).click();
								driver.findElement(By.name("PhoneNumber")).sendKeys(phone);
								driver.findElement(By.name("Email")).click();
								driver.findElement(By.name("Email")).sendKeys(email);
								driver.findElement(By.name("field2021")).click();
								driver.findElement(By.name("field2021")).sendKeys(name);
								driver.findElement(By.className("mdc-button__ripple")).click();
								Thread.sleep(1000000);
								break;

							case "Wed":
								driver.findElement(By.xpath("//a[@href='" + wedLink + "']")).click();
								driver.findElement(By.name("ReservationCount")).click();
								driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE, "2");
								driver.findElement(By.className("mdc-button__ripple")).click();
								driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a"))
										.click();
								System.out.println("wedTime");
								label = wedTime + " " + slot;
								path = "//a[contains(@aria-label, \"" + label + "\") ]";
								driver.findElement(By.xpath(path)).click();
								driver.findElement(By.name("PhoneNumber")).click();
								driver.findElement(By.name("PhoneNumber")).sendKeys(phone);
								driver.findElement(By.name("Email")).click();
								driver.findElement(By.name("Email")).sendKeys(email);
								driver.findElement(By.name("field2021")).click();
								driver.findElement(By.name("field2021")).sendKeys(name);
								driver.findElement(By.className("mdc-button__ripple")).click();
								Thread.sleep(1000000);
								break;

							case "Thu":
								driver.findElement(By.xpath("//a[@href='" + thuLink + "']")).click();
								driver.findElement(By.name("ReservationCount")).click();
								driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE, "2");
								driver.findElement(By.className("mdc-button__ripple")).click();
								driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a"))
										.click();
								label = thuTime + " " + slot;
								path = "//a[contains(@aria-label, \"" + label + "\") ]";
								driver.findElement(By.xpath(path)).click();
								driver.findElement(By.name("PhoneNumber")).click();
								driver.findElement(By.name("PhoneNumber")).sendKeys(phone);
								driver.findElement(By.name("Email")).click();
								driver.findElement(By.name("Email")).sendKeys(email);
								driver.findElement(By.name("field2021")).click();
								driver.findElement(By.name("field2021")).sendKeys(name);
								driver.findElement(By.className("mdc-button__ripple")).click();
								Thread.sleep(1000000);
								break;

							case "Fri":
								driver.findElement(By.xpath("//a[@href='" + friLink + "']")).click();
								driver.findElement(By.name("ReservationCount")).click();
								driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE, "2");
								driver.findElement(By.className("mdc-button__ripple")).click();
								driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a"))
										.click();
								label = friTime + " " + slot;
								path = "//a[contains(@aria-label, \"" + label + "\") ]";
								driver.findElement(By.xpath(path)).click();
								driver.findElement(By.name("PhoneNumber")).click();
								driver.findElement(By.name("PhoneNumber")).sendKeys(phone);
								driver.findElement(By.name("Email")).click();
								driver.findElement(By.name("Email")).sendKeys(email);
								driver.findElement(By.name("field2021")).click();
								driver.findElement(By.name("field2021")).sendKeys(name);
								driver.findElement(By.className("mdc-button__ripple")).click();
								Thread.sleep(1000000);
								break;

							case "Sat":
								driver.findElement(By.xpath("//a[@href='" + satLink + "']")).click();
								driver.findElement(By.name("ReservationCount")).click();
								driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE, "2");
								driver.findElement(By.className("mdc-button__ripple")).click();
								driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a"))
										.click();
								label = satTime + " " + slot;
								path = "//a[contains(@aria-label, \"" + label + "\") ]";
								driver.findElement(By.xpath(path)).click();
								driver.findElement(By.name("PhoneNumber")).click();
								driver.findElement(By.name("PhoneNumber")).sendKeys(phone);
								driver.findElement(By.name("Email")).click();
								driver.findElement(By.name("Email")).sendKeys(email);
								driver.findElement(By.name("field2021")).click();
								driver.findElement(By.name("field2021")).sendKeys(name);
								driver.findElement(By.className("mdc-button__ripple")).click();
								Thread.sleep(1000000);
								break;

							case "Sun":
								driver.findElement(By.xpath("//a[@href='" + sunLink + "']")).click();
								driver.findElement(By.name("ReservationCount")).click();
								driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE, "2");
								driver.findElement(By.className("mdc-button__ripple")).click();
								driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a"))
										.click();
								label = sunTime + " " + slot;
								path = "//a[contains(@aria-label, \"" + label + "\") ]";
								driver.findElement(By.xpath(path)).click();
								driver.findElement(By.name("PhoneNumber")).click();
								driver.findElement(By.name("PhoneNumber")).sendKeys(phone);
								driver.findElement(By.name("Email")).click();
								driver.findElement(By.name("Email")).sendKeys(email);
								driver.findElement(By.name("field2021")).click();
								driver.findElement(By.name("field2021")).sendKeys(name);
								driver.findElement(By.className("mdc-button__ripple")).click();
								Thread.sleep(1000000);
								break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					driver.quit();
					flag = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			driver.quit();
			flag = false;
		}
	}

	private String formatTime(String time) {
		String[] parts = time.split(":");
		String hour = parts[0];
		String minutes = parts[1].substring(0, 2);
		String period = time.substring(time.length() - 2);
		if (hour.equals("10") && minutes.equals("30")) {
			period = "am";
		}
		String formattedTime = hour + ":" + minutes + " " + period;

		return formattedTime;
	}
}
