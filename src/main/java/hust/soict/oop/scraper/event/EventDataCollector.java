package hust.soict.oop.scraper.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventDataCollector {

	private List<Event> events;
	private ObjectMapper objectMapper;

	public EventDataCollector() {
		events = new ArrayList<Event>();
		objectMapper = new ObjectMapper();
	}

	public void collectEventData() {
		try {
			Document doc = Jsoup.connect(
					"https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam")
					.get();
			System.out.printf("\nWebsite Title: %s\n\n", doc.title());

			List<Element> ages = doc.select("h2").subList(1, 6);

			for (Element age : ages) {
				String ageText = age.select(".mw-headline").text();
				List<String> dynastyList = new ArrayList<>();
				dynastyList.add("");
				List<String> yearList = new ArrayList<>();
				yearList.add("");

				Element currentElement = age.nextElementSibling();
				while (currentElement != null && !currentElement.tagName().equals("h2")) {
					if (currentElement.tagName().equals("h3")) {
						dynastyList.add(0, currentElement.select(".mw-headline").text());
					} else if (currentElement.tagName().equals("p")) {
						String time = currentElement.select("b").text();
						String event = currentElement.text().replace(time, "").trim();
						if (!event.isBlank()) {
							Event eventEntity = new Event(ageText, dynastyList.get(0), event, time);
							events.add(eventEntity);
						} else {
							yearList.set(0, time);
						}
					} else if (currentElement.tagName().equals("dl")) {
						Elements ddElements = currentElement.select("dd");
						ddElements.forEach(element -> {
							String rawTime = element.select("b").text();
							String event = element.text().replace(rawTime, "").trim();
							String time = rawTime.concat(" ").concat(yearList.get(0));
							Event eventEntity = new Event(ageText, dynastyList.get(0), event, time);
							events.add(eventEntity);
						});
					}
					currentElement = currentElement.nextElementSibling();
				}
			}

			events.forEach(event -> {
				System.out.printf("%d. %s   %s   %s   %s\n", events.indexOf(event) + 1, event.getAge(),
						event.getDynasty(), event.getDate(), event.getEvent());
			});

			try (FileWriter fileWriter = new FileWriter("src/main/java/hust/soict/oop/scraper/event/data/events.json")) {
				String json = objectMapper.writeValueAsString(events);
				fileWriter.write(json);
				System.out.println("\nData written to file successfully.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		EventDataCollector dataCollector = new EventDataCollector();
		dataCollector.collectEventData();
	}
}