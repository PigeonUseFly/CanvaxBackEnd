package Schema;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.*;
@Controller
public class CalendarController {

        @RequestMapping("/calendar") //ToDO googla requestmapping
    public String calendar(Model model) throws IOException, ParserException {
        String icalString = "BEGIN:VCALENDAR\n" +
                "PRODID:-//Ben Fortuna//iCal4j 1.0//EN\n" +
                "VERSION:2.0\n" +
                "CALSCALE:GREGORIAN\n" +
                "BEGIN:VEVENT\n" +
                "DTSTART:20220327T090000Z\n" +
                "DTEND:20220327T100000Z\n" +
                "SUMMARY:Test Event\n" +
                "UID:test-uid\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR\n";

        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(new StringReader(icalString));
        Component component = calendar.getComponent(Component.VEVENT);
        VEvent event = (VEvent) component;

        model.addAttribute("event", event);
        return "calendar";
    }
}