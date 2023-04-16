package com.example.backendcal;

public class ScheduledAppointment {
    private String name;
    private String location;
    private String startTime;
    private String endTime;

    public ScheduledAppointment(String name, String location,String startTime,String endTime){
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String toString(){
        return String.format("Kursnamn: %s\nPlats: %s\nStarttid: %s\nSluttid: %s\n\n",name,location,startTime,endTime);
    }
    public String getName(){
        return name;
    }
    public String getLocation(){
        return location;
    }
    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
}
