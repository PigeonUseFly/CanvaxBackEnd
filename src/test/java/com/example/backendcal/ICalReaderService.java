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
import net.fortuna.ical4j.model.component.VEvent;

@Component
public class ICalReaderService {
    private ArrayList<ScheduledAppointment> listCourses = new ArrayList<>();
    ScheduledAppointment scheduledAppointment = new ScheduledAppointment();

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

    public void writeICalEvents(String filePath) throws ParserException, IOException {
        List<VEvent> vEvents = readICalFile(filePath);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Iterator<VEvent> it = vEvents.iterator();

        while(it.hasNext()) {
            VEvent event = it.next();
            String str = event.getSummary().getValue();
            String[] parts = str.split(",");
            String[] name = parts[0].split(": ");
            Date startDate = event.getStartDate().getDate();
            String startDateString = dateFormat.format(startDate);
            Date endDate = event.getEndDate().getDate();
            String endDateString = dateFormat.format(endDate);
            String location = event.getLocation().getValue();
            if(!(location.isEmpty())){
                scheduledAppointment = new ScheduledAppointment(name[1],location, startDateString, endDateString,"");
                listCourses.add(scheduledAppointment);
            }
        }
    }
    public void printICalEvents(){

        for (ScheduledAppointment listed:listCourses) {
            System.out.println(listCourses.size());
            System.out.println(listed);
            System.out.println(listCourses.get(212));
        }
    }
    public ScheduledAppointment listOfLessons(int index){
        return listCourses.get(index);
    }
}