
package hust.soict.oop.scraper.figure;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;




public abstract class FigureDataCollector {
	//constructor
	public FigureDataCollector() {}

	//check connection
	public boolean Connect(String url) {
		try {
			Connection.Response response = Jsoup.connect(url).execute();
			int statusCode = response.statusCode();
			
			if (statusCode == 200) {
				return true;
			}
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Document getDoc(String url) throws IOException {	
		Connection con = Jsoup.connect(url);
		Document doc = con.get();
		return doc;	
	}
	

	
	public abstract  List<String> getAllUrls (String url) throws IOException;
	public abstract void getData (List<String> allUrls) throws IOException;
	public abstract void Start() throws IOException;


	
}
