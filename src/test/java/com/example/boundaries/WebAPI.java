package com.example.boundaries;

import net.fortuna.ical4j.data.ParserException;
import net.minidev.json.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Validated
public interface WebAPI {

   /* @GetMapping(value = "/programs/{id}")
    public ResponseEntity<String> getProgram(@PathVariable("id")  String id);
    */

    @ResponseBody
    @GetMapping(value = "/calendar/{ngt}")
    ResponseEntity<Object> getJsonFile() throws IOException, JSONException, ParserException;

    @ResponseBody
    @GetMapping(value = "/programs/{id}")
    public ResponseEntity<Object> getProgram(@PathVariable("id")  String id) throws IOException, JSONException, ParseException, ParserException;
}

