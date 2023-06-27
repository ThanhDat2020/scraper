package hust.soict.oop.scraper.dynasty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynastyDataCollector {

    public static void main(String[] args) throws IOException {
        String url = "http://vietycotruyen.com.vn/cac-trieu-dai-viet-nam-qua-tung-thoi-ky-lich-su";
        Document doc = Jsoup.connect(url)
                .header("Accept-Charset", "UTF-8")
                .get();

        Element table = doc.select("table").first();
        Elements rows = table.select("tr");

        List<Figure> figures = new ArrayList<>();

        String[] columnNames = {"dynasty", "king", "period", "longevity"};

        for (int i = 1; i < rows.size(); i++) { // Bắt đầu từ 1 để bỏ qua dòng tiêu đề
            Element row = rows.get(i);
            Elements columns = row.select("td");

            Figure figure = new Figure();

            StringBuilder rowData = new StringBuilder(); // Chuỗi lưu dữ liệu của mỗi hàng

            for (int j = 0; j < columns.size() && j < columnNames.length; j++) {
                Element column = columns.get(j);
                String columnName = columnNames[j];

                String cellData = column.text();

                figure.setProperty(columnName, cellData);

                rowData.append(cellData); // Thêm dữ liệu của mỗi ô vào chuỗi hàng

                if (j < columns.size() - 1) {
                    rowData.append("\n"); // Thêm ký tự xuống dòng nếu chưa phải ô cuối cùng trong hàng
                }
            }

            figures.add(figure);

            System.out.println(rowData.toString()); // In dữ liệu của mỗi hàng
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\84332\\Downloads\\Location\\src\\main\\java\\hust\\soict\\oop\\scraper\\dynasty\\data\\dynasties.json")) {
            objectMapper.writeValue(fileWriter, figures);
            System.out.println("Dữ liệu đã được ghi vào tệp JSON thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Figure {
        private String dynasty;
        private String king;
        private String period;
        private String longevity;

        public String getDynasty() {
            return dynasty;
        }

        public void setDynasty(String dynasty) {
            this.dynasty = dynasty;
        }

        public String getKing() {
            return king;
        }

        public void setKing(String king) {
            this.king = king;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getLongevity() {
            return longevity;
        }

        public void setLongevity(String longevity) {
            this.longevity = longevity;
        }

        public void setProperty(String propertyName, String value) {
            switch (propertyName) {
                case "dynasty":
                    setDynasty(value);
                    break;
                case "king":
                    setKing(value);
                    break;
                case "period":
                    setPeriod(value);
                    break;
                case "longevity":
                    setLongevity(value);
                    break;
                default:
                    // Unknown property
                    break;
            }
        }
    }
}
