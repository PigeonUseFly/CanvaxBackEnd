package com.example.backendcal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
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

public class ICalReaderService {
    ArrayNode eventArrayNode;
    ObjectMapper objectMapper;
    ObjectNode parentObjectNode;
    public ICalReaderService() throws ParserException, IOException {
        start();
    }

    public static void main(String[] args) throws ParserException, IOException {
        ICalReaderService iCalReaderService = new ICalReaderService();
    }
    public void start() throws ParserException, IOException {
        int index = 0;
        ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);

        List<VEvent> events = readICalFile("ical/SchemaICAL.ics");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EventJson> eventJsonList = new ArrayList();
        Iterator var4 = events.iterator();

        while(var4.hasNext()) {
            VEvent event = (VEvent)var4.next();
            String summary = event.getSummary().getValue();
            Date startDate = event.getStartDate().getDate();
            dateFormat.format(startDate);
            Date endDate = event.getEndDate().getDate();
            dateFormat.format(endDate);
            String location = event.getLocation().getValue();
            EventJson eventJson = new EventJson(summary, startDate, endDate, location);
            eventJsonList.add(eventJson);
        }

        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        eventArrayNode = objectMapper.createArrayNode();
        Iterator var15 = eventJsonList.iterator();

        while(var15.hasNext()) {
            EventJson eventJson = (EventJson)var15.next();
            ObjectNode eventObjectNode = objectMapper.createObjectNode();
            eventObjectNode.put("index", index++);
            eventObjectNode.put("summary", eventJson.getSummary());
            eventObjectNode.put("startDate", dateFormat.format(eventJson.getStartDate()));
            eventObjectNode.put("endDate", dateFormat.format(eventJson.getEndDate()));
            eventObjectNode.put("location", eventJson.getLocation());
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
    public ArrayNode getEventArrayNode(){
        return eventArrayNode;
    }
    public ObjectMapper getObjectMapper(){
        return objectMapper;
    }
    public ObjectNode getParentObjectNode(){
        return parentObjectNode;
    }
}
