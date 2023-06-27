package hust.soict.oop.scraper.festival;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hust.soict.oop.scraper.festival.Festival;

public class FestivalDataCollector {
	
	private static String timeReformatter(String time) {
		String tmp[] = time.split("[ngày|tháng|năm|[-]+|đến hết|,|(\\\\b([0-9]|[1-9][0-9]|100)\\\\b)| ]");
		String result = "";
		if (tmp.length == 0) {
			result = time.replace(" tháng ", "/");
			result = result.replace(" năm ", "/");
			result = result.replace("ngày ", "");
			
			return result;
		}
		else return time;
	}
	
    private static String getCleanData(String data) {
        String array[] = data.split("(\\[)(\\b([0-9]|[1-9][0-9]|100)\\b)(\\])"); //Get rid of reference hyperlinks
        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        
        return result;
    }
    
    private static Festival insertData(String name, String date, String description, String location, Image image, String source) {
    	Festival festival = new Festival();
    	festival.setName(name);
    	festival.setDate(date);
    	festival.setDescription(description);
    	festival.setLocation(location);
    	festival.setImage(image);
    	festival.setSource(source);
        return festival;
    }
    
	public static void main(String[] args) {
        try {
            String link = "https://vi.wikipedia.org/wiki/C%C3%A1c_ng%C3%A0y_l%E1%BB%85_%E1%BB%9F_Vi%E1%BB%87t_Nam";
            Document doc;
            doc = Jsoup.connect(link).get();
            ArrayList<Festival> festivalList = new ArrayList<Festival>();
            
            Elements listTable = doc.select(".wikitable");
            for (int i = 0; i < listTable.size(); i++) {
                Elements listTr = listTable.get(i).select("tr");

                for (int j = 1; j < listTr.size(); j++) {
                    String name = "", date = "", description = "", location = "";
                    String source = "";
                    Image image = new Image();
                	Elements listTd = listTr.get(j).select("td");
                    if (i == 0) {
                        for (int k = 0; k < listTd.size(); k++) {
                        	if (k == 0) {
                        		name = getCleanData(listTd.get(k).text().trim());
                        		if (listTd.get(k).firstElementChild() != null) {		
	                                String href = listTd.get(k).firstElementChild().attr("href");
	                                Document imageDoc = null;
	                                if (href != "") {
	                                	try {
	                        				imageDoc = Jsoup.connect("https://vi.wikipedia.org/"+href).get();
	                        			} catch (IOException e) {
	                        				e.printStackTrace();
	                        			}
	                                	Elements imageSrc = imageDoc.getElementsByClass("mw-file-description");
	                                	for (int x = 0; x < imageSrc.size(); x++) {
		                                	String imageUrl = (imageSrc.get(x) == null || imageSrc.get(x).attr("href").contains(".svg|.SVG")) ? "" : imageSrc.get(x).attr("href");
		                    				String caption = (imageSrc.get(x).nextElementSibling() == null) ? "" : imageSrc.get(x).nextElementSibling().text();
		                    				if (imageUrl != "" && caption != "") {
		                        				image.setImageUrl(imageUrl);
		                        				image.setCaption(caption);
		                        				break;
		                    				}
	                                	}
	                                }
                        		}
                        	}
                        	
                        	else if (k == 1) date = timeReformatter(getCleanData(listTd.get(k).text().trim()));
                        	else if (k == 2) {
                        		String descField = listTd.get(k).text().trim();
                        		description = (descField != "") ? getCleanData(listTd.get(k).text().trim()) : "Not Available";
                        	}
                        }
                        location = "Toàn quốc";
                    }

                    else if (i == 1) {
                        for (int k = 0; k < listTd.size(); k++) {
                            if (k == 0) date = timeReformatter(getCleanData(listTd.get(k).text().trim()));
                            else if (k == 1) {
                                name = getCleanData(listTd.get(k).text().trim());
                                if (listTd.get(k).firstElementChild() != null) {
	                                String href1 = listTd.get(k).firstElementChild().attr("href");
	                                Document imgNDescDoc = null;
	                                if (href1 != "") {
	                                	try {
	                        				imgNDescDoc = Jsoup.connect("https://vi.wikipedia.org/"+href1).get();
	                        			} catch (IOException e) {
	                        				e.printStackTrace();
	                        			}
	                                	Elements imageSrc = imgNDescDoc.getElementsByClass("mw-file-description");
	                                	for (int x = 0; x < imageSrc.size(); x++) {
		                                	String imageUrl = (imageSrc.get(x) == null || imageSrc.get(x).attr("href").contains(".svg|.SVG")) ? "" : imageSrc.get(x).attr("href");
		                    				String caption = (imageSrc.get(x).nextElementSibling() == null) ? "" : imageSrc.get(x).nextElementSibling().text();
		                    				if (imageUrl != "" && caption != "") {
		                        				image.setImageUrl(imageUrl);
		                        				image.setCaption(caption);
		                        				break;
		                    				}
	                                	}
	                                    Elements listDesc = imgNDescDoc.select("#mw-content-text > div.mw-parser-output > p");
	                                    Elements listDesc1 = imgNDescDoc.select("#mw-content-text > div.mw-parser-output > p.mw-empty-elt");
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
                            if (k == 0) date = timeReformatter(getCleanData(listTd.get(k).text().trim()));	          																
                            else if (k == 1){			
                            	name = getCleanData(listTd.get(k).text().trim());
                            	if (name.contains(")")) name = name.split("\\)")[0] + ")";
                                if (listTd.get(k).firstElementChild() != null) {		
	                                String href2 = listTd.get(k).firstElementChild().attr("href");
	                                Document imgNDescDoc = null;
	                                if (href2 != "") {
	                                	try {
	                        				imgNDescDoc = Jsoup.connect("https://vi.wikipedia.org/"+href2).get();
	                        			} catch (IOException e) {
	                        				e.printStackTrace();
	                        			}
	                                	Elements imageSrc = imgNDescDoc.getElementsByClass("mw-file-description");
	                                	for (int x = 0; x < imageSrc.size(); x++) {
		                                	String imageUrl = (imageSrc.get(x) == null || imageSrc.get(x).attr("href").contains(".svg|.SVG")) ? "" : imageSrc.get(x).attr("href");
		                    				String caption = (imageSrc.get(x).nextElementSibling() == null) ? "" : imageSrc.get(x).nextElementSibling().text();
		                    				if (imageUrl != "" && caption != "") {
		                        				image.setImageUrl(imageUrl);
		                        				image.setCaption(caption);
		                        				break;
		                    				}
	                                	}
	                                    Elements listDesc = imgNDescDoc.select("#mw-content-text > div.mw-parser-output > p");
	                                    Elements listDesc1 = imgNDescDoc.select("#mw-content-text > div.mw-parser-output > p.mw-empty-elt");
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
                        }
                        location = "Toàn quốc";
                    }
                    
                    else {
                    	for (int k = 0; k < listTd.size(); k++) {
                        	if (k == 0) date = timeReformatter(getCleanData(listTd.get(k).text().trim()));
                        	else if (k == 1) {
                        		name = getCleanData(listTd.get(k).text().trim());
                        		if (listTd.get(k).firstElementChild() != null) {		
	                                String href3 = listTd.get(k).firstElementChild().attr("href");
	                                Document imgNDescDoc = null;
	                                if (href3 != "") {
	                                	try {
	                        				imgNDescDoc = Jsoup.connect("https://vi.wikipedia.org/"+href3).get();
	                        			} catch (IOException e) {
	                        				e.printStackTrace();
	                        			}
	                                	Elements imageSrc = imgNDescDoc.getElementsByClass("mw-file-description");
	                                	for (int x = 0; x < imageSrc.size(); x++) {
		                                	String imageUrl = (imageSrc.get(x) == null || imageSrc.get(x).attr("href").contains(".svg|.SVG")) ? "" : imageSrc.get(x).attr("href");
		                    				String caption = (imageSrc.get(x).nextElementSibling() == null) ? "" : imageSrc.get(x).nextElementSibling().text();
		                    				if (imageUrl != "" && caption != "") {
		                        				image.setImageUrl(imageUrl);
		                        				image.setCaption(caption);
		                        				break;
		                    				}
	                                	}
	                                    Elements listDesc = imgNDescDoc.select("#mw-content-text > div.mw-parser-output > p");
	                                    Elements listDesc1 = imgNDescDoc.select("#mw-content-text > div.mw-parser-output > p.mw-empty-elt");
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
                        }
                        location = "Toàn quốc";
                    }
                    
                    source = link;
                    festivalList.add(insertData(name, date, description, location, image, source));

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
