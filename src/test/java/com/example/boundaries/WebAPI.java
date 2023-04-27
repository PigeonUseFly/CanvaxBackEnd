package com.example.boundaries;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public interface WebAPI {

    @PostMapping(value = "/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postEvents(@RequestBody String eventsJson);

    ResponseEntity<JSONArray> getJsonFile() throws IOException, JSONException;
}

