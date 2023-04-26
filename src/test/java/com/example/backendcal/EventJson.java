package com.example.backendcal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.json.JSONObject;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@JsonIgnoreProperties
public class EventJson extends JSONObject {
    private String summary;
    private String description;
    private Date startDate;
    private Date endDate;
    private String locationName;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public EventJson(String summary, String description, Date startDate, Date endDate, String locationName) {
        this.summary = summary;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationName = locationName;
    }

    public EventJson() {

    }

    public String toString() {
        return "Summary: " + this.summary + " Description: " + this.description + " Start date: " + this.startDate + " End date: " + this.endDate + " Location: " + this.locationName + "\n";
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

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
