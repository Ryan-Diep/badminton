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
    String label;
    String path;
    boolean flag = true;

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
        String sutUrl = "https://reservation.frontdesksuite.ca/rcfs/nepeansportsplex/Home/Index?Culture=en&PageId=b0d362a1-ba36-42ae-b1e0-feefaf43fe4c&ShouldStartReserveTimeFlow=False&ButtonId=00000000-0000-0000-0000-000000000000";
        driver.get(sutUrl);
        
        while(flag) {
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
	                    case "Mon" :
	                    	driver.findElement(By.xpath("//a[@href='/rcfs/richcraftkanata/ReserveTime/StartReservation?pageId=b3b9b36f-8401-466d-b4c4-19eb5547b43a&amp;buttonId=af0013cb-31b9-4189-89f1-3c203c1b46f8&amp;culture=en&amp;uiCulture=en']")).click();
	                    	driver.findElement(By.name("ReservationCount")).click();
	                    	driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE,"2");
	                    	driver.findElement(By.className("mdc-button__ripple")).click();
							driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a")).click();
							label = "8:30 AM " + bookingDate;
	                    	path = "//a[contains(@aria-label, \"" + label + "\") ]";        
	                    	driver.findElement(By.xpath(path)).click();
	                    	driver.findElement(By.name("PhoneNumber")).click();
	                    	driver.findElement(By.name("PhoneNumber")).sendKeys("6138081681");
	                    	driver.findElement(By.name("Email")).click();
	                    	driver.findElement(By.name("Email")).sendKeys("ryandiep5@gmail.com");
	                    	driver.findElement(By.name("field2021")).click();
	                    	driver.findElement(By.name("field2021")).sendKeys("Ryan");
	                    	driver.findElement(By.className("mdc-button__ripple")).click();
	                    	Thread.sleep(1000000);
	                    	break;
		                    
	                    case "Tue" :
	                    	driver.findElement(By.xpath("//a[@href='/rcfs/richcraftkanata/ReserveTime/StartReservation?pageId=b3b9b36f-8401-466d-b4c4-19eb5547b43a&buttonId=af0013cb-31b9-4189-89f1-3c203c1b46f8&culture=en&uiCulture=en']")).click();
	                    	driver.findElement(By.name("ReservationCount")).click();
	                    	driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE,"2");
							driver.findElement(By.className("mdc-button__ripple")).click();
							driver.findElement(By.className("date-text")).click();
	                    	driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a")).click();
							Thread.sleep(10000);
							label = "7:00 PM " + bookingDate;
	                    	path = "//a[contains(@aria-label, \"" + label + "\") ]";        
	                    	driver.findElement(By.xpath(path)).click();
	                    	driver.findElement(By.name("PhoneNumber")).click();
	                    	driver.findElement(By.name("PhoneNumber")).sendKeys("6138081681");
	                    	driver.findElement(By.name("Email")).click();
	                    	driver.findElement(By.name("Email")).sendKeys("ryandiep5@gmail.com");
	                    	driver.findElement(By.name("field2021")).click();
	                    	driver.findElement(By.name("field2021")).sendKeys("Ryan");
	                    	driver.findElement(By.className("mdc-button__ripple")).click();
	                    	Thread.sleep(1000000);
	                        break;
                    
	                    case "Wed":
	                    	driver.findElement(By.xpath("//a[@href='/rcfs/nepeansportsplex/ReserveTime/StartReservation?pageId=b0d362a1-ba36-42ae-b1e0-feefaf43fe4c&amp;buttonId=9ce6512e-016d-4bef-8a73-461225acfb08&amp;culture=en&amp;uiCulture=en']")).click();
	                    	driver.findElement(By.name("ReservationCount")).click();
	                    	driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE,"2");
							driver.findElement(By.className("mdc-button__ripple")).click();
							driver.findElement(By.className("date-text")).click();
	                    	driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a")).click();
	                    	label = "7:00 PM " + slot;
	                    	path = "//a[contains(@aria-label, \"" + label + "\") ]";        
	                    	driver.findElement(By.xpath(path)).click();
	                    	driver.findElement(By.name("PhoneNumber")).click();
	                    	driver.findElement(By.name("PhoneNumber")).sendKeys("6138081681");
	                    	driver.findElement(By.name("Email")).click();
	                    	driver.findElement(By.name("Email")).sendKeys("ryandiep5@gmail.com");
	                    	driver.findElement(By.name("field2021")).click();
	                    	driver.findElement(By.name("field2021")).sendKeys("Ryan");
	                    	driver.findElement(By.className("mdc-button__ripple")).click();
	                    	Thread.sleep(1000000);
	                    	break;
	                    	
	                    case "Thu":
							driver.findElement(By.xpath("//a[@href='/rcfs/richcraftkanata/ReserveTime/StartReservation?pageId=b3b9b36f-8401-466d-b4c4-19eb5547b43a&buttonId=910a4518-3e8b-417b-834a-da13e5075db1&culture=en&uiCulture=en']")).click();
							driver.findElement(By.name("ReservationCount")).click();
							driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE,"2");
							driver.findElement(By.className("mdc-button__ripple")).click();
							driver.findElement(By.className("date-text")).click();
							driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a")).click();
	                    	label = "9:30 AM " + slot;
	                    	path = "//a[contains(@aria-label, \"" + label + "\") ]";        
	                    	driver.findElement(By.xpath(path)).click();
	                    	driver.findElement(By.name("PhoneNumber")).click();
	                    	driver.findElement(By.name("PhoneNumber")).sendKeys("6138081681");
	                    	driver.findElement(By.name("Email")).click();
	                    	driver.findElement(By.name("Email")).sendKeys("ryandiep5@gmail.com");
	                    	driver.findElement(By.name("field2021")).click();
	                    	driver.findElement(By.name("field2021")).sendKeys("Ryan");
	                    	driver.findElement(By.className("mdc-button__ripple")).click();
	                    	Thread.sleep(1000000);
	                        break;
	                    
	                    case "Fri":
							driver.findElement(By.xpath("//a[@href='/rcfs/richcraftkanata/ReserveTime/StartReservation?pageId=b3b9b36f-8401-466d-b4c4-19eb5547b43a&buttonId=910a4518-3e8b-417b-834a-da13e5075db1&culture=en&uiCulture=en']")).click();
							driver.findElement(By.name("ReservationCount")).click();
							driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE,"2");
							driver.findElement(By.className("mdc-button__ripple")).click();
							driver.findElement(By.className("date-text")).click();
							driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a")).click();
	                    	label = "10:30 AM " + slot;
	                    	path = "//a[contains(@aria-label, \"" + label + "\") ]";        
	                    	driver.findElement(By.xpath(path)).click();
	                    	driver.findElement(By.name("PhoneNumber")).click();
	                    	driver.findElement(By.name("PhoneNumber")).sendKeys("6138081681");
	                    	driver.findElement(By.name("Email")).click();
	                    	driver.findElement(By.name("Email")).sendKeys("ryandiep5@gmail.com");
	                    	driver.findElement(By.name("field2021")).click();
	                    	driver.findElement(By.name("field2021")).sendKeys("Ryan");
	                    	driver.findElement(By.className("mdc-button__ripple")).click();
	                    	Thread.sleep(1000000);
	                        break;
	                        
	                    case "Sat":
							driver.findElement(By.xpath("//a[@href='/rcfs/richcraftkanata/ReserveTime/StartReservation?pageId=b3b9b36f-8401-466d-b4c4-19eb5547b43a&buttonId=910a4518-3e8b-417b-834a-da13e5075db1&culture=en&uiCulture=en']")).click();
							driver.findElement(By.name("ReservationCount")).click();
							driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE,"2");
							driver.findElement(By.className("mdc-button__ripple")).click();
							driver.findElement(By.className("date-text")).click();
							driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a")).click();
	                    	label = "9:30 AM " + slot;
	                    	path = "//a[contains(@aria-label, \"" + label + "\") ]";                        	
	                    	driver.findElement(By.xpath(path)).click();
	                    	driver.findElement(By.name("PhoneNumber")).click();
	                    	driver.findElement(By.name("PhoneNumber")).sendKeys("6138081681");
	                    	driver.findElement(By.name("Email")).click();
	                    	driver.findElement(By.name("Email")).sendKeys("ryandiep5@gmail.com");
	                    	driver.findElement(By.name("field2021")).click();
	                    	driver.findElement(By.name("field2021")).sendKeys("Ryan");
	                    	driver.findElement(By.className("mdc-button__ripple")).click();
	                    	Thread.sleep(1000000);
	                    	break;
	                    	
	                    case "Sun":
							driver.findElement(By.xpath("//a[@href='/rcfs/richcraftkanata/ReserveTime/StartReservation?pageId=b3b9b36f-8401-466d-b4c4-19eb5547b43a&buttonId=910a4518-3e8b-417b-834a-da13e5075db1&culture=en&uiCulture=en']")).click();
							driver.findElement(By.name("ReservationCount")).click();
							driver.findElement(By.name("ReservationCount")).sendKeys(Keys.BACK_SPACE,"2");
							driver.findElement(By.className("mdc-button__ripple")).click();
							driver.findElement(By.className("date-text")).click();
							driver.findElement(By.xpath("//span[text()='" + bookingDate + "']/ancestor::a")).click();
	                    	label = "7:30 PM " + slot;
	                    	path = "//a[contains(@aria-label, \"" + label + "\") ]";                        	
	                    	driver.findElement(By.xpath(path)).click();
	                    	driver.findElement(By.name("PhoneNumber")).click();
	                    	driver.findElement(By.name("PhoneNumber")).sendKeys("6138081681");
	                    	driver.findElement(By.name("Email")).click();
	                    	driver.findElement(By.name("Email")).sendKeys("ryandiep5@gmail.com");
	                    	driver.findElement(By.name("field2021")).click();
	                    	driver.findElement(By.name("field2021")).sendKeys("Ryan");
	                    	driver.findElement(By.className("mdc-button__ripple")).click();
	                    	Thread.sleep(1000000);
	                        break;
                    }
        		}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		driver.quit();
                flag = false;
        	}	
        }
    }
}
