package com.example.backendcal;

public class Event {
    private int index;
    private String summary;
    private String description;
    private String startDate;
    private String endDate;
    private String locationName;

    public Event(int index, String summary, String description, String startDate, String endDate, String locationName) {
        this.index = index;
        this.summary = summary;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationName = locationName;
    }

    // Getters
    public int getIndex() {
        return index;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLocationName() {
        return locationName;
    }

    // Setters
    public void setIndex(int index) {
        this.index = index;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}