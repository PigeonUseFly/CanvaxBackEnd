package com.example.backendcal;

import java.util.Date;

public class EventJson {
    private String summary;
    private Date startDate;
    private Date endDate;
    private String location;

    public EventJson(String summary, Date startDate, Date endDate, String location) {
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void SetEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
