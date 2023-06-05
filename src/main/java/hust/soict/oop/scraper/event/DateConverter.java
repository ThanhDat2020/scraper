package hust.soict.oop.scraper.event;

public class DateConverter {
    public static String convertDate(String inputDate) {
        String[] dateParts = inputDate.split(" ");
        if (dateParts.length == 3) {
            String day = dateParts[0];
            String month = dateParts[1];
            String year = dateParts[2];
            
            // Check if the date parts are valid integers
            if (isInteger(day) && isInteger(month) && isInteger(year)) {
                // Format the day with leading zeros if necessary
                day = formatWithLeadingZeros(Integer.parseInt(day));
                
                // Format the month with leading zeros if necessary
                month = formatWithLeadingZeros(Integer.parseInt(month));
                
                return day + "/" + month + "/" + year;
            }
        } else if (dateParts.length == 2) {
            String month = dateParts[0];
            String year = dateParts[1];
            
            // Check if the date parts are valid integers
            if (isInteger(month) && isInteger(year)) {
                // Format the month with leading zeros if necessary
                month = formatWithLeadingZeros(Integer.parseInt(month));
                
                return month + "/" + year;
            }
        }
        
        // Return the original input if it doesn't match the expected formats
        return inputDate;
    }
    
    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static String formatWithLeadingZeros(int value) {
        return String.format("%02d", value);
    }
}