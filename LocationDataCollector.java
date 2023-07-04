package hust.soict.oop.scraper.location;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LocationDataCollector {
    public static void main(String[] args) {
        try {
            // Kết nối và tải trang web
            Document doc = Jsoup.connect("https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam").get();

            // Tìm tất cả các bảng trong trang web
            Elements tables = doc.select("table.wikitable");

            // Tạo danh sách để lưu trữ dữ liệu từ các bảng
            List<String[]> data = new ArrayList<>();

            // Lấy dữ liệu từ tất cả các bảng
            for (Element table : tables) {
                // Lấy danh sách các hàng trong bảng
                Elements rows = table.select("tr");

                // Lấy các dòng dữ liệu từ bảng
                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements columns = row.select("td");

                    // Kiểm tra số lượng cột và bỏ qua hàng nếu không đủ
                    if (columns.size() >= 5) {
                        String[] rowData = new String[5];

                        // Lấy dữ liệu từ các cột
                        rowData[0] = columns.get(0).text();
                        rowData[1] = columns.get(1).text();
                        rowData[2] = columns.get(2).text();
                        rowData[3] = columns.get(3).text();
                        rowData[4] = columns.get(4).text();

                        data.add(rowData);
                    }
                }
            }

            // Tạo đối tượng JSON từ danh sách data
            JSONArray jsonArray = new JSONArray();
            for (String[] rowData : data) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Di tích", rowData[0]);
                jsonObject.put("vị trí", rowData[1]);
                jsonObject.put("loại di tích", rowData[2]);
                jsonObject.put("năm công nhận", rowData[3]);
                jsonObject.put("ghi chú", rowData[4]);
                jsonArray.put(jsonObject);
            }

            // Ghi dữ liệu vào file JSON
            String json = jsonArray.toString(4);
            String filePath = "C:\\Users\\84332\\Downloads\\Location\\src\\main\\java\\hust\\soict\\oop\\scraper\\location\\data\\locations.json";
            Files.write(Paths.get(filePath), json.getBytes());

            System.out.println("Dữ liệu đã được ghi vào file locations.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
