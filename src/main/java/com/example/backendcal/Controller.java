package com.example.backendcal;

import com.example.backendcal.boundaries.WebAPI;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.fortuna.ical4j.data.ParserException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Klass som sköter logiken i programmet samt hanterar REST API till frontend.
 */
@RestController
@CrossOrigin(origins = "*")
public class Controller implements WebAPI {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private ICalToJsonConverter iCalToJsonConverter;

    public Controller() throws ParserException, IOException {
        iCalToJsonConverter = new ICalToJsonConverter();
    }

    /**
     * Endpoint som läser filen "events.json" och skickar till frontend.
     * @return Datan och header som säger vad för mediatype som skickas.
     * @throws IOException
     */
    @Override
    public ResponseEntity<Object> getJsonFile() throws IOException {
        File file = new File("events.json");
        String jsonString = new String(Files.readAllBytes(file.toPath()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(jsonString, headers, HttpStatus.OK);
    }

    /**
     * Endpoint som tar bort ett event i "events.json"-filen som har samma index som skickades från frontend.
     * @param id Indexet som skickas från frontend för att avgöra vilket event som ska tas bort.
     * @throws IOException
     */
    public void removeEvent(String id) throws IOException, ParserException {
        System.out.println("Ta bort " + id);
        iCalToJsonConverter.getHashMap().remove(id);
        iCalToJsonConverter.changesInHashmap("events.json");
    }

    /**
     * Endpoint för att lägga till ett nytt event i "events.json"-filen.
     * @param summary Kort beskrivning av eventet.
     * @param description Utförlig beskrivning av eventet.
     * @param startDateString Datum/tid när eventet börjar.
     * @param endDateString Datum/tid när eventet slutar.
     * @param location Plats för event.
     * @throws IOException
     * @throws ParseException
     * @throws JSONException
     */
    public void insertEvent(String summary, String description, String startDateString, String endDateString, String location) throws IOException, ParseException, JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = formatter.parse(startDateString);
        Date endDate = formatter.parse(endDateString);
        Event event = new Event(summary, description, startDate, endDate, location);
        String eventData = objectMapper.writeValueAsString(event);

        BufferedReader bufferedReader = new BufferedReader(new FileReader("events.json"));
        String jsonString = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonString += line;
        }
        bufferedReader.close();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray eventArray = jsonObject.getJSONArray("events");
        JSONObject eventObject = new JSONObject(eventData);
        eventArray.put(eventObject);

        FileWriter fileWriter = new FileWriter("events.json");
        fileWriter.write(jsonObject.toString(2) + System.lineSeparator());
        fileWriter.close();
    }

    /**
     * Inre klass som ger endpointsen funktionalitet för att konvertera json och Http-forms.
     */
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            converters.add(new GsonHttpMessageConverter());
            converters.add(new FormHttpMessageConverter());
        }

        public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
            converters.stream()
                    .filter(c -> c instanceof FormHttpMessageConverter)
                    .forEach(c -> ((FormHttpMessageConverter) c).setCharset(StandardCharsets.UTF_8));
        }
    }

}
