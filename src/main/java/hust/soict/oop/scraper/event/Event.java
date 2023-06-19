package hust.soict.oop.scraper.event;

class Image {
	
	private String imageUrl;
	private String caption;

	public Image() {
		super();
	}
	
	public Image(String imageUrl, String caption) {
		super();
		this.imageUrl = imageUrl;
		this.caption = caption;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
}

public class Event {
	
	private String event;
	private String from;
	private String to;
	private String age;
	private String dynasty;
	private String description;
	private Image image;
	private String source;
	
	public Event() {
		super();
	}
	
	public Event(String event, String from, String to, String source) {
		super();
		this.event = event;
		this.from = from;
		this.to = to;
		this.source = source;
	}
	
	public Event(String age, String dynasty, String event, String from, String to, String description, Image image, String source) {
		this(event, from, to, source);
		this.age = age;
		this.dynasty = dynasty;
		this.description = description;
		this.image = image;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getImage() {
		return image; 
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return getTo() == "" ?
				age + "   " + dynasty + "   " + getEvent() + "   " + getFrom() 
				: age + "   " + dynasty + "   " + getEvent() + "   " + getFrom() + " - " + getTo();
	}

}
