package com.example.backendcal;

import java.io.*;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EventController {

    private final List<Event> events;

    public EventController() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        events = objectMapper.readValue(new File("events.json"), new TypeReference<List<Event>>(){});
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents() {
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}