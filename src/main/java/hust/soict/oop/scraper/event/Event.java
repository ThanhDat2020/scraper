package hust.soict.oop.scraper.event;
//import java.util.*;

public class Event {
	private String event;
	private String from;
	private String to;
//	private String location;
//	private String description;
//	private String image;
//	private List<String> historicalFigures;
//	private List<String> relatedEvents;
	
	public Event(String event, String from, String to) {
		super();
		this.event = event;
		this.from = from;
		this.to = to;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
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
	
	@Override
	public String toString() {
		return to == "" ? event + "   " + from : event + "   " + from + " - " + to;
	}
}

