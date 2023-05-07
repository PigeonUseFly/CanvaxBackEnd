package com.example.backendcal.boundaries;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Validated
public interface WebAPI {

    @ResponseBody
    @GetMapping(value = "/calendar/{ngt}")
    ResponseEntity<Object> getJsonFile() throws IOException, JSONException;

    @PutMapping("/files/{fileId}")
    ResponseEntity<?> removeEvent(@RequestParam int inputFromFrontend) throws IOException;

    @RequestMapping(value = "/removeEvent", method = RequestMethod.POST)
    public ResponseEntity<?> removeEvent(@RequestBody Map<String, Integer> payload) throws IOException;

}