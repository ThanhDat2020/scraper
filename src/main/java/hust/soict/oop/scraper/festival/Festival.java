package hust.soict.oop.scraper.festival;
import java.util.*;

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

public class Festival {
    private String name;
    private String date;
    private String location;
    private String description;
    private Image image;
    private List<String> historicalFigures;
    private String significance;
    private String cause;
    private String outcome;
    private List<String> relatedEvents;
    private String source;

    // Constructor
    public Festival(String name, String date, String location, String description, Image image, String source) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.image = image;
        this.source = source;
        //this.historicalFigures = new ArrayList<>();
        //this.relatedEvents = new ArrayList<>();
    }
    
    public Festival() {
    		
    }

    // Getters and Setters for the attributes
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
		return image; 
	}

	public void setImage(Image image) {
		this.image = image;
	}
    
    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHistoricalFigures() {
        return historicalFigures;
    }

    public void addHistoricalFigure(String historicalFigure) {
        this.historicalFigures.add(historicalFigure);
    }

    public String getSignificance() {
        return significance;
    }

    public void setSignificance(String significance) {
        this.significance = significance;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public List<String> getRelatedEvents() {
        return relatedEvents;
    }

    public void addRelatedEvent(String relatedEvent) {
        this.relatedEvents.add(relatedEvent);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
