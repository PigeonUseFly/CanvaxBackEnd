package com.example.backendcal;

import com.example.boundaries.WebAPI;
import com.google.gson.*;
import com.google.gson.GsonBuilder;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.TimeZone;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

@RestController
public class CalController implements WebAPI {
    private ICalToJsonConverter iCalToJsonConverter;

    public CalController() throws ParserException, IOException {
        iCalToJsonConverter = new ICalToJsonConverter();
    }
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
        List<EventJson> events = readJsonFile("events.json");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EventJson> jsonList = new ArrayList<>();
        Iterator iterator = events.iterator();

        while (iterator.hasNext()) {
            EventJson event = (EventJson) iterator.next();
            String summary = event.getSummary();
            String description = event.getDescription();
            Date startDate = event.getStartDate();
            formatter.format(startDate);
            Date endDate = event.getEndDate();
            formatter.format(endDate);
            String location = event.getLocationName();
            EventJson eventJson = new EventJson(summary, description, startDate, endDate, location);
            jsonList.add(eventJson);
        }
        return jsonList;
    }

    /**
     *
     * @param filename
     * @return
     * @throws ParserException
     * @throws IOException
     */
    private static List<EventJson> readJsonFile(String filename) throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        EventList eventList = gson.fromJson(br, EventList.class);
        br.close();

        List<EventJson> events = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone timeZone = TimeZoneRegistryFactory.getInstance().createRegistry().getTimeZone("Europe/Stockholm");

        for (EventJson eventJson : eventList.getEvents()) {
            String summary = eventJson.getSummary();
            String description = eventJson.getDescription();
            Date startDate = eventJson.getStartDate();
            formatter.format(startDate);
            Date endDate = eventJson.getEndDate();
            formatter.format(endDate);
            DateTime startDateTime = new DateTime(startDate, timeZone);
            DateTime endDateTime = new DateTime(endDate, timeZone);
            String locationName = eventJson.getLocationName();

            EventJson event = new EventJson(summary, description, startDate, endDate, locationName);
            events.add(event);
        }
        return events;
    }

    public void removeEvent(int inputFromFrontend) throws IOException {
        iCalToJsonConverter.getEventArrayNode().remove(inputFromFrontend);
        iCalToJsonConverter.getObjectMapper().writeValue(new File("events.json"), iCalToJsonConverter.getParentObjectNode());
    }

    private static class EventList {
        private List<EventJson> events;

        public List<EventJson> getEvents() {
            return events;
        }

        public void setEvents(List<EventJson> events) {
            this.events = events;
        }
    }
}
