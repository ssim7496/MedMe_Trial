package com.example.siyo_pc.medme_trial.classes;

import java.util.Date;

public class MM_Sickness_Change_Log {
    private int icInfoChangeID;
    private int icPersonChangerID;
    private int icSicknessID;
    private String icInformationOriginal;
    private String icInformationNew;
    private Date icDateChnaged;

    public MM_Sickness_Change_Log() {
        super();
    }

    public MM_Sickness_Change_Log(int infoID, int personID, int sicknessID, String infoOriginal, String infoNew, Date date){
        super();
        this.icInfoChangeID = infoID;
        this.icPersonChangerID = personID;
        this.icSicknessID = sicknessID;
        this.icInformationOriginal = infoOriginal;
        this.icInformationNew = infoNew;
        this.icDateChnaged = date;
    }

    public int GetInfoChangeID() { return icInfoChangeID; }

    public void SetInfoChangeID(int logID) { icInfoChangeID = logID; }

    public int GetPersonChangerID() { return icPersonChangerID; }

    public void SetPersonChangerID(int personID) { icPersonChangerID = personID; }

    public int GetSicknessID() { return icSicknessID; }

    public void SetSicknessID(int sicknessID) { icSicknessID = sicknessID; }

    public String GetInformationOriginal() { return icInformationOriginal; }

    public void SetInformationOriginal(String descr) { icInformationOriginal = descr; }

    public String GetInformationNew() { return icInformationNew; }

    public void SetInformationNew(String descr) { icInformationNew = descr; }

    public Date GetDateChnaged() { return icDateChnaged; }

    public void SetDateChnaged(Date date) { icDateChnaged = date; }
}
