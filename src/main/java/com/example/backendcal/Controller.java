package com.example.backendcal;

import com.example.backendcal.boundaries.WebAPI;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.fortuna.ical4j.data.ParserException;
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

@RestController
@CrossOrigin(origins = "*")
public class Controller implements WebAPI {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private ICalToJsonConverter iCalToJsonConverter;

    public Controller() throws ParserException, IOException {
        iCalToJsonConverter = new ICalToJsonConverter();
    }

    @Override
    public ResponseEntity<Object> getJsonFile() throws IOException {
        File file = new File("events.json");
        String jsonString = new String(Files.readAllBytes(file.toPath()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(jsonString, headers, HttpStatus.OK);
    }

    public void removeEvent(int index) throws IOException {
        iCalToJsonConverter.getEventArrayNode().remove(index);
        iCalToJsonConverter.getObjectMapper().writeValue(new File("events.json"), iCalToJsonConverter.getParentObjectNode());
    }

    public void insertEvent(String summary, String description, String startDateString, String endDateString, String location) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = formatter.parse(startDateString);
        Date endDate = formatter.parse(endDateString);
        EventJson eventJson = new EventJson(summary, description, startDate, endDate, location);
        String eventData = mapper.writeValueAsString(eventJson);

        FileWriter fileWriter = new FileWriter("events.json", true);
        fileWriter.write(eventData);
        fileWriter.close();
    }

    @Configuration
   // @ComponentScan(basePackages = {"com.example.backendcal"})
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
