package hust.soict.oop.scraper.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationDataCollector {

    public static void main(String[] args) throws IOException {
        String url = "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam";
        Document doc = Jsoup.connect(url)
                .header("Accept-Charset", "UTF-8")
                .get();

        Elements tables = doc.select("table");

        List<Figure> figures = new ArrayList<>();

        String[] columnNames = {"relics", "location", "type", "year of recognition", "note"};

        for (Element table : tables) {
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements columns = row.select("td");

                Figure figure = new Figure();

                for (int j = 0; j < columns.size() && j < columnNames.length; j++) {
                    Element column = columns.get(j);
                    String columnName = columnNames[j];

                    String cellData = column.text();

                    figure.setProperty(columnName, cellData);
                }

                figures.add(figure);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\84332\\Downloads\\Location\\src\\main\\java\\hust\\soict\\oop\\scraper\\location\\data\\locations.json")) {
            objectMapper.writeValue(fileWriter, figures);
            System.out.println("Dữ liệu đã được ghi vào tệp JSON thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Figure {
        private String relics;
        private String location;
        private String type;
        private String yearOfRecognition;
        private String note;

        public String getRelics() {
            return relics;
        }

        public void setRelics(String relics) {
            this.relics = relics;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getYearOfRecognition() {
            return yearOfRecognition;
        }

        public void setYearOfRecognition(String yearOfRecognition) {
            this.yearOfRecognition = yearOfRecognition;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public void setProperty(String propertyName, String value) {
            switch (propertyName) {
                case "relics":
                    setRelics(value);
                    break;
                case "location":
                    setLocation(value);
                    break;
                case "type":
                    setType(value);
                    break;
                case "year of recognition":
                    setYearOfRecognition(value);
                    break;
                case "note":
                    setNote(value);
                    break;
                default:
                    // Unknown property
                    break;
            }
        }
    }
}
