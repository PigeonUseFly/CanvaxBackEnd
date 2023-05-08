package com.example.backendcal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Klass som konverterar iCal-filen till en .json-fil.
 */
public class ICalToJsonConverter {
    private ArrayNode eventArrayNode;
    private ObjectMapper objectMapper;
    private ObjectNode parentObjectNode;

    /**
     * Main metod som kallar på konstruktorn som i sin tur kallar på metoden som skapar .json-filen.
     * @param args
     * @throws ParserException
     * @throws IOException
     */
    public static void main(String[] args) throws ParserException, IOException {
        ICalToJsonConverter iCalToJsonConverter = new ICalToJsonConverter();
    }

    /**
     *  Konstruktor som kallar på metoden för att skapa .json-filen.
     * @throws ParserException
     * @throws IOException
     */
    public ICalToJsonConverter() throws ParserException, IOException {
        createJsonFile();
    }

    /**
     * Metod som konverterar datan från readIcalFile-metoden för att skapa EventJson-objekt
     * och skriva dessa till filen "events.json".
     * @throws ParserException
     * @throws IOException
     */
    public void createJsonFile() throws ParserException, IOException {
        List<VEvent> events = readICalFile("ical/SchemaICAL.ics");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EventJson> eventJsonList = new ArrayList();
        Iterator icalIterator = events.iterator();

        while(icalIterator.hasNext()) {
            VEvent event = (VEvent)icalIterator.next();
            String summary = event.getSummary().getValue();
            int start = summary.indexOf("Program:");
            int end = summary.indexOf("Moment:");
            String moment = summary.substring(start, end);
            StringBuilder stringBuilder = new StringBuilder(summary);
            stringBuilder.delete(start, end);
            String newSummary = String.valueOf(stringBuilder);
            Date startDate = event.getStartDate().getDate();
            dateFormat.format(startDate);
            Date endDate = event.getEndDate().getDate();
            dateFormat.format(endDate);
            String location = event.getLocation().getValue();
            HashMap<Event> events = new HashMap(); //Denna ska inte ligga här!!
            EventJson eventJson = new EventJson(newSummary, moment, startDate, endDate, location);
            eventJsonList.add(eventJson);
        }

        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        eventArrayNode = objectMapper.createArrayNode();
        Iterator eventIterator = eventJsonList.iterator();

        while(eventIterator.hasNext()) {
            EventJson eventJson = (EventJson)eventIterator.next();
            ObjectNode eventObjectNode = objectMapper.createObjectNode();
            eventObjectNode.put("summary", eventJson.getSummary());
            eventObjectNode.put("description", eventJson.getDescription());
            eventObjectNode.put("startDate", dateFormat.format(eventJson.getStartDate()));
            eventObjectNode.put("endDate", dateFormat.format(eventJson.getEndDate()));
            eventObjectNode.put("locationName", eventJson.getLocationName());
            eventArrayNode.add(eventObjectNode);
        }

        parentObjectNode = objectMapper.createObjectNode();
        parentObjectNode.set("events", eventArrayNode);
        objectMapper.writeValue(new File("events.json"), parentObjectNode);
    }

    /**
     * Metod som läser in iCal-filen och skapar ett Calendar-objekt av dessa.
     * @param filename
     * @return
     * @throws ParserException
     * @throws IOException
     */
    private static List<VEvent> readICalFile(String filename) throws ParserException, IOException {
        InputStream inputStream = new FileInputStream(new File(filename));
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(inputStream);
        return calendar.getComponents("VEVENT");
    }

    public ArrayNode getEventArrayNode() {
        return eventArrayNode;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public ObjectNode getParentObjectNode() {
        return parentObjectNode;
    }
}
