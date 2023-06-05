package hust.soict.oop.scraper.event;

public class WikiEvent extends Event {
	
	private String age;
	private String dynasty;

	public WikiEvent(String age, String dynasty, String event, String from, String to) {
		super(event, from, to);
		this.age = age;
		this.dynasty = dynasty;
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
	
	@Override
	public String toString() {
		return getTo() == "" ?
				age + "   " + dynasty + "   " + getEvent() + "   " + getFrom() 
				: age + "   " + dynasty + "   " + getEvent() + "   " + getFrom() + " - " + getTo();
	}

}
