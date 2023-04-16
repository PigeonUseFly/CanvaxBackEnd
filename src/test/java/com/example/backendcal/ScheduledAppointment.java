package com.example.backendcal;

public class ScheduledAppointment {
    private static int count = -1;
    private int myIndex;
    private String name;
    private String location;
    private String startTime;
    private String endTime;
    private String ownNotes;
    public ScheduledAppointment(){

    }

    public ScheduledAppointment(String name, String location,String startTime,String endTime,String ownNotes){
        count++;
        this.myIndex = count;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ownNotes = ownNotes;
    }

    public String toString(){
        return String.format("Index: %d\nKursnamn: %s\nPlats: %s\nStarttid: %s\nSluttid: %s\n\n",myIndex, name,location,startTime,endTime);
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
    public int getIndex(){
        return myIndex;
    }
    public void setOwnNotes(String ownNotes){
        this.ownNotes = ownNotes;
    }
    public String getOwnNotes(){
        return ownNotes;
    }
}
