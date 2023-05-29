package datacollection;

import entity.Figure;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NKSDataCollector  extends FigureDataCollector{
    public NKSDataCollector() {
    }

    @Override
    public List<String> getAllUrls(String url) throws IOException {
        // TODO Auto-generated method stub
        
        List<String> allLinks = new ArrayList<String>();

        for (int i = 0; i < 291; i++) {
            Document doc = getDoc(url + "?start=" + i * 5);
            
            Elements eLinkCharater = doc.select("h2");
            
            for (Element r : eLinkCharater) {
                String link = r.select("a").attr("href");
                allLinks.add("https://nguoikesu.com" + link);
            }
        }

        return allLinks;
    }

    @Override
    public void getData(List<String> allUrls) throws IOException {
        // TODO Auto-generated method stub
        

        Writer writer = new FileWriter("src/Data/nks.json");
            writer.write("[\n");
        int count = 0;
        for (String url : allUrls) {
            count++;
            String name = null;
            String description = "";
            String sinh = null;
            String mat = null;
            String time;
            
            Figure figure = new Figure();

            Document doc = getDoc(url);

            name = doc.select("div.page-header h2").text();
            figure.setName(name);

            Elements infobox = doc.select("table.infobox");

            if (infobox.size() > 0) {
                Elements rows = infobox.get(0).select("tr");
                

                for (Element r : rows) {
                    Elements eKey = r.select("th");
                    Elements eValue = r.select("td");
                    if (eKey.size() > 0 && eValue.size() > 0) {
                        String key = eKey.text();
                        String value = eValue.text();

                        if (key.equals("Sinh")) {
                            sinh = eValue.text();
                        }
                        if (key.equals("Mất")) {
                            mat = eValue.text();
                        }

                            description += key + ": " + value + "\n";
                    }        
                    if (eValue.size() == 0) {
                        String value = r.text();
                        description += value + "\n";
                    }
                }
                time = (sinh == null ? "Không rõ" : sinh) + " - " + (mat == null ? "Không rõ" : mat);
                figure.setTime(time);

                Elements articleBody = doc.select("div[itemprop = articleBody]");
                Elements paragraphs = articleBody.select("div[itemprop = articleBody] ~ *");
                if (paragraphs.size() > 0) {
                    for (Element p : paragraphs) {
                        if (!p.text().equals("")) {
                            description += p.text() + "\n";
                        }
                    }
                }
                figure.setDescription(description);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(figure, writer);
                writer.flush();
                writer.write(",\n");



            }

        }

        System.out.println(count);    
        writer.write("\n]");
        writer.close();
        System.out.println("Done");
    }

    @Override
    public void Start() throws IOException {
        // TODO Auto-generated method stub
        String url = "https://nguoikesu.com/nhan-vat";
        List<String> allLinks= getAllUrls(url);
        

        NKSDataCollector nks = new NKSDataCollector();
        nks.getData(allLinks);

    }
    

    public static void main(String[] args) throws IOException {
        NKSDataCollector nks = new NKSDataCollector();
        List<String> allLinks= nks.getAllUrls("https://nguoikesu.com/nhan-vat");
        nks.getData(allLinks);
    }
    
}
