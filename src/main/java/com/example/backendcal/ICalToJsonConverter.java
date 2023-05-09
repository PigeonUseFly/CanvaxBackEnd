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

    public void createJsonFile() throws ParserException, IOException {
        int index = 0;
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

/*
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        eventArrayNode = objectMapper.createArrayNode();
        Iterator eventIterator = eventJsonList.iterator();

        while(eventIterator.hasNext()) {
            EventJson eventJson = (EventJson)eventIterator.next();
            ObjectNode eventObjectNode = objectMapper.createObjectNode();
            eventObjectNode.put("index", index++);
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

 */
    }



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
    public ObjectNode getRootNode(){
        return rootNode;
    }
    public HashMap<String, Event> getHashMap(){
        return hashMap;
    }
}
