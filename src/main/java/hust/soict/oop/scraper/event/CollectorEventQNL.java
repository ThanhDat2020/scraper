package hust.soict.oop.scraper.event;

import java.io.IOException;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectorEventQNL extends CollectorEvent {

    public CollectorEventQNL() {
        super();
    }
    
    public static List<String> extractTextAfter(String specificString, List<String> strings) {
        // Extracts text after a specific string in each element of the list
        List<String> extractedTexts = new ArrayList<>();
        
        for (String str : strings) {
            int index = str.indexOf(specificString);
            if (index != -1) {
                String extractedText = str.substring(index + specificString.length()).trim();
                extractedTexts.add(extractedText);
            }
        }
        
        return extractedTexts;
    }
    
    public static List<String> extractDate(List<String> strings) {
        // Extracts the date from each element of the list
        List<String> extractedTexts = new ArrayList<>();
        
        for (String str : strings) {
            int check = 0;
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (ch == ',') {
                    String extractedText = str.substring(0, i);
                    extractedTexts.add(extractedText);
                    check = 1;
                    break;
                }
            }
            if (check == 0) extractedTexts.add(str);
        }
        
        return extractedTexts;
    }
    
    @Override
    public void collectEventData(String url) {
        try {
            // Connect to the URL and retrieve the document
            Document doc = Jsoup.connect(url).get();
            System.out.printf("\nWebsite Title: %s\n\n", doc.title());
            
            // Extract text from <p> elements and keep only the ones containing "•"
            List<String> strList = doc.select("p").eachText();
            strList.removeIf(str -> !str.contains("•"));
            strList = extractTextAfter("•", strList);

            // Extract dates from the text and store them in a map
            List<String> rawDateList = extractDate(strList);
            Map<String, List<String>> matchedDatesMap = new HashMap<>();

            rawDateList.forEach(str -> {
                // Use regex to find the dates in the string
                Pattern pattern = Pattern.compile("\\b(\\d{1,2}\\.\\d{1,2}\\.\\d{1,4}( TCN| SCN)?|\\d{1,2}-\\d{1,2}-\\d{1,4}( TCN| SCN)?|\\d{1,2}\\.\\d{1,4}( TCN| SCN)?|\\d{1,4}( TCN| SCN)?)\\b");
                Matcher matcher = pattern.matcher(str);

                List<String> matchedDates = new ArrayList<>();
                while (matcher.find()) {
                    String date = matcher.group();
                    if (!matchedDates.contains(date)) matchedDates.add(date);
                }

                matchedDatesMap.put(str, matchedDates);
            });

            // Create Event objects from the extracted data and add them to the events list
            int index = 0;
            for (String str : rawDateList) {
                List<String> matchedDates = matchedDatesMap.get(str);
                String startDate = matchedDates.get(0).replace("-", "/").trim();
                startDate = startDate.replace(".", "/");
                String endDate = matchedDates.size() == 2 ? matchedDates.get(1).replace("-", "/").trim() : "";
                endDate = endDate.replace(".", "/");
                if (endDate.contains("SCN")) 
                	endDate = endDate.replace(" SCN", "");
                else if (endDate.contains("TCN"))
                	startDate = startDate.concat(" TCN");
                if (startDate.equals("113")) startDate = startDate + " TCN";
                Event event = new Event(strList.get(index), HelperDateConverter.convertDate(startDate, "/"), HelperDateConverter.convertDate(endDate, "/"), url);
                events.add(event);
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CollectorEventQNL dataCollector = new CollectorEventQNL();
        dataCollector.collectEventData("https://quynhonland.vn/tom-tat-cac-moc-su-kien-lich-su-viet-nam/");
        dataCollector.printEvents();
        dataCollector.writeEventsToFile("src/main/java/hust/soict/oop/scraper/event/data/qnl_events.json");
    }

}
