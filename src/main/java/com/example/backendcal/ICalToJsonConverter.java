package com.example.backendcal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Class that converts the iCal-file to a json-file.
 */
public class ICalToJsonConverter {
    private ArrayNode eventArrayNode;
    private ObjectMapper objectMapper;
    private ObjectNode parentObjectNode;
    private ObjectNode rootNode;
    private HashMap<String, Event> hashMap = new HashMap();

    public static void main(String[] args) throws ParserException, IOException {
        ICalToJsonConverter iCalToJsonConverter = new ICalToJsonConverter();
    }

    public ICalToJsonConverter() throws ParserException, IOException {
        createJsonFile();
    }

    /**
     * Method that uses the method to read the iCal-file and creates objects out of the events and writes these to the "events.json"-file.
     * @throws ParserException
     * @throws IOException
     */
    public void createJsonFile() throws ParserException, IOException {
        List<VEvent> events = readICalFile("ical/SchemaICAL.ics");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Iterator icalIterator = events.iterator();

        while (icalIterator.hasNext()) {
            VEvent event = (VEvent) icalIterator.next();
            String summary = event.getSummary().getValue();
            int start = summary.indexOf("Program:");
            int end = summary.indexOf("Moment:");
            String id = event.getUid().getValue();
            id = id.replace("\r\n", "");
            String moment = summary.substring(start, end);
            StringBuilder stringBuilder = new StringBuilder(summary);
            stringBuilder.delete(start, end);
            String newSummary = String.valueOf(stringBuilder);
            Date startDate = event.getStartDate().getDate();
            dateFormat.format(startDate);
            Date endDate = event.getEndDate().getDate();
            dateFormat.format(endDate);
            String location = event.getLocation().getValue();
            Event eventJson = new Event(newSummary, moment, startDate, endDate, location);
            hashMap.put(id, eventJson);
            changesInHashmap("events.json");
        }
    }

    /**
     * Method that re-writes the "events.json"-file to essentially update the file after a new event was added.
     * @param filename
     * @throws IOException
     */
    public void changesInHashmap(String filename) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        rootNode = objectMapper.createObjectNode();
        for(Map.Entry<String, Event> entry : hashMap.entrySet()){
            ObjectNode eventNode = objectMapper.createObjectNode();
            Event event = entry.getValue();

            eventNode.put("id",entry.getKey());
            eventNode.put("summary", event.getSummary());
            eventNode.put("moment", event.getDescription());
            eventNode.put("startDate", dateFormat.format(event.getStartDate()));
            eventNode.put("endDate", dateFormat.format(event.getEndDate()));
            eventNode.put("location", event.getLocationName());

            rootNode.set(entry.getKey(), eventNode);
        }
        FileWriter fileWriter = new FileWriter(filename);
        objectMapper.writeValue(fileWriter, rootNode);
    }

    /**
     * Method that reads the iCal-file.
     * @param filename
     * @return A list of VEvent-objects.
     * @throws ParserException
     * @throws IOException
     */
    private static List<VEvent> readICalFile(String filename) throws ParserException, IOException {
        InputStream inputStream = new FileInputStream(new File(filename));
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(inputStream);
        return calendar.getComponents("VEVENT");
    }

    public HashMap<String, Event> getHashMap(){
        return hashMap;
    }
}
