package com.example.backendcal;

import com.example.backendcal.boundaries.WebAPI;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.fortuna.ical4j.data.ParserException;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Class that handles the logic in the program and also implements the REST API to frontend.
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
     * Endpoint that reads the file "events.json" and sends this to frontend.
     * @return Data and header that defines what Mediatype that will be sent.
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
     * Endpoint that removes an event in the "events.json"-file which has the same index that was sent from frontend.
     * @param id The index that was sent from frontend to decide which event to remove.
     * @throws IOException
     */
    public void removeEvent(String id) throws IOException {
        System.out.println("Ta bort " + id);
        iCalToJsonConverter.getHashMap().remove(id);
        iCalToJsonConverter.changesInHashmap("events.json");
    }

    /**
     * Endpoint to add a new event in the "events.json"-file.
     * @param summary Short summary of the event.
     * @param description Detailed description of the event.
     * @param startDate Date/time for when the event starts.
     * @param endDate Date/time for when the event stops.
     * @param location Location for the event.
     * @throws IOException
     * @throws ParseException
     * @throws JSONException
     */
    public void insertEvent(String summary, String description, String startDate, String endDate, String location) throws IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date formattedStartDate = formatter.parse(startDate);
        Date formattedEndDate = formatter.parse(endDate);
        Event event = new Event(summary, description, formattedStartDate, formattedEndDate, location);
        String uniqueID = UUID.randomUUID().toString();
        iCalToJsonConverter.getHashMap().put(uniqueID, event);
        iCalToJsonConverter.changesInHashmap("events.json");
    }

    /**
     * Endpoint to fetch a new iCal-file and re-write the "events.json"-file.
     * @param programID The name of the program that you want to download the iCal-file for.
     * @throws IOException
     * @throws ParserException
     */
    public void downloadIcalFile(String programID) throws IOException, ParserException, InterruptedException {
        File tempFile = File.createTempFile("downloaded", ".ics");
        FileUtils.copyURLToFile(new URL("https://schema.mau.se/setup/jsp/SchemaICAL.ics?startDatum=idag&intervallTyp=m&intervallAntal=6&sprak=SV&sokMedAND=true&forklaringar=true&resurser=p." + programID), tempFile);

        Thread.sleep(1000);

        String destinationFilePath = "ical/SchemaICAL.ics";
        FileUtils.copyFile(tempFile, new File(destinationFilePath));
        tempFile.delete();
        ICalToJsonConverter converter = new ICalToJsonConverter();
        converter.createJsonFile();
    }

    /**
     * Inner class that gives the endpoints the functionality to convert json and HTTP-forms.
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
