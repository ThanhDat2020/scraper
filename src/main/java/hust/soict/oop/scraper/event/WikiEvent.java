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

public class WikiEvent extends Event {
	
	private String age;
	private String dynasty;
	private String description;
	private Image image;

	public WikiEvent(String age, String dynasty, String event, String from, String to, String description, Image image) {
		super(event, from, to);
		this.age = age;
		this.dynasty = dynasty;
		this.description = description;
		this.image = image;
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

	@Override
	public String toString() {
		return getTo() == "" ?
				age + "   " + dynasty + "   " + getEvent() + "   " + getFrom() 
				: age + "   " + dynasty + "   " + getEvent() + "   " + getFrom() + " - " + getTo();
	}

}
