package com.example.siyo_pc.medme_trial.classes;

import java.util.Date;

public class MM_Personnel_Log {
    private int plLogID;
    private int plPersonID;
    private String plDescription;
    private Date plDate;

    public MM_Personnel_Log() {
        super();
    }

    public MM_Personnel_Log(int logID, int personID, String descr, Date date){
        super();
        this.plLogID = logID;
        this.plPersonID = personID;
        this.plDescription = descr;
        this.plDate = date;
    }

    public int GetLogID() { return plLogID; }

    public void SetLogID(int logID) { plLogID = logID; }

    public int GetPersonID() { return plPersonID; }

    public void SetPersonID(int personID) { plPersonID = personID; }

    public String GetLogDescription() { return plDescription; }

    public void SetLogDescription(String descr) { plDescription = descr; }

    public Date GetDate() { return plDate; }

    public void SetDate(Date date) { plDate = date; }
}
