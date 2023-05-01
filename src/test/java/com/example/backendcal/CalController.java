package com.example.backendcal;

import com.example.boundaries.WebAPI;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
public class CalController implements WebAPI {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private ICalToJsonConverter iCalToJsonConverter;

    public CalController() throws ParserException, IOException {
        iCalToJsonConverter = new ICalToJsonConverter();
    }


    @Override
    public ResponseEntity<Object> getJsonFile() throws IOException, JSONException, ParserException {
        List<EventJson> events = readEventFile();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = objectMapper.writeValueAsString(events);
        return ResponseEntity.ok(jsonArray);
    }
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            converters.add(new GsonHttpMessageConverter());
        }
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
