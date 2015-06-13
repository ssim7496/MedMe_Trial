package com.example.siyo_pc.medme_trial.classes;

public class MM_Disease {
    private int dDiseaseID;
    private int dSicknessID;
    private int dSymptomID;
    private String dGreekName;
    private String dDiseaseName;
    private String dDiseaseDesc;
    private boolean dMode;

    public MM_Disease (int diseaseID, int sicknessID, int symptomID, String greekName, String diseaseName, String diseaseDesc,
                       boolean mode) {
        super();
        this.dDiseaseID = diseaseID;
        this.dSicknessID = sicknessID;
        this.dSymptomID = symptomID;
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
        this.dMode = mode;
    }

    public int GetDiseaseID() { return dDiseaseID; }

    public void SetDiseaseID(int diseaseID) { dDiseaseID = diseaseID; }

    public int GetSicknessID() { return dSicknessID; }

    public void SetSicknessID(int sicknessID ) { dSicknessID = sicknessID; }

    public int GetSymptomID() { return dSymptomID; }

    public void SetSymptomID(int symptomID) { dSymptomID = symptomID; }

    public String GetGreekName() {
        return dGreekName;
    }

    public void SetGreekName(String greekName) {
        dGreekName = greekName;
    }

    public String GetDiseaseName() {
        return dDiseaseName;
    }

    public void SetDiseaseName(String diseaseName) {
        dDiseaseName = diseaseName;
    }

    public String GetDiseaseDesc() {
        return dDiseaseDesc;
    }

    public void SetDiseaseDesc(String diseaseDesc) {
        dDiseaseDesc = diseaseDesc;
    }

    public boolean GetMode() {
        return dMode;
    }

    public void SetMode(boolean mode) {
        dMode = mode;
    }
}
