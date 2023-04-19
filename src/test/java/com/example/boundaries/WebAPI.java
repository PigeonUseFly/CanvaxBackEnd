package com.example.boundaries;

import net.fortuna.ical4j.data.ParserException;
import net.minidev.json.parser.ParseException;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;

@Validated
public interface WebAPI {

   /* @GetMapping(value = "/programs/{id}")
    public ResponseEntity<String> getProgram(@PathVariable("id")  String id);
    */

    @ResponseBody
    @GetMapping(value = "/programs/{id}")
    public ResponseEntity<Object> getProgram(@PathVariable("id")  String id) throws IOException, JSONException, ParseException, ParserException;
}
