import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Runner {
    public static Scanner in = new Scanner(System.in);
    public static String phone, name, email;
    public static int index, locationIndex, timeIndex;
    public static void main(String[] args) throws JsonProcessingException, IOException {
        ChromeTest test = new ChromeTest();
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

		for (JsonNode court : jsonData) {
            String location = court.get("courtName").asText();
            System.out.println("---------------------------------------------------");
            System.out.println("Location: " + location);
            System.out.println("Times:");

            JsonNode table = court.get("table");
            table.forEach(day -> {
                day.fields().forEachRemaining(entry -> {
                    System.out.println(" " + entry.getKey() + ":");
                    JsonNode times = entry.getValue().get("time");
                    times.forEach(time -> {
                        System.out.println(" + " + time.asText());
                    });
                });
            });
            System.out.println("---------------------------------------------------\n");
        }

        System.out.println("---------------------------------------------------");
        System.out.print("Enter Phone Number: ");
        test.setPhone(in.nextLine());
        System.out.print("Enter Email Address: ");
        test.setEmail(in.nextLine());
        System.out.print("Enter Name: ");
        test.setEmail(in.nextLine());
        System.out.println("--------------------------------------------------\n-");

        boolean choosingLocation = true;
        while (choosingLocation) {
            System.out.println("---------------------------------------------------");
            index = 1;
            System.out.println("Choose the Location to Book: ");
            for (JsonNode court : jsonData) {
                String location = court.get("courtName").asText();
                System.out.println(" " + index + ". " + location);
                index++;
            }
            try {
                System.out.print("Selection: ");
                locationIndex = in.nextInt() - 1;
                in.nextLine();
                if (!(locationIndex < 0 || locationIndex >= index)) {
                    test.setUrl(jsonData.get(locationIndex).get("href").asText());
                    choosingLocation = false;
                }
                else {
                    throw new Exception();
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input, try again");
            }
            System.out.println("---------------------------------------------------\n");
            choosingLocation = false;
        }

        boolean choosingTime = true;
        while (choosingTime) {
            System.out.println("---------------------------------------------------");
            index = 1;
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("EEE");
            LocalDate futureDate = currentDate.plus(2, ChronoUnit.DAYS);
            String futureDay = futureDate.format(dayFormat).replace(".", "");
            System.out.println("Booking for " + futureDay);
            System.out.println("Choose the Time to Book: ");
            JsonNode courtData = jsonData.get(locationIndex);
            JsonNode tableData = courtData.get("table");
            JsonNode dayData = null;

            Iterator<JsonNode> iterator = tableData.iterator();
            while (iterator.hasNext()) {
                JsonNode entry = iterator.next();
                if (entry.has(futureDay)) {
                    dayData = entry.get(futureDay);
                    break;
                }
            }

            dayData.get("time").forEach(time -> {
                System.out.println(" " + index + ". " + time.asText());
                index++;
            });
            System.out.print("Selection: ");
            timeIndex = in.nextInt() - 1;
            in.nextLine();
            if (!(timeIndex < 0 || timeIndex >= index)) {
                System.out.println("control");
                test.setTime(formatTime(dayData.get("time").get(timeIndex).asText()));
                System.out.println("set time");
                test.setLink(dayData.get("link").asText());
                System.out.println("set link");
                choosingTime = false;
            // try {
            //     System.out.print("Selection: ");
            //     timeIndex = in.nextInt() - 1;
            //     in.nextLine();
            //     if (!(timeIndex < 0 || timeIndex >= index)) {
            //         System.out.println("control");
            //         test.setTime(formatTime(jsonData.get(locationIndex).get("table").get(futureDay).get("time").get(timeIndex).asText()));
            //         System.out.println("set time");
            //         test.setLink(jsonData.get(locationIndex).get("table").get(futureDay).get("link").asText());
            //         System.out.println("set link");
            //         choosingTime = false;
            //     }
            //     else {
            //         throw new Exception();
            //     }
            // }
            // catch (Exception e) {
            //     System.out.println("Invalid input, try again");
            }
            
            System.out.println("---------------------------------------------------\n");
        }

        in.close();
        test.setupClass();
        test.setup();
        test.test();
        test.teardown();
    }
    private static String formatTime(String time) {
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
