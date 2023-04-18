package com.example.backendcal;

import java.io.*;
import java.util.List;

import com.example.boundaries.WebAPI;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.web.JsonPath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

//@RestController
//@RequestMapping(path="5500", produces="CanvaxBackEnd/events.json")
@ServletComponentScan
@SpringBootApplication
public class Controller {
    private String filePath = "events.json";
    private File file;
    public Controller() {
    }

    public Object getItem() {
        return file = new File(filePath);
    }

    /*public EventJson createItem(@RequestBody EventJson eventJson) {
        return jsonRepository.save(eventJson);
    } */

    public static void main(String[] args) throws IOException, ParserException {
        String icalString = "BEGIN:VCALENDAR\nPRODID:-//Ben Fortuna//iCal4j 1.0//EN\nVERSION:2.0\nCALSCALE:GREGORIAN\nBEGIN:VEVENT\nDTSTART:20220327T090000Z\nDTEND:20220327T100000Z\nSUMMARY:Test Event\nUID:test-uid\nEND:VEVENT\nEND:VCALENDAR\n";
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(new StringReader(icalString));
        Component component = calendar.getComponent("VEVENT");
        VEvent event = (VEvent)component;
        SpringApplication.run(Controller.class, args);
        System.out.println("Event Summary: " + event.getSummary().getValue());
    }

    public void removeEvent() {
        //Gottemannen får jobba här (endast här!!)
    }
}