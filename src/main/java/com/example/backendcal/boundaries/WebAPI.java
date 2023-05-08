package com.example.backendcal.boundaries;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * Gränssnitt för API. Kolla implementation i Controller för information om de olika metoderna.
 */
@Validated
public interface WebAPI {

    @ResponseBody
    @GetMapping(value = "/events")
    ResponseEntity<Object> getJsonFile() throws IOException, JSONException;

    @DeleteMapping("/events/{id}")
    void removeEvent(@PathVariable String id) throws IOException;

    @PostMapping("/events")
    void insertEvent(@RequestParam String summary, @RequestParam String description, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String location) throws IOException, ParseException, JSONException;
}