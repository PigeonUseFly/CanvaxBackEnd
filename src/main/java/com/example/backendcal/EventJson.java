package com.example.backendcal;

import org.json.JSONObject;
import java.util.Date;

public class EventJson extends JSONObject {
    private String summary;
    private String description;
    private Date startDate;
    private Date endDate;
    private String locationName;

    public EventJson(String summary, String description, Date startDate, Date endDate, String locationName) {
        this.summary = summary;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationName = locationName;
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
