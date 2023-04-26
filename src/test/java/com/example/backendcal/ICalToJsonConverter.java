package com.example.backendcal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.*;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

public class ICalToJsonConverter {
    ArrayNode eventArrayNode;
    ObjectMapper objectMapper;
    ObjectNode parentObjectNode;

    public ICalToJsonConverter() throws ParserException, IOException {
        start();
    }

    public static void main(String[] args) throws ParserException, IOException {
        ICalToJsonConverter iCalToJsonConverter = new ICalToJsonConverter();
    }
    public void start() throws ParserException, IOException {
        int index = 0;
        ObjectNode jsonNode = new ObjectNode(JsonNodeFactory.instance);

        List<VEvent> events = readICalFile("ical/SchemaICAL.ics");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EventJson> eventJsonList = new ArrayList();
        Iterator icalIterator = events.iterator();

        while(icalIterator.hasNext()) {
            VEvent event = (VEvent)icalIterator.next();
            String summary = event.getSummary().getValue();
            int start = summary.indexOf("Moment:");
            int end = summary.indexOf("Program:");
            String moment = summary.substring(start, end);
            StringBuilder sb = new StringBuilder(summary);
            sb.delete(start, end);
            String newSummary = String.valueOf(sb);
            Date startDate = event.getStartDate().getDate();
            dateFormat.format(startDate);
            Date endDate = event.getEndDate().getDate();
            dateFormat.format(endDate);
            String location = event.getLocation().getValue();
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
}
