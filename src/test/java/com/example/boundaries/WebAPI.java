package com.example.boundaries;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@Validated
public interface WebAPI {

    @ResponseBody
    @GetMapping(value = "/calendar/{ngt}")
    ResponseEntity<Object> getJsonFile() throws IOException, JSONException;

    @PutMapping("/files/{fileId}")
    ResponseEntity<?> removeEvent(@RequestParam int inputFromFrontend) throws IOException;
}