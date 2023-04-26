package com.example.boundaries;

import net.fortuna.ical4j.data.ParserException;
import net.minidev.json.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.List;

@Validated
public interface WebAPI {

   /* @GetMapping(value = "/programs/{id}")
    public ResponseEntity<String> getProgram(@PathVariable("id")  String id);
    */

    @ResponseBody
    @GetMapping(path = "/calendar/{ngt}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JSONArray> getJsonFile() throws IOException;


    @ResponseBody
    @GetMapping(value = "/programs/{id}")
    public ResponseEntity<Object> getProgram(@PathVariable("id")  String id) throws IOException, JSONException, ParseException, ParserException;
}

