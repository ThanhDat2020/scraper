package hust.soict.oop.scraper.event;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class EventDataCollector {
    protected List<Event> events;
    private ObjectMapper objectMapper;

    public EventDataCollector() {
        events = new ArrayList<>();
        objectMapper = new ObjectMapper();
    }

    public abstract void collectEventData(String url);

    public void printEvents() {
        events.forEach(event -> {
            System.out.printf("%d. %s\n", events.indexOf(event) + 1, event.toString());
        });
    }

    public void writeEventsToFile(String file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            String json = objectMapper.writeValueAsString(events);
            fileWriter.write(json);
            System.out.println("\nData written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
