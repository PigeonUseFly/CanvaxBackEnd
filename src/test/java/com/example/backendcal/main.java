package com.example.backendcal;

import java.io.IOException;
import java.text.ParseException;
import net.fortuna.ical4j.data.ParserException;


public class main {

    public static void main(String[] args) throws IOException, ParserException, ParseException {
        Controller controller = new Controller();
        controller.meny();
    }
}