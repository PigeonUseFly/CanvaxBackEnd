package com.example.backendcal;

import net.fortuna.ical4j.data.ParserException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class BackendCalApplicationTests {

    public BackendCalApplicationTests() throws ParserException, IOException {
        ICalReaderService ical = new ICalReaderService();
    }

    @Test
    void contextLoads() {

    }

}
