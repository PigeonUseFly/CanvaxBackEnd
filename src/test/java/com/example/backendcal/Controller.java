package com.example.backendcal;
import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;
public class Controller {
    Scanner scan = new Scanner(System.in);
    ICalReaderService readerService;
    boolean isRunning = true;
    public Controller() throws ParserException, IOException {
        readerService = new ICalReaderService();
        readerService.writeICalEvents("ical/SchemaICAL.ics");
        readerService.printICalEvents();
    }
    public void meny(){
        while(isRunning){
            System.out.println("\nPlease input valid lesson id\n");
            System.out.println("1. Set notes on lessons\n2. Read notes from lesson\n3. Exit system\n");
            int input = scan.nextInt();
            switch(input){
                case 1:
                    System.out.println("Which lessons notes would you like to give notes to(give index)");
                    int answer = scan.nextInt();
                    System.out.println("please write note");
                    scan.nextLine();
                    String notes = scan.nextLine();
                    readerService.listOfLessons(answer).setOwnNotes(notes);
                    break;
                case 2:
                    System.out.println("Which lessons notes would you like to see(give index)");
                    input = scan.nextInt();
                    System.out.println(readerService.listOfLessons(input).getOwnNotes());
                    break;
                case 3:
                    isRunning = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("give valid number");
                    break;
            }
        }

    }
}
