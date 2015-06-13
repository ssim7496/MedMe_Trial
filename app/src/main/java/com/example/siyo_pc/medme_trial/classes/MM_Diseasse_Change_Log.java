package com.example.siyo_pc.medme_trial.classes;

import java.util.Date;

public class MM_Diseasse_Change_Log {
    private int icInfoChangeID;
    private int icPersonChangerID;
    private int icDiseaseID;
    private String icInformationOriginal;
    private String icInformationNew;
    private Date icDateChnaged;

    public MM_Diseasse_Change_Log(int infoID, int personID, int diseaseID, String infoOriginal, String infoNew, Date date){
        super();
        this.icInfoChangeID = infoID;
        this.icPersonChangerID = personID;
        this.icDiseaseID = diseaseID;
        this.icInformationOriginal = infoOriginal;
        this.icInformationNew = infoNew;
        this.icDateChnaged = date;
    }

    public int GetInfoChangeID() { return icInfoChangeID; }

    public void SetInfoChangeID(int logID) { icInfoChangeID = logID; }

    public int GetPersonChangerID() { return icPersonChangerID; }

    public void SetPersonChangerID(int personID) { icPersonChangerID = personID; }

    public int GetDiseaseID() { return icDiseaseID; }

    public void SetDiseaseID(int diseaseID) { icDiseaseID = diseaseID; }

    public String GetInformationOriginal() { return icInformationOriginal; }

    public void SetInformationOriginal(String descr) { icInformationOriginal = descr; }

    public String GetInformationNew() { return icInformationNew; }

    public void SetInformationNew(String descr) { icInformationNew = descr; }

    public Date GetDateChnaged() { return icDateChnaged; }

    public void SetDateChnaged(Date date) { icDateChnaged = date; }
}
