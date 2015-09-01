package com.example.siyo_pc.medme_trial.classes;

public class MM_Sickness_Description {
    private int sSicknessAuto;
    private int sSicknessID;
    private int sDiseaseID;
    private int sSymptomID;
    private int sMode;

    public MM_Sickness_Description(){
        super();
    }

    public MM_Sickness_Description (int sSicknessAuto, int sSicknessID, int sDiseaseID, int sSymptomID, int sMode) {
        super();
        this.sSicknessAuto = sSicknessAuto;
        this.sSicknessID = sSicknessID;
        this.sDiseaseID = sDiseaseID;
        this.sSymptomID = sSymptomID;
        this.sMode = sMode;
    }

    public MM_Sickness_Description (int sSicknessAuto, int sSicknessID, int sDiseaseID, int sSymptomID) {
        super();
        this.sSicknessAuto = sSicknessAuto;
        this.sSicknessID = sSicknessID;
        this.sDiseaseID = sDiseaseID;
        this.sSymptomID = sSymptomID;
    }

    public MM_Sickness_Description (int sSicknessID, int sDiseaseID, int sSymptomID) {
        super();
        this.sSicknessID = sSicknessID;
        this.sDiseaseID = sDiseaseID;
        this.sSymptomID = sSymptomID;
    }

    public int GetSicknessAuto() { return sSicknessAuto; }

    public void SetSicknessAuto(int sicknessAuto ) { sSicknessAuto = sicknessAuto; }

    public int GetSicknessID() { return sSicknessID; }

    public void SetSicknessID(int sicknessID ) { sSicknessID = sicknessID; }

    public int GetDiseaseID() { return sDiseaseID; }

    public void SetDiseaseID(int diseaseID ) { sDiseaseID = diseaseID; }

    public int GetSymptomID() { return sSymptomID; }

    public void SetSymptomID(int symptomID ) { sSymptomID = symptomID; }

    public int GetMode() {
        return sMode;
    }

    public void SetMode(int mode) {
        sMode = mode;
    }
}
