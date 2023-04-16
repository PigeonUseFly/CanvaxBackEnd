package com.example.backendcal;

import java.io.IOException;
import java.io.StringReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

@Controller
public class CalendarController {

    @GetMapping("/calendar")
    public String getCalendar(Model model) throws IOException, ParserException {
        String iCalString = "BEGIN:VCALENDAR\nPRODID:-//Ben Fortuna//iCal4j 1.0//EN\nVERSION:2.0\nCALSCALE:GREGORIAN\nBEGIN:VEVENT\nDTSTART:20220327T090000Z\nDTEND:20220327T100000Z\nSUMMARY:Test Event\nUID:test-uid\nEND:VEVENT\nEND:VCALENDAR\n";
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(new StringReader(iCalString));
        VEvent event = (VEvent) calendar.getComponent("VEVENT");
        model.addAttribute("event", event);
        return "calendar";
    }
}