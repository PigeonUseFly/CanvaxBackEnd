package com.example.backendcal;

import com.example.boundaries.WebAPI;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.fortuna.ical4j.data.ParserException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

@RestController
public class CalController implements WebAPI {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private ICalToJsonConverter iCalToJsonConverter;

    public CalController() throws ParserException, IOException {
        iCalToJsonConverter = new ICalToJsonConverter();
    }

    @Override
    public ResponseEntity<Object> getJsonFile() throws IOException {
        File file = new File("events.json");
        String jsonString = new String(Files.readAllBytes(file.toPath()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(jsonString, headers, HttpStatus.OK);
    }
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            converters.add(new GsonHttpMessageConverter());
        }
    }

    public ResponseEntity<?> removeEvent(int inputFromFrontend) throws IOException { //TODO väldigt incomplete
        iCalToJsonConverter.getEventArrayNode().remove(inputFromFrontend);
        iCalToJsonConverter.getObjectMapper().writeValue(new File("events.json"), iCalToJsonConverter.getParentObjectNode());
        return ResponseEntity.ok().build();
    }
}
