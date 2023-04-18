package com.example.boundaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Validated
public interface WebAPI {

    @GetMapping(value = "/programs/{id}")
    public ResponseEntity<String> getProgram(@PathVariable("id")  String id);
}
