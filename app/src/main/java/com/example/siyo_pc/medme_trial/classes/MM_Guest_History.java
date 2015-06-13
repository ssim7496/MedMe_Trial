package com.example.siyo_pc.medme_trial.classes;

import java.util.Date;

public class MM_Guest_History {
    private int ghLogID;
    private int ghPersonID;
    private String ghDescription;
    private Date ghDate;

    public MM_Guest_History(int logID, int personID, String descr, Date date){
        super();
        this.ghLogID = logID;
        this.ghPersonID = personID;
        this.ghDescription = descr;
        this.ghDate = date;
    }

    public int GetLogID() { return ghLogID; }

    public void SetLogID(int logID) { ghLogID = logID; }

    public int GetPersonID() {
        return ghPersonID;
    }

    public void SetPersonID(int personID) {
        ghPersonID = personID;
    }

    public String GetLogDescription() {
        return ghDescription;
    }

    public void SetLogDescription(String descr) {
        ghDescription = descr;
    }

    public Date GetDate() {
        return ghDate;
    }

    public void SetDate(Date date) {
        ghDate = date;
    }
}
