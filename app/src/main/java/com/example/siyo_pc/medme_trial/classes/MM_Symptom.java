package com.example.siyo_pc.medme_trial.classes;

public class MM_Symptom {

    private int sID;
    private String sGreekName;
    private String sSymptomName;
    private String sSymptomDesc;
    private boolean sSymptomMode;

    public MM_Symptom (int ID, String greekName, String symptomName, String symptomDesc, boolean symptomMode) {
        super();
        this.sID = ID;
        this.sGreekName = greekName;
        this.sSymptomName = symptomName;
        this.sSymptomDesc = symptomDesc;
        this.sSymptomMode = symptomMode;
    }

    public int getID() { return sID; }

    public void setID(int ID) { sID = ID; }

    public String getGreekName() {
        return sGreekName;
    }

    public void setGreekName(String greekName) {
        sGreekName = greekName;
    }

    public String getSymptomName() {
        return sSymptomName;
    }

    public void setSymptomName(String symptomName) { sSymptomName = symptomName; }

    public String getSymptomDesc() {
        return sSymptomDesc;
    }

    public void setSymptomDesc(String symptomDesc) {
        sSymptomDesc = symptomDesc;
    }

    public boolean getSymptomMode() {
        return sSymptomMode;
    }

    public void setSymptomMode(boolean symptomMode) {
        sSymptomMode = symptomMode;
    }
}