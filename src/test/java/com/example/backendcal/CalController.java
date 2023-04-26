package com.example.backendcal;

import com.example.boundaries.WebAPI;
import com.google.gson.*;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

@RestController
public class CalController implements WebAPI {
  /*  public ResponseEntity<String> getProgram(String id) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/plain"))
                .body("Käften " + id);
    } */

    public ResponseEntity<Object> getProgram(String id) throws IOException, ParserException {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/plain"))
                .body(readEventFile() + id);
    }
    /**
     * Method that uses the readICalFile method to read the file "ical/SchemaICal.ics"
     * and turn these events into objects of EventJson class and return these in a list.
     * @return Objects of EventJson in a list.
     * @throws IOException
     * @throws ParserException
     */
    public List<EventJson> readEventFile() throws IOException, ParserException {
        List<VEvent> events = readICalFile("ical/SchemaICAL.ics");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EventJson> jsonList = new ArrayList<>();
        Iterator iterator = events.iterator();

        while (iterator.hasNext()) {
            VEvent event = (VEvent) iterator.next();
            String summary = event.getSummary().getValue();
            int start = summary.indexOf("Moment:");
            int end = summary.indexOf("Program:");
            String moment = summary.substring(start, end);
            Date startDate = event.getStartDate().getDate();
            formatter.format(startDate);
            Date endDate = event.getEndDate().getDate();
            formatter.format(endDate);
            String location = event.getLocation().getValue();
            EventJson eventJson = new EventJson(summary, moment,startDate, endDate, location);
            jsonList.add(eventJson);
        }
        return jsonList;
    }

    private static List<VEvent> readICalFile(String filename) throws ParserException, IOException {
        InputStream inputStream = new FileInputStream(new File(filename));
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(inputStream);
        return calendar.getComponents("VEVENT");
    }
}
