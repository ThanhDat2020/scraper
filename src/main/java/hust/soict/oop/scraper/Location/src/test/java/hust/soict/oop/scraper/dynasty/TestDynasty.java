package hust.soict.oop.scraper.dynasty;
import java.util.*;

public class TestDynasty {
    private String name;
    private String date;
    private String location;
    private String description;
    private List<String> historicalFigures;
    private String significance;
    private String cause;
    private String outcome;
    private List<String> relatedEvents;
    private List<String> sources;

    // Constructor
    public TestDynasty(String name, String date, String location, String description) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.historicalFigures = new ArrayList<>();
        this.relatedEvents = new ArrayList<>();
        this.sources = new ArrayList<>();
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

    public List<String> getSources() {
        return sources;
    }

    public void addSource(String source) {
        this.sources.add(source);
    }
}
