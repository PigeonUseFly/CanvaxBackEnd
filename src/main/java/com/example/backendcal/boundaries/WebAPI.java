package com.example.backendcal.boundaries;

import com.example.backendcal.EventJson;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@Validated
public interface WebAPI {

    @ResponseBody
    @GetMapping(value = "/calendar/{ngt}")
    ResponseEntity<Object> getJsonFile() throws IOException, JSONException;

    @PostMapping("/calendar/remove-element")
    void removeEvent(@RequestParam int index) throws IOException;

    @PostMapping("/calendar/insert-element")
    void insertEvent(@RequestParam String summary, @RequestParam String description, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String location) throws IOException, ParseException;
}