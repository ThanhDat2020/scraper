package hust.soict.oop.scraper;
import java.io.IOException;

import javax.swing.text.html.parser.Element;

import org.jsoup.Jsoup; 
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class main {
        /**
         * @param args
         * @throws IOException
         */
        public static void main(String[] args) throws IOException{
            String link = "https://tinhte.vn/thread/nokia-kien-apple-vi-pham-32-ban-quyen-ve-man-hinh-giao-dien-phan-mem-ang-ten.2666744";
            Document doc = Jsoup.connect(link).timeout(5000).get();
            for (int i = 1; i <=9;i++){
                Document docPage = Jsoup.connect(link + "page"+i).timeout(5000).get();
                Elements elementPage = docPage.select("span[class=xf-body-paragraph]");
                for (int j =0  j <elementPage.size();j++){
                    System.out.println(elementPage.get(j).ownText());
                }
            }


        }
    
}
