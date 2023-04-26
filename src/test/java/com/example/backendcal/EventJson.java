package com.example.backendcal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.json.JSONObject;
import java.util.Date;

@Entity
public class EventJson extends JSONObject {
    private String summary;
    private String moment;
    private Date startDate;
    private Date endDate;
    private String location;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public EventJson(String summary, String moment, Date startDate, Date endDate, String location) {
        this.summary = summary;
        this.moment = moment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public EventJson() {

    }

    public String toString() {
        return "Summary: " + this.summary + " Start date: " + this.startDate + " End date: " + this.endDate + " Location: " + this.location + "\n";
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getMoment() {
        return moment;
    }
}
