package hust.soict.oop.scraper.event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollectorEventWiki extends CollectorEvent {
	
	public CollectorEventWiki() {
		super();
	}

    @Override
    public void collectEventData(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
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
                        processParagraph(currentElement, ageText, dynastyList, yearList, url);
                    } else if (currentElement.tagName().equals("dl")) {
                        processDefinitionList(currentElement, ageText, dynastyList, yearList, url);
                    }
                    currentElement = currentElement.nextElementSibling();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processParagraph(Element element, String ageText, List<String> dynastyList, List<String> yearList, String url) {
        String time = element.select("b").text();
        String[] years = time.split("-|–");
        String startYear = years[0].contains("tháng ") ? years[0].replace("tháng ", "").trim() : years[0].trim();
        String endYear = years.length > 1 ? (years[1].contains("tháng ") ? years[1].replace("tháng ", "").trim() : years[1].trim()) : "";
        String event = element.text().replace(time, "").trim();
        if (!event.isBlank()) {
        	Elements links = element.getElementsByTag("a");
        	String description = "";
        	Image image = new Image();
        	
        	for (int i = 0; i < links.size(); i++) {
        		Document doc = null;
        		Elements eleDocument = null;
        		String href = links.get(i).attr("href");
    			try {
    				doc = Jsoup.connect("https://vi.wikipedia.org/"+href).get();
    				eleDocument = doc.select("div[class=mw-parser-output]");
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			description = description + " " + (eleDocument.select("p").size() != 0 ? eleDocument.select("p").get(0).text() : "");
    			Elements imageUrls = doc.getElementsByClass("mw-file-description");
    			for (int j = 0; j < imageUrls.size(); j++) {
    				String imgUrl = imageUrls.get(j) == null || imageUrls.get(j).attr("href").contains(".svg|.SVG") ? "" : imageUrls.get(j).attr("href");
    				String caption = imageUrls.get(j).nextElementSibling() == null ? "" : imageUrls.get(j).nextElementSibling().text();
    				
    				if (imgUrl != "" && caption != "") {
        				image.setImageUrl(imgUrl);
        				image.setCaption(caption);
        				break;
    				}
    			}
        	}
        	
        	description = description.replaceAll("\\[[^\\]]*\\]", "").replaceAll("\\s+", " ").trim();
        	startYear = startYear.replace(".", "");
            endYear = endYear.replace(".", "");
        	Event eventEntity = new Event(ageText, dynastyList.get(0), event, HelperDateConverter.convertDate(startYear, " "), HelperDateConverter.convertDate(endYear, " "), description, image, url);
        	events.add(eventEntity);
        } else {
            yearList.set(0, time);
        }
    }

    private void processDefinitionList(Element element, String ageText, List<String> dynastyList, List<String> yearList, String url) {
        Elements ddElements = element.select("dd");
        ddElements.forEach(ddElement -> {
            String rawTime = ddElement.select("b").text();
            String event = ddElement.text().replace(rawTime, "").trim();
            String[] years = rawTime.split("-|–");
            String startYear = (years[0].contains("tháng ") ? years[0].replace("tháng ", "") : years[0]).trim().concat(" ").concat(yearList.get(0));
            String endYear = years.length > 1 ? (years[1].contains("tháng ") ? years[1].replace("tháng ", "") : years[1]).trim().concat(" ").concat(yearList.get(0)) : "";
            
            Elements links = ddElement.getElementsByTag("a");
        	String description = "";
        	Image image = new Image();
        	
        	for (int i = 0; i < links.size(); i++) {
        		Document doc = null;
        		Elements eleDocument = null;
        		String href = links.get(i).attr("href");
    			try {
    				doc = Jsoup.connect("https://vi.wikipedia.org/"+href).get();
    				eleDocument = doc.select("div[class=mw-parser-output]");
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			description = description + " " + (eleDocument.select("p").size() != 0 ? eleDocument.select("p").get(0).text() : "");
    			Elements imageUrls = doc.getElementsByClass("mw-file-description");
    			for (int j = 0; j < imageUrls.size(); j++) {
    				String imgUrl = imageUrls.get(j) == null || imageUrls.get(j).attr("href").contains(".svg|.SVG") ? "" : imageUrls.get(j).attr("href");
    				String caption = imageUrls.get(j).nextElementSibling() == null ? "" : imageUrls.get(j).nextElementSibling().text();
    				
    				if (imgUrl != "" && caption != "") {
        				image.setImageUrl(imgUrl);
        				image.setCaption(caption);
        				break;
    				}
    			}
        	}
        	
        	description = description.replaceAll("\\[[^\\]]*\\]", "").replaceAll("\\s+", " ").trim();
            startYear = startYear.replace(".", "");
            endYear = endYear.replace(".", "");
        	Event eventEntity = new Event(ageText, dynastyList.get(0), event, HelperDateConverter.convertDate(startYear, " "), HelperDateConverter.convertDate(endYear, " "), description, image, url);
            events.add(eventEntity);
        });
    }

    public static void main(String[] args) {
        CollectorEventWiki dataCollector = new CollectorEventWiki();
        dataCollector.collectEventData("https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam");
        dataCollector.printEvents();
        dataCollector.writeEventsToFile("src/main/java/hust/soict/oop/scraper/event/data/wiki_events.json");
    }
}
