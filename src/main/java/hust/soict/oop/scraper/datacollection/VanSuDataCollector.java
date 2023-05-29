package datacollection;

import entity.Figure;

import java.util.List;
import java.util.ArrayList;

//import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class VanSuDataCollector extends FigureDataCollector {

    VanSuDataCollector() {
    }

    @Override
    public List<String> getAllUrls(String url) throws IOException {
        // TODO Auto-generated method stub
        
        List<String> allLinks = new ArrayList<String>();
        for (int i = 0; i < 120; i++) {
            Document doc = getDoc(url + "?page=" + Integer.toString(i));
            Elements eLink = doc.select("table[class = ui selectable celled table] tbody tr");

            for (Element row : eLink) {
                Element href = row.selectFirst("td > a");
                String link = href.attr("href");
                allLinks.add("https://vansu.vn" + link);
            }
        }
        return allLinks;
    }

    @Override
    public void getData(List<String> allUrls) throws IOException {
        // TODO Auto-generated method stub
        Writer writer = new FileWriter("src/Data/vansu.json");
        writer.write("[\n");
        int count = 0;

        for (String url : allUrls) {
            String name;
            String otherName = null;
            String description = "";
            String time = null;
            String period = null;
            String place = null;


            Figure figure = new Figure();
            Document doc = getDoc(url);

            name = doc.select("div[class = active section]").get(0).text();
            figure.setName(name);
//            System.out.println(name);
            Elements rows = doc.select("table[class = ui selectable celled table] tbody tr");
            for (Element row : rows) {
                Elements td = row.select("td");
                if (td.size() > 1) {
                    String key = td.get(0).text();
                    String value = td.get(1).text();
                
                    if (key.equals("Tên khác")) {
                        otherName = value;    
                        figure.setOtherName(otherName);               
                    }
                    if (key.equals("Thời kì")) {
                        period = value;
                        figure.setPeriod(period);
                    }
                    if (key.equals("Năm sinh")) {
                        time = value;
                        figure.setTime(time);
                    }
                    if (key.equals("Tỉnh thành")) {
                        place = value;
                        figure.setPlace(place);
                    }
                }
                
                else{
                    Elements pTags = row.select("td > p");
                    if (pTags.size() > 0) {
                        for (Element pTag : pTags) {
                            description += pTag.text() + "\n";
                        }               
                }
                }
                
            }
            if (otherName == null) {
                figure.setOtherName("Không rõ");
            }
            if (period == null) {
                figure.setPeriod("Không rõ");
            }
            if (time == null) {
                figure.setTime("Không rõ");
            }
            if (place == null) {
                figure.setPlace("Không rõ");
            }
            
            figure.setDescription(description);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(figure, writer);
            writer.flush();
            if (count < allUrls.size() - 1) {
                writer.write(",\n");
            }
            count++;

        }
        writer.write("\n]");
        writer.close();
        
    }
    
    @Override
    public void Start() throws IOException {
        // TODO Auto-generated method stub

        VanSuDataCollector vs = new VanSuDataCollector();
        List<String> allUrls = vs.getAllUrls("https://vansu.vn/viet-nam/viet-nam-nhan-vat");
        vs.getData(allUrls);
        
        
    }
    
    public static void main(String[] args) throws IOException{
        VanSuDataCollector vs = new VanSuDataCollector();
        vs.getData(vs.getAllUrls("https://vansu.vn/viet-nam/viet-nam-nhan-vat"));
    }
}
