package com.example.backendcal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendCalApplicationTests {

    public BackendCalApplicationTests(){
        ICalReaderService ical = new ICalReaderService();
    }

    @Test
    void contextLoads() {

    }

}
