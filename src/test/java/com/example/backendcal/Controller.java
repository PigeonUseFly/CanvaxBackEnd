package com.example.backendcal;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class Controller {
    private JsonRepository jsonRepository;
    public Controller() {
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public List<EventJson> getItem() {
        return jsonRepository.findAll();
    }

    public EventJson createItem(@RequestBody EventJson eventJson) {
        return jsonRepository.save(eventJson);
    }

    public static void main(String[] args) throws IOException, ParserException {
        String icalString = "BEGIN:VCALENDAR\nPRODID:-//Ben Fortuna//iCal4j 1.0//EN\nVERSION:2.0\nCALSCALE:GREGORIAN\nBEGIN:VEVENT\nDTSTART:20220327T090000Z\nDTEND:20220327T100000Z\nSUMMARY:Test Event\nUID:test-uid\nEND:VEVENT\nEND:VCALENDAR\n";
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(new StringReader(icalString));
        Component component = calendar.getComponent("VEVENT");
        VEvent event = (VEvent)component;
        System.out.println("Event Summary: " + event.getSummary().getValue());
    }

    public void removeEvent() {
        //Gottemannens får jobba här (endast här!!)
    }
}