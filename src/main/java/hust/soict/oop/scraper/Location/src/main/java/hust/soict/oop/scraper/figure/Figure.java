package hust.soict.oop.scraper.figure;

public class Figure {

    public String name;
    public String otherName;
    public String time;
    public String place;
    public String period;
    public String description;

   public Figure(String name, String time, String description, String place, String period, String otherName){
        this.name = name;
        this.time = time;
        this.description = description;
        this.place = place;
        this.period = period;
            this.otherName = otherName;

   }

   public Figure() {
   }
   
   public String getName() {
       return name;
   }
    public void setName(String name) {
         this.name = name;
    }
    public String getDescription() {
         return description;
    }
    public void setDescription(String description) {
         this.description = description;
    }
    public String getTime() {
         return time;
    }
    public void setTime(String time) {
         this.time = time;
    }
    public String getPlace() {
         return place;
    }
    public void setPlace(String place) {
         this.place = place;
    }
     public String getPeriod() {
           return period;
     }
     public void setPeriod(String period) {
           this.period = period;
     }
     public String getOtherName() {
           return otherName;
     }
     public void setOtherName(String otherName) {
           this.otherName = otherName;
     }
    
}
