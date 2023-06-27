package hust.soict.oop.scraper.figure;

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




public class KingDataCollector extends FigureDataCollector {
	
	public KingDataCollector() {}
	

	
	public List<String> getAllUrls (String url) throws IOException{
		List<String> allLinks = new ArrayList<>();
		
		Document doc = getDoc(url);
		//select tbody la con truc tiep cua table
		Elements table = doc.select("table[cellpadding = 0] tbody");
		
		
		//cho vao list
		for (Element e : table) {
			Elements row = e.select("tr[style *= height:50]"); //chon tr co attr la style co height:50
			
			for (Element e1 : row) {
				Element td = e1.select("td").get(1);
				Element link = td.getElementsByTag("a").get(0);
				allLinks.add(link.attr("href"));
				
			}
		}
	
//		System.out.println(allLinks.size());
		return  allLinks;
	}

	@Override
	public void getData(List<String> allUrls) throws IOException {
		// TODO Auto-generated method stub

		String name;
		String country;
//		String description;
		
		String triVi;
		String sinh;
		String mat;
		
		
		String huy;
		String nienHieu;
		String thuyHieu;
		String mieuHieu;
		
		String trieuDai;
		String tienNhiem;
		String keNhiem;
		
		String anTang;
		String tonGiao;
		
		String thanPhu;
		String thanMau;
		String theThiep;

		Writer file = new FileWriter("scraper/src/main/java/hust/soict/oop/scraper/figure/data/king.json");
		file.write("[\n");
		
		
		for (int i=0; i < allUrls.size(); i++) {
			Document doc = getDoc("https://vi.wikipedia.org" + allUrls.get(i));
			Elements info = doc.getElementsByClass("infobox").select("[style = width:22em]");
			King k = new King();


			Elements paragraph = doc.select("div [class = mw-parser-output] > p ");
			if (paragraph.size() > 0){
				k.setDescription(paragraph.get(0).text());
			}
	//		System.out.println(k.description);
			
			
				Elements eName = info.select("th");
				if (eName.size() > 0){
					name = eName.get(0).text();
					k.setName(name);
//					System.out.println(name);
				}
								
				
				Elements eCountry = info.select("th[style *= background: #cbe]");
				if (eCountry.size() > 0){
					country = eCountry.get(1).text();
					k.setCountry(country);
//					System.out.println(k.country);
				}
				
				//get pictureLink
				Elements ePicture = info.select("img");
				if (ePicture.size() > 0) {
					String pictureLink = ePicture.get(0).attr("src");
					k.setPictureLink("https:" + pictureLink);
				}
				else {
					k.setPictureLink("Không có ảnh");
				}
				// get info
				Elements keys = info.select("th");
				for (Element e : keys) {
					
					if (e.text().equals("Trị vì") || e.text().equals("Tại vị")) {		//1
						triVi = e.siblingElements().get(0).text();
						k.setTriVi(triVi);
//						System.out.println("Tri vi: " + k.triVi);
					}
					else {
						if (k.getTriVi() == null) {
							k.setTriVi("Không rõ");
						}
					}					

					if (e.text().equals("Sinh")) {		//2
						sinh = e.siblingElements().get(0).text();
						k.setSinh(sinh);
//						System.out.println("Sinh: " + k.sinh);
					}
					else {
						if (k.getSinh() == null) {
							k.setSinh("Không rõ");
						}
					}

					if (e.text().equals("Mất")) {	//3
						mat = e.siblingElements().get(0).text();
						k.setMat(mat);
//						System.out.println("Mat: " + k.mat);
					}
					else {
						if (k.getMat() == null) {
							k.setMat("Không rõ");
						}
					}

					if (e.text().equals("Húy")) {			//4
						Elements eHuy = e.siblingElements();
						if (eHuy.size() > 0) {
							huy = eHuy.get(0).text();
							k.setHuy(huy);
//							System.out.println("Huy: " + k.huy);
						}
						else {
							Element eHuy1 = e.parent().nextElementSibling();
							huy = eHuy1.text();
							k.setHuy(huy);
//							System.out.println("Huy: " + k.huy);
						}
						
					}
					else {
						if (k.getHuy() == null) {
							k.setHuy("Không rõ");
						}
					}
					
					if (e.text().equals("Niên hiệu")) {	//5
						Elements eNienHieu = e.siblingElements();
						if (eNienHieu.size() > 0) {
							nienHieu = eNienHieu.get(0).text();
							k.setNienHieu(nienHieu);
//							System.out.println("Nien hieu: " + k.nienHieu);
						}
						else {
							Element eNienHieu1 = e.parent().nextElementSibling();
							nienHieu = eNienHieu1.text();
							k.setNienHieu(nienHieu);
//							System.out.println("Nien hieu: " + k.nienHieu);
						}						
					}
					else {
						if (k.getNienHieu() == null) {
							k.setNienHieu("Không rõ");
						}
					}

					if (e.text().equals("Thuỵ hiệu")) {	//6
						Elements eThuyHieu = e.siblingElements();
						if (eThuyHieu.size() > 0) {
							thuyHieu = eThuyHieu.get(0).text();
							k.setThuyHieu(thuyHieu);
//							System.out.println("Thuy hieu: " + k.thuyHieu);
						}
						else {
							Element eThuyHieu1 = e.parent().nextElementSibling();
							thuyHieu = eThuyHieu1.text();
							k.setThuyHieu(thuyHieu);
//							System.out.println("Thuy hieu: " + k.thuyHieu);
						}
					}
					else {
						if (k.getThuyHieu() == null) {
							k.setThuyHieu("Không rõ");
						}
					}

					if (e.text().equals("Miếu hiệu")) {	//7
						Elements eMieuHieu = e.siblingElements();
						if (eMieuHieu.size() > 0) {
							mieuHieu = eMieuHieu.get(0).text();
							k.setMieuHieu(mieuHieu);
//							System.out.println("Mieu hieu: " + k.mieuHieu);
						}
						else {
							Element eMieuHieu1 = e.parent().nextElementSibling();
							mieuHieu = eMieuHieu1.text();
							k.setMieuHieu(mieuHieu);
//							System.out.println("Mieu hieu: " + k.mieuHieu);
						}
					}
					else {
						if (k.getMieuHieu() == null) {
							k.setMieuHieu("Không rõ");
						}
					}

					if (e.text().equals("Triều đại")) {	//8
						trieuDai = e.siblingElements().get(0).text();
						k.setTrieuDai(trieuDai);
//						System.out.println("Trieu dai: " + k.trieuDai);
					}
					else {
						if (k.getTrieuDai() == null) {
							k.setTrieuDai("Không rõ");
						}
					}

					if (e.text().equals("Tiền nhiệm")) {	//9
						tienNhiem = e.siblingElements().get(0).text();
						k.setTienNhiem(tienNhiem);
//						System.out.println("Tien nhiem: " + k.tienNhiem);
					}
					else {
						if (k.getTienNhiem() == null) {
							k.setTienNhiem("Không rõ");
						}
					}

					if (e.text().equals("Kế nhiệm")) {		//10
						keNhiem = e.siblingElements().get(0).text();
						k.setKeNhiem(keNhiem);
//						System.out.println("Ke nhiem: " + k.keNhiem);
					}
					else {
						if (k.getKeNhiem() == null) {
							k.setKeNhiem("Không rõ");
						}
					}

					if (e.equals("An táng")) {		//11
						anTang = e.siblingElements().get(0).text();
						k.setAnTang(anTang);
//						System.out.println("An tang: " + k.anTang);
					}
					else {
						if (k.getAnTang() == null) {
							k.setAnTang("Không rõ");
						}
					}

					if (e.equals("Tôn giáo")) {
						tonGiao = e.siblingElements().get(0).text();
						k.setTonGiao(tonGiao);
//						System.out.println("Ton giao: " + k.tonGiao);
					}
					else {
						if (k.getTonGiao() == null) {
							k.setTonGiao("Không rõ");
						}
					}

					if (e.text().equals("Thân phụ")) {		//12
						thanPhu = e.nextElementSibling().text();
						k.setThanPhu(thanPhu);
//						System.out.println("Than phu: " + k.thanPhu);
					}
					else {
						if (k.getThanPhu() == null) {
							k.setThanPhu("Không rõ");
						}
						
					}

					if (e.text().equals("Thân mẫu")) {		//13
						thanMau = e.nextElementSibling().text();
						k.setThanMau(thanMau);
//						System.out.println("Than mau: " + k.thanMau);
					}
					else {
						if (k.getThanMau() == null) {
							k.setThanMau("Không rõ");
						}
					}

					if (e.text().equals("Thê thiếp")) {	//14
						theThiep = e.nextElementSibling().text();
						k.setTheThiep(theThiep);
//						System.out.println("The thiep: " + k.theThiep);
					}
					else {
						if (k.getTheThiep() == null) {
							k.setTheThiep("Không rõ");
						}
					}

				}

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				gson.toJson(k, file);
				file.flush();		// đẩy dữ liệu từ bộ nhớ dem xuống file ngay lập tức
				if (i < allUrls.size() - 1){
					file.write(",\n");
				}
				else {
					file.write("\n");
				}
				file.flush();
				
				
		}
		
			file.write("]");
			file.flush();
			file.close();		
		}
		
		
		
	

	@Override
	public void Start() throws IOException {
		// TODO Auto-generated method stub

		String url = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam";
		
		KingDataCollector k = new KingDataCollector();
		List<String> allUrls = k.getAllUrls(url);
		k.getData(allUrls);
		

		
	}
	
	public static void main(String[] args) throws IOException {
		KingDataCollector k = new KingDataCollector();
		k.getData(k.getAllUrls("https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam"));;
	// 	System.out.println(k.getAllUrls("https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam").size());
	}
	//166 nv
}
