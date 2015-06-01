package com.example.siyo_pc.medme_trial.classes;

public class MM_Sickness {

    public int sID;
    public String sGreekName;
    public String sSicknessName;
    public String sSicknessDesc;

    public MM_Sickness (int ID, String greekName, String sicknessName, String sicknessDesc) {
        super();
        this.sID = ID;
        this.sGreekName = greekName;
        this.sSicknessName = sicknessName;
        this.sSicknessDesc = sicknessDesc;
    }

    public MM_Sickness (String sicknessName) {
        super();
        this.sSicknessName = sicknessName;
    }

    public int getID() {
        return sID;
    }

    public void setID(int ID) {
        sID = ID;
    }

    public String getGreekName() {
        return sGreekName;
    }

    public void setGreekName(String greekName) {
        sGreekName = greekName;
    }

    public String getSicknessName() {
        return sSicknessName;
    }

    public void setSicknessName(String sicknessName) {
        sSicknessName = sicknessName;
    }

    public String getSicknessDesc() {
        return sSicknessDesc;
    }

    public void setSicknessDesc(String sicknessDesc) {
        sSicknessDesc = sicknessDesc;
    }
}