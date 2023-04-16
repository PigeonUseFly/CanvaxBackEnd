package com.example.backendcal;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class ICalReaderService {

    public ICalReaderService(){

    }

    public List<VEvent> readICalFile(String filePath) throws IOException, ParserException {
        List<VEvent> vEvents = new ArrayList<>();
        CalendarBuilder builder = new CalendarBuilder();
        InputStream is = new FileInputStream(filePath);

        try {
            Calendar calendar = builder.build(is);
            Iterator<?> it = calendar.getComponents().iterator();

            while(it.hasNext()) {
                Object o = it.next();
                if (o instanceof VEvent) {
                    VEvent event = (VEvent)o;
                    vEvents.add(event);
                }
            }
        } catch (Throwable ex) {
            try {
                is.close();
            } catch (Throwable t) {
                ex.addSuppressed(t);
            }

            throw ex;
        }

        is.close();
        return vEvents;
    }

    public void printICalEvents(String filePath) throws ParserException, IOException {
        List<VEvent> vEvents = readICalFile(filePath);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Iterator<VEvent> it = vEvents.iterator();

        while(it.hasNext()) {
            VEvent event = it.next();
            System.out.println("Event Summary: " + event.getSummary().getValue());
            Date startDate = event.getStartDate().getDate();
            String startDateString = dateFormat.format(startDate);
            System.out.println("Event Start Time: " + startDateString);
            Date endDate = event.getEndDate().getDate();
            String endDateString = dateFormat.format(endDate);
            System.out.println("Event End Time: " + endDateString);
            System.out.println("Event Location: " + event.getLocation().getValue());
            System.out.println();
        }
    }

}