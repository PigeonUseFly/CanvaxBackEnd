package com.example.backendcal;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonArrayChecker {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("C:\\Users\\Ermin\\projektarbete1\\CanvaxBackEnd\\events.json");

        try {
            // Attempt to parse the file as an array of objects
            Object[] objects = mapper.readValue(file, Object[].class);

            // If the parsing succeeds, print a success message
            System.out.println("File contains a valid JSON array");

        } catch (JsonParseException e) {
            // If the file cannot be parsed as JSON, print an error message
            System.out.println("File is not valid JSON");

        } catch (IOException e) {
            // If there is an error reading the file, print an error message
            System.out.println("Error reading file");
        }
    }
}

