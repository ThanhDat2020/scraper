package hust.soict.oop.scraper.entities;
//import java.util.*;

public class Event {
	private String age;
	private String dynasty;
	private String event;
	private String date;
//	private String to;
//	private String location;
//	private String description;
//	private String image;
//	private List<String> historicalFigures;
//	private List<String> relatedEvents;
	
	public Event(String age, String dynasty, String event, String date) {
		super();
		this.age = age;
		this.dynasty = dynasty;
		this.event = event;
		this.date = date;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDynasty() {
		return dynasty;
	}

	public void setDynasty(String dynasty) {
		this.dynasty = dynasty;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
   
}

