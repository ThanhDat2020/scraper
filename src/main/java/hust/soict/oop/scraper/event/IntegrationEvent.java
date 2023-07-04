package hust.soict.oop.scraper.event;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class IntegrationEvent {

    private static final String OUTPUT_FILE_PATH = "src/main/java/hust/soict/oop/scraper/event/data/duplicated.txt";
    private static final String WIKI_EVENTS_JSON_PATH = "src/main/java/hust/soict/oop/scraper/event/data/wiki_events.json";
    private static final String NEW_EVENTS_JSON_PATH = "src/main/java/hust/soict/oop/scraper/event/data/events.json";
    private static final String QNL_EVENTS_JSON_PATH = "src/main/java/hust/soict/oop/scraper/event/data/qnl_events.json";

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        int count = 0;
        IntegrationEvent integration = new IntegrationEvent();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH))) {
            // Load JSON files
            List<Event> wikiEventList = integration.loadEventsFromJson(objectMapper, WIKI_EVENTS_JSON_PATH);
            List<Event> qnlEventList = new ArrayList<>(integration.loadEventsFromJson(objectMapper, QNL_EVENTS_JSON_PATH));
            List<Event> combinedList = new ArrayList<>();
            
            System.out.println("Wiki: " + wikiEventList.size());
            System.out.println("QNL: " + qnlEventList.size());

            Iterator<Event> qnlIterator = qnlEventList.iterator();
            while (qnlIterator.hasNext()) {
                Event eventQNL = qnlIterator.next();

                boolean foundMatch = false;

                if (eventQNL.getFrom().contains("Thế kỉ"))
                    continue;

                List<Integer> qnlStartDate = HelperDateConverter.convertToIntList(eventQNL.getFrom());
                List<Integer> qnlEndDate = eventQNL.getTo().isEmpty() ? null
                        : HelperDateConverter.convertToIntList(eventQNL.getTo());

                for (int i = 0; i < wikiEventList.size(); i++) {
                    if (wikiEventList.get(i).getFrom().contains("Thế kỉ"))
                        continue;

                    List<Integer> wikiStartDate = HelperDateConverter.convertToIntList(wikiEventList.get(i).getFrom());
                    List<Integer> wikiEndDate = wikiEventList.get(i).getTo().isEmpty() ? null
                            : HelperDateConverter.convertToIntList(wikiEventList.get(i).getTo());

                    if (integration.areEquals(wikiStartDate, wikiEndDate, qnlStartDate, qnlEndDate)) {
                        writer.write("Events " + integration.formatEventDetails(wikiEventList.get(i)) + " and " + eventQNL.getEvent() + " are the same.\n");
                        foundMatch = true;
                        count++;

                        if (integration.compareDates(wikiStartDate, qnlStartDate) == 0) {
                            if (wikiEndDate != null && qnlEndDate != null && integration.compareDates(wikiEndDate, qnlEndDate) <= 0) {
                                Event event = wikiEventList.get(i);
                                event.setTo(eventQNL.getTo());
                                event.setEvent(event.getEvent() + ". " + eventQNL.getEvent());
                                wikiEventList.set(i, event);
                                writer.write("New Event: " + integration.formatEventDetails(wikiEventList.get(i)) + "\n");
                                break;
                            }

                            if (wikiEndDate != null && qnlEndDate != null && integration.compareDates(wikiEndDate, qnlEndDate) > 0) {
                                Event event = wikiEventList.get(i);
                                event.setEvent(eventQNL.getEvent() + ". " + event.getEvent());
                                wikiEventList.set(i, event);
                                writer.write("New Event: " + integration.formatEventDetails(wikiEventList.get(i)) + "\n");
                                break;
                            }

                            if (wikiEndDate != null && qnlEndDate == null) {
                                Event event = wikiEventList.get(i);
                                event.setEvent(eventQNL.getEvent() + ". " + event.getEvent());
                                wikiEventList.set(i, event);
                                writer.write("New Event: " + integration.formatEventDetails(wikiEventList.get(i)) + "\n");
                                break;
                            }

                            if (wikiEndDate == null && qnlEndDate != null) {
                                Event event = wikiEventList.get(i);
                                event.setTo(eventQNL.getTo());
                                event.setEvent(event.getEvent() + ". " + eventQNL.getEvent());
                                wikiEventList.set(i, event);
                                writer.write("New Event: " + integration.formatEventDetails(wikiEventList.get(i)) + "\n");
                                break;
                            }

                            if (wikiEndDate == null && qnlEndDate == null) {
                                Event event = wikiEventList.get(i);
                                event.setEvent(eventQNL.getEvent());
                                wikiEventList.set(i, event);
                                writer.write("New Event: " + integration.formatEventDetails(wikiEventList.get(i)) + "\n");
                                break;
                            }

                        }

                        if (integration.compareDates(wikiStartDate, qnlStartDate) > 0) {
                            Event event = wikiEventList.get(i);
                            event.setFrom(eventQNL.getFrom());
                            if (wikiEndDate == null && qnlEndDate != null) {
                                event.setTo(eventQNL.getTo());
                            }
                            event.setEvent(eventQNL.getEvent() + ". " + event.getEvent());
                            wikiEventList.set(i, event);
                            writer.write("New Event: " + integration.formatEventDetails(wikiEventList.get(i)) + "\n");
                            break;
                        }

                        if (integration.compareDates(wikiStartDate, qnlStartDate) < 0) {
                            Event event = wikiEventList.get(i);
                            if (wikiEndDate != null && qnlEndDate != null && integration.compareDates(wikiEndDate, qnlStartDate) == 0) {
                                event.setTo(eventQNL.getTo());
                            }
                            event.setEvent(event.getEvent() + ". " + eventQNL.getEvent());
                            wikiEventList.set(i, event);
                            writer.write("New Event: " + integration.formatEventDetails(wikiEventList.get(i)) + "\n");
                            break;
                        }
                    }
                }

                if (foundMatch) {
                    qnlIterator.remove(); 
                }
            }

            System.out.println("Removed " + count + " events from the QNL list.");

            //Save wikiEventList to a file
            combinedList.addAll(wikiEventList);
            combinedList.addAll(qnlEventList);
            System.out.println("Total: " + combinedList.size() + " events");
            integration.saveEventsToJson(objectMapper, combinedList, NEW_EVENTS_JSON_PATH);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int compareDates(List<Integer> wikiStartDate, List<Integer> qnlStartDate) {
        for (int i = 0; i < Math.min(wikiStartDate.size(), qnlStartDate.size()); i++) {
            int wikiValue = wikiStartDate.get(i);
            int qnlValue = qnlStartDate.get(i);
            if (wikiValue < qnlValue) {
                return -1;
            } else if (wikiValue > qnlValue) {
                return 1;
            }
        }

        return Integer.compare(wikiStartDate.size(), qnlStartDate.size());
    }

    private boolean areEquals(List<Integer> wikiStartDate, List<Integer> wikiEndDate,
                                     List<Integer> qnlStartDate, List<Integer> qnlEndDate) {
        if (qnlEndDate != null && wikiEndDate != null) {
            if (qnlStartDate.equals(wikiStartDate) || qnlStartDate.equals(wikiEndDate)
                    || qnlEndDate.equals(wikiStartDate) || qnlEndDate.equals(wikiEndDate)) {
                return true;
            }
        }

        if (qnlEndDate != null && wikiEndDate == null) {
            if (qnlStartDate.equals(wikiStartDate) || qnlEndDate.equals(wikiStartDate)) {
                return true;
            }
        }

        if (qnlEndDate == null && wikiEndDate != null) {
            if (qnlStartDate.equals(wikiStartDate) || qnlStartDate.equals(wikiEndDate)) {
                return true;
            }
        }

        if (qnlEndDate == null && wikiEndDate == null) {
            if (qnlStartDate.equals(wikiStartDate)) {
                return true;
            }
        }

        return false;
    }

    private List<Event> loadEventsFromJson(ObjectMapper objectMapper, String filePath) throws IOException {
        return Arrays.asList(objectMapper.readValue(Paths.get(filePath).toFile(), Event[].class));
    }

    private void saveEventsToJson(ObjectMapper objectMapper, List<Event> events, String filePath) throws IOException {
        objectMapper.writeValue(Paths.get(filePath).toFile(), events);
    }

    private String formatEventDetails(Event event) {
        String from = event.getFrom();
        String to = event.getTo();
        String eventDetails = event.getEvent();
        return from + "-" + to + " ||| " + eventDetails;
    }
}
