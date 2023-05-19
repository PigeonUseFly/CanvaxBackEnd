package com.example.backendcal;

import java.util.Date;

/**
 * Class used to create objects out of the data of each event.
 */
public class Event {
    private String summary;
    private String description;
    private Date startDate;
    private Date endDate;
    private String locationName;

    /**
     * Constructor for the class.
     * @param summary Short summary of the event.
     * @param description Detailed description of the event.
     * @param startDate Date/time for when the event starts.
     * @param endDate Date/time for when the event stops.
     * @param locationName Location for the event.
     */
    public Event(String summary, String description, Date startDate, Date endDate, String locationName) {
        this.summary = summary;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationName = locationName;
    }

    public String getSummary() {
        return this.summary;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public String getDescription() {
        return description;
    }

}
