package com.example.backendcal;

import com.example.boundaries.WebAPI;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalController implements WebAPI {
    public ResponseEntity<String> getProgram(String id) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/plain"))
                .body("Käften " + id);
    }
}
