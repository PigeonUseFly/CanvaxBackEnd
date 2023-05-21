package com.example.backendcal.boundaries;

import net.fortuna.ical4j.data.ParserException;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * Interface for API. Check implementation in Controller for information about the different methods.
 */
@Validated
public interface WebAPI {

    @ResponseBody
    @GetMapping(value = "/events")
    ResponseEntity<Object> getJsonFile() throws IOException, JSONException;

    @DeleteMapping(value ="/events/{id}")
    void removeEvent(@PathVariable String id) throws IOException, ParserException;

    @PostMapping("/events/insert")
    void insertEvent(@RequestParam String summary, @RequestParam String description, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String location) throws IOException, ParseException, JSONException;

    @GetMapping("/events/download-ical/{programID}")
    void downloadIcalFile(@PathVariable String link) throws IOException, ParserException;
}