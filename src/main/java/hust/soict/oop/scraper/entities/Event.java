package hust.soict.oop.scraper.entities;
import java.util.*;

public class Event {
	private String age;
	private String dynasty;
	private String eventName;
	private String from;
	private String to;
	private String location;
	private String description;
	private String image;
	private List<String> historicalFigures;
	private List<String> relatedEvents;

    // Constructor
	public Event(String age, String dynasty, String eventName, String from, String to, String location,
			String description, String image, List<String> historicalFigures, List<String> relatedEvents) {
		super();
		this.age = age;
		this.dynasty = dynasty;
		this.eventName = eventName;
		this.from = from;
		this.to = to;
		this.location = location;
		this.description = description;
		this.image = image;
		this.historicalFigures = historicalFigures;
		this.relatedEvents = relatedEvents;
	}

	public String geteventName() {
		return eventName;
	}

	public void seteventName(String eventName) {
		this.eventName = eventName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDynasty() {
		return dynasty;
	}

	public void setDynasty(String dynasty) {
		this.dynasty = dynasty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getHistoricalFigures() {
		return historicalFigures;
	}

	public void setHistoricalFigures(List<String> historicalFigures) {
		this.historicalFigures = historicalFigures;
	}

	public List<String> getRelatedEvents() {
		return relatedEvents;
	}

	public void setRelatedEvents(List<String> relatedEvents) {
		this.relatedEvents = relatedEvents;
	}
   
}

