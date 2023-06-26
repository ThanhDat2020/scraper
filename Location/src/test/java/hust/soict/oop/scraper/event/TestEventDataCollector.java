package hust.soict.oop.scraper.event;

import java.util.stream.IntStream;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestEventDataCollector {

	public static void main(String[] args) {

		List<Event> events = new ArrayList<Event>();

		try {
			/**
			 * Here we create a document object, The we use JSoup to fetch the website.
			 */
			ObjectMapper objectMapper = new ObjectMapper();
			
			Document doc = Jsoup.connect(
					"https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam")
					.get();

			System.out.printf("\nWebsite Title: %s\n\n", doc.title());

			// Get the list of ages
			List<Element> ages = doc.select("h2").subList(1, 6);

			for (Element age : ages) {
				String ageText = age.select(".mw-headline").text();
//				System.out.println("Age: " + ageText);
				String year = new String();
				List<String> yearList = new ArrayList<String>();
				yearList.add(year);
				List<String> dynastyList = new ArrayList<String>();
				dynastyList.add("");

				Element currentElement = age.nextElementSibling();
				while (currentElement != null && !currentElement.tagName().equals("h2")) {
//					System.out.println("  - " + currentElement.text());
					if (currentElement.tagName().equals("h3")) {
						dynastyList.add(0, currentElement.select(".mw-headline").text());
//						System.out.println("  - Dynasty: " + dynastyList.get(0));
					} else if (currentElement.tagName().equals("p")) {
						String time = currentElement.select("b").text();
//						System.out.println("p: " + time);
						String event = currentElement.text().replace(time, "").trim();
//						System.out.println("p: " + event);
						if (event.isBlank()) {
							yearList.set(0, time);
						} else {
//							System.out.println("      - Time: " + time);
//							System.out.println("      - Event: " + event);
							Event eventEntity = new Event(ageText, dynastyList.get(0), event, time);
							events.add(eventEntity);
						}
					} else if (currentElement.tagName().equals("dl")) {
						Elements ddElements = currentElement.select("dd");
						ddElements.forEach(element -> {
							String rawTime = element.select("b").text();
							String event = element.text().replace(rawTime, "").trim();
							String time = rawTime.concat(" ").concat(yearList.get(0));
//							System.out.println("      - Time: " + time);
//							System.out.println("      - Event: " + event);
							Event eventEntity = new Event(ageText, dynastyList.get(0), event, time);
							events.add(eventEntity);
						});
					}
					currentElement = currentElement.nextElementSibling();
				}
			}

			IntStream.range(0, events.size())
				.forEach(index -> {
		            Event event = events.get(index);
		            System.out.printf("%d. %s   %s   %s   %s\n", index + 1, event.getAge(), event.getDynasty(),
					event.getDate(), event.getEvent());
				});
		
			try (FileWriter fileWriter = new FileWriter("src/main/java/hust/soict/oop/data/raw/events.json")) {
				// Convert the list to JSON string
	            String json = objectMapper.writeValueAsString(events);

	            // Write the JSON string to the file
	            fileWriter.write(json);
	            System.out.println("\nData written to file successfully.");
			}

			/**
			 * In case of any IO errors, we want the messages written to the console
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
