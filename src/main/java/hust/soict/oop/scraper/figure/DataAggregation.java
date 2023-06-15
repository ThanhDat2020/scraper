package hust.soict.oop.scraper.figure;

import java.io.IOException;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DataAggregation {
    public List<Figure> loadDataNKS() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/hust/soict/oop/scraper/figure/data/nks.json"));
        Gson gson = new GsonBuilder().create();
        Figure[] figures = gson.fromJson(reader, Figure[].class);
        List<Figure> NKS = Arrays.asList(figures);
        return NKS;
    }

    public List<Figure> loadDataVS() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/hust/soict/oop/scraper/figure/data/vansu.json"));
        Gson gson = new GsonBuilder().create();
        Figure[] figures = gson.fromJson(reader, Figure[].class);
        List<Figure> VS = Arrays.asList(figures);
        return VS;
    }

    public List<Figure> Aggregation(List<Figure> NKS, List<Figure> VS) {
        List<Figure> figure = new ArrayList<Figure>();

        int count = 0;
        int countNKS = 0;
        int countVS = 0;
        int countSame = 0;

        for (Figure nks : NKS) {
            countNKS++;
            boolean check = false;
            String nameNKS = nks.getName();
            for (Figure vs : VS) {
                
                String nameVS = vs.getName();
                if (nameNKS.equals(nameVS)) {
                    countSame++;
                    check = true;
                    String name = nameVS;
                    String timeNKS = nks.getTime();
                    String timeVS = vs.getTime();
                    String time = "Không rõ";

                    if (!timeNKS.equals("Không rõ - Không rõ") && !timeVS.equals("Không rõ")) {
                        time = timeNKS + " --- (theo nguoikesu.com)" + "\n" + timeVS + " --- (theo vansu.vn)";
                    }

                    if (!timeNKS.equals("Không rõ - Không rõ") && timeVS.equals("Không rõ")) {
                        time = timeNKS;
                    }

                    if (timeNKS.equals("Không rõ - Không rõ") && !timeVS.equals("Không rõ")) {
                        time = timeVS;
                    }

                    String descriptionNKS = nks.getDescription();
                    String descriptionVS = vs.getDescription();
                    String description = descriptionNKS + " --- theo nguoikesu.com" + "\n" + descriptionVS + " --- theo vansu.vn";

                    String otherName = vs.getOtherName();
                    String place = vs.getPlace();
                    String period = vs.getPeriod();

                    Figure f = new Figure(name, time, description, otherName, place, period);

                    figure.add(f);
                    count++;
                }
            }

            if (check == false) {
                figure.add(nks);
                count++;
            }
        }

        for (Figure vs : VS) {
            boolean check = false;
            String nameVS = vs.getName();
            
            for (Figure f : figure) {
                String name = f.getName();
                if (nameVS.equals(name)) {
                    check = true;
                    break;
                }

                if (check == false) {
                    figure.add(vs);
                    count++;
                }
            }
        }

        System.out.println("Số lượng nhân vật trong nguoikesu.com: " + countNKS);
        System.out.println("Số lượng nhân vật trong vansu.vn: " + countVS);
        System.out.println("Số lượng nhân vật trùng nhau: " + countSame);
        System.out.println("Số lượng nhân vật sau khi gộp: " + count);
        
        return figure;
    }

    public static void Start() throws IOException {
        DataAggregation d = new DataAggregation();
        List<Figure> listNKS = d.loadDataNKS();
        List<Figure> listVS = d.loadDataVS();

        List<Figure> f = d.Aggregation(listNKS, listVS);

        Writer file = new FileWriter("src/main/java/hust/soict/oop/scraper/figure/data/figure.json");

        file.write("[\n");
        for (Figure figure : f) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(figure, file);
            file.write(",\n");
            file.flush();
        }
        file.write("]");
        file.flush();
    }

    public static void main(String[] args) throws IOException {
        Start();
    }
}
