package Schema;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* public class ICalReader {

    public static List<VEvent> readICalFile(String filePath) throws IOException, ParserException {
        List<VEvent> vEvents = new ArrayList<>();

        // create a new CalendarBuilder
        CalendarBuilder builder = new CalendarBuilder();

        // read the iCal file into an InputStream
        try (InputStream is = new FileInputStream(filePath)) {
            // parse the InputStream into a Calendar object
            Calendar calendar = builder.build(is);

            // iterate through each component in the Calendar object
            for (Object o : calendar.getComponents()) {
                // if the component is a VEVENT, add it to the list of VEvents
                if (o instanceof VEvent) {
                    VEvent event = (VEvent) o;
                    vEvents.add(event);
                }
            }
        }

        return vEvents;
    }
    public static void main(String[] args) throws ParserException, IOException {
        ICalReader reader = new ICalReader();
        List<VEvent> events = reader.readICalFile("C:/Users/Ermin/Downloads/Schema.ics");

        String filePath = ("C:/Users/Ermin/Downloads/Schema.ics");

        try {
            List<VEvent> vEvents = readICalFile(filePath);

            // iterate through each VEvent object and print its details
            for (VEvent event : vEvents) {
                System.out.println("Event Summary: " + event.getSummary().getValue());
                String originalDate = String.valueOf(event.getStartDate().getDate());
                String result = originalDate.substring(0,8);
                System.out.println("Event Start Time: " + result);
                System.out.println("Event End Time: " + event.getEndDate().getDate());
                System.out.println("Event Location: " + event.getLocation().getValue());
                System.out.println();



                System.out.println("test");
            }
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }
    }
    }
    */

public class ICalReader {
    public static List<VEvent> readICalFile(String filePath) throws IOException, ParserException {
        List<VEvent> vEvents = new ArrayList<>();

        // create a new CalendarBuilder
        CalendarBuilder builder = new CalendarBuilder();

        // read the iCal file into an InputStream
        try (InputStream is = new FileInputStream(filePath)) {
            // parse the InputStream into a Calendar object
            Calendar calendar = builder.build(is);

            // iterate through each component in the Calendar object
            for (Object o : calendar.getComponents()) {
                // if the component is a VEVENT, add it to the list of VEvents
                if (o instanceof VEvent) {
                    VEvent event = (VEvent) o;
                    vEvents.add(event);
                }
            }
        }

        return vEvents;
    }

    public static void main(String[] args) throws ParserException, IOException {
        ICalReader reader = new ICalReader();
        List<VEvent> events = reader.readICalFile("C:/Users/Ermin/Downloads/Schema.ics");

        String filePath = ("C:/Users/Ermin/Downloads/Schema.ics");

        try {
            List<VEvent> vEvents = readICalFile(filePath);

            // create a SimpleDateFormat object to format the date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // iterate through each VEvent object and print its details
            for (VEvent event : vEvents) {
                System.out.println("Event Summary: " + event.getSummary().getValue());

                // get the start date as a Date object
                Date startDate = event.getStartDate().getDate();

                // format the start date as a string
                String startDateString = dateFormat.format(startDate);

                // print the start time
                System.out.println("Event Start Time: " + startDateString);

                // get the end date as a Date object
                Date endDate = event.getEndDate().getDate();

                // format the end date as a string
                String endDateString = dateFormat.format(endDate);

                // print the end time
                System.out.println("Event End Time: " + endDateString);

                System.out.println("Event Location: " + event.getLocation().getValue());
                System.out.println();
            }
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }
    }
}

