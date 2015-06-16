package com.example.siyo_pc.medme_trial.classes;

public class MM_Sickness {

    private int sSicknessID;
    private int sDiseaseID;
    private int sSymptomID;
    private String sGreekName;
    private String sSicknessName;
    private String sSicknessDesc;
    private boolean sMode;

    public MM_Sickness(){
        super();
    }

    public MM_Sickness (int ID, int diseaseID, int symptomID, String greekName, String sicknessName, String sicknessDesc, boolean mode) {
        super();
        this.sSicknessID = ID;
        this.sDiseaseID = diseaseID;
        this.sSymptomID = symptomID;
        this.sGreekName = greekName;
        this.sSicknessName = sicknessName;
        this.sSicknessDesc = sicknessDesc;
        this.sMode = mode;
    }

    public MM_Sickness (int ID, String greekName, String sicknessName, String sicknessDesc) {
        super();
        this.sSicknessID = ID;
        this.sGreekName = greekName;
        this.sSicknessName = sicknessName;
        this.sSicknessDesc = sicknessDesc;
    }

    public MM_Sickness (String greekName, String sicknessName, String sicknessDesc) {
        super();
        this.sGreekName = greekName;
        this.sSicknessName = sicknessName;
        this.sSicknessDesc = sicknessDesc;
    }

    public MM_Sickness (String sicknessName) {
        super();
        this.sSicknessName = sicknessName;
    }

    public int GetSicknessID() { return sSicknessID; }

    public void SetSicknessID(int sicknessID ) { sSicknessID = sicknessID; }

    public int GetDiseaseID() { return sDiseaseID; }

    public void SetDiseaseID(int diseaseID) { sDiseaseID = diseaseID; }

    public int GetSymptomID() { return sSymptomID; }

    public void SetSymptomID(int symptomID) { sSymptomID = symptomID; }

    public String GetGreekName() {
        return sGreekName;
    }

    public void SetGreekName(String greekName) {
        sGreekName = greekName;
    }

    public String GetSicknessName() {
        return sSicknessName;
    }

    public void SetSicknessName(String sicknessName) {
        sSicknessName = sicknessName;
    }

    public String GetSicknessDesc() {
        return sSicknessDesc;
    }

    public void SetSicknessDesc(String sicknessDesc) {
        sSicknessDesc = sicknessDesc;
    }

    public boolean GetMode() {
        return sMode;
    }

    public void SetMode(boolean mode) {
        sMode = mode;
    }
}