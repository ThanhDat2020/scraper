package hust.soict.oop.scraper.festival;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hust.soict.oop.scraper.festival.Festival;

public class FestivalDataCollector {

    private static String getCleanData(String data) {
        String array[] = data.split("(\\[)(\\b([0-9]|[1-9][0-9]|100)\\b)(\\])");
        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        return result;
    }
    
    private static Map<String, Object> insertData(String name, String date, String description, String location) {
    	Map<String, Object> data = new HashMap<String, Object>();
    	data.put("name", name);
        data.put("date", date);
        data.put("description", description);
        data.put("location", location);
        return data;
    }
    
	public static void main(String[] args) {
        try {
            String link = "https://vi.wikipedia.org/wiki/C%C3%A1c_ng%C3%A0y_l%E1%BB%85_%E1%BB%9F_Vi%E1%BB%87t_Nam";
            Document doc;
            doc = Jsoup.connect(link).get();
            ArrayList<Map<String, Object>> festivalList = new ArrayList<Map<String, Object>>();
            
            Elements listTable = doc.select(".wikitable");
            for (int i = 0; i < listTable.size(); i++) {
                Elements listTr = listTable.get(i).select("tr");

                for (int j = 1; j < listTr.size(); j++) {
                    String name = "", date = "", description = "", location = "";
                	Elements listTd = listTr.get(j).select("td");
                    
                    if (i == 0) {
                        for (int k = 0; k < listTd.size(); k++) {
                        	if (k == 0) name = getCleanData(listTd.get(k).text().trim());
                        	else if (k == 1) date = getCleanData(listTd.get(k).text().trim());
                        	else if (k == 2) {
                        		String descField = listTd.get(k).text().trim();
                        		if (descField != "") {
                        			description = getCleanData(listTd.get(k).text().trim());
                        		}
                        		else description = "Not Available";
                        	}
                        }
                        location = "Nationwide";
                    }

                    else if (i == 1) {
                        for (int k = 0; k < listTd.size(); k++) {
                            if (k == 0) date = getCleanData(listTd.get(k).text().trim());
                            else if (k == 1) {
                                name = getCleanData(listTd.get(k).text().trim());
                                if (listTd.get(k).firstElementChild() != null) {
	                                String href1 = listTd.get(k).firstElementChild().attr("href");
	                                if (href1 != "") {
	                                    String getFestivalDesc = "https://vi.wikipedia.org" + href1;
	                                    Document docDesc = Jsoup.connect(getFestivalDesc).get();
	                                    Elements listDesc = docDesc.select("#mw-content-text > div.mw-parser-output > p");
	                                    Elements listDesc1 = docDesc.select("#mw-content-text > div.mw-parser-output > p.mw-empty-elt");
	                                    if  (listDesc1.isEmpty()) {
	                                    	description = getCleanData(listDesc.get(0).text().trim());
	                                    }
	                                    else description = getCleanData(listDesc.get(1).text().trim());
	                                }
	                                else {
	                                	description = "Not Available";
	                                }
                                }
                                else {
                                    description = "Not Available";
                                }
                            }
                            else if (k == 2) location = getCleanData(listTd.get(k).text().trim());
                        }
                    }

                    else if (i == 2) {
                        for (int k = 0; k < listTd.size(); k++) {											 
                            if (k == 0) date = getCleanData(listTd.get(k).text().trim());	          																
                            else if (k == 1){			
                            	Elements brElements = listTd.get(k).select("br");
                            	if(!brElements.isEmpty()) {
                            		name = listTd.get(k).text().trim();
                            	}
                            	else name = getCleanData(listTd.get(k).text().trim());		  
                                if (listTd.get(k).firstElementChild() != null) {		
	                                String href2 = listTd.get(k).firstElementChild().attr("href");
	                                if (href2 != "") {
	                                    String getFestivalDesc = "https://vi.wikipedia.org" + href2;
	                                    Document docDesc = Jsoup.connect(getFestivalDesc).get();
	                                    Elements listDesc = docDesc.select("#mw-content-text > div.mw-parser-output > p");
	                                    Elements listDesc2 = docDesc.select("#mw-content-text > div.mw-parser-output > p.mw-empty-elt");
	                                    if  (listDesc2.isEmpty()) {
	                                    	description = getCleanData(listDesc.get(0).text().trim());
	                                    }
	                                    else {
	                                    	description = getCleanData(listDesc.get(1).text().trim());
	                                    }
	                                }
	                                else {
	                                	description = "Not Available";
	                                }
                                }
                                else {
                                    description = "Not Available";
                                }
                            }
                        }
                        location = "Nationwide";
                    }
                    
                    else {
                    	for (int k = 0; k < listTd.size(); k++) {
                        	if (k == 0) date = getCleanData(listTd.get(k).text().trim());
                        	else if (k == 1) {
                        		name = getCleanData(listTd.get(k).text().trim());
                        		if (listTd.get(k).firstElementChild() != null) {		
	                                String href2 = listTd.get(k).firstElementChild().attr("href");
	                                if (href2 != "") {
	                                    String getFestivalDesc = "https://vi.wikipedia.org" + href2;
	                                    Document docDesc = Jsoup.connect(getFestivalDesc).get();
	                                    Elements listDesc = docDesc.select("#mw-content-text > div.mw-parser-output > p");
	                                    Elements listDesc2 = docDesc.select("#mw-content-text > div.mw-parser-output > p.mw-empty-elt");
	                                    if  (listDesc2.isEmpty()) {
	                                    	description = getCleanData(listDesc.get(0).text().trim());
	                                    }
	                                    else {
	                                    	description = getCleanData(listDesc.get(1).text().trim());
	                                    }
	                                }
	                                else {
	                                	description = "Not Available";
	                                }
                                }
                                else {
                                    description = "Not Available";
                                }
                        	}
                        }
                        location = "Nationwide";
                    }
                    
                    festivalList.add(insertData(name, date, description, location));

                }
            }
            try (FileWriter fileWriter = new FileWriter("src/main/java/hust/soict/oop/scraper/festival/data/festivals.json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonString = gson.toJson(festivalList);
                fileWriter.write(jsonString);
                fileWriter.flush();
                System.out.println("\nData written to file successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
}
