package com.example.backendcal;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class EventList {
    private List<Event> events;
    @JsonCreator
    public EventList(List<Event> events ) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}