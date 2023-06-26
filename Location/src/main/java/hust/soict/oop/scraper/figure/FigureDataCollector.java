package hust.soict.oop.scraper.figure;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FigureDataCollector {

    public static void main(String[] args) throws IOException {
        String url = "http://vietycotruyen.com.vn/cac-trieu-dai-viet-nam-qua-tung-thoi-ky-lich-su";
        Document doc = Jsoup.connect(url)
                .header("Accept-Charset", "UTF-8")
                .get();

        Element table = doc.select("table").first();
        Elements rows = table.select("tr");

        List<Figure> figures = new ArrayList<>();

        String[] columnNames = {"name", "age", "gender", "3"};

        for (int i = 1; i < rows.size(); i++) { // Bắt đầu từ 1 để bỏ qua dòng tiêu đề
            Element row = rows.get(i);
            Elements columns = row.select("td");

            Figure figure = new Figure();

            for (int j = 1; j < columns.size(); j++) {
                Element column = columns.get(j);
                String columnName = columnNames[j - 1];

                String cellData = column.text();
                String fixedCellData = fixFontIssue(cellData); // Gọi phương thức fixFontIssue để sửa lỗi font chữ

                figure.setProperty(columnName, fixedCellData);
            }

            figures.add(figure);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try (FileWriter fileWriter = new FileWriter("C:/Users/84332/Downloads/scraper-fest/scraper-fest/src/main/java/hust/soict/oop/scraper/figure/data/figures.json")) {
            objectMapper.writeValue(fileWriter, figures);
            System.out.println("Dữ liệu đã được ghi vào tệp JSON thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String fixFontIssue(String text) {
        // Sử dụng Apache Commons Text để xử lý lỗi font chữ
        String escapedText = StringEscapeUtils.unescapeJava(text);

        // Chuyển đổi mã hoá
        String decodedText = new String(escapedText.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        return decodedText;
    }

    static class Figure {
        private String name;
        private String age;
        private String gender;
        private String property3;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getProperty3() {
            return property3;
        }

        public void setProperty3(String property3) {
            this.property3 = property3;
        }

        public void setProperty(String propertyName, String value) {
            switch (propertyName) {
                case "name":
                    setName(value);
                    break;
                case "age":
                    setAge(value);
                    break;
                case "gender":
                    setGender(value);
                    break;
                case "3":
                    setProperty3(value);
                    break;
                default:
                    // Unknown property
                    break;
            }
        }
    }
}
