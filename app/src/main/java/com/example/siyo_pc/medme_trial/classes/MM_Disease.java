package com.example.siyo_pc.medme_trial.classes;

public class MM_Disease {
    private int dDiseaseID;
    private int dSicknessID;
    private int dSymptomID;
    private String dGreekName;
    private String dDiseaseName;
    private String dDiseaseDesc;
    private boolean dDiseaseMode;

    public MM_Disease (int diseaseID, int sicknessID, int symptomID, String greekName, String diseaseName, String diseaseDesc,
                       boolean diseaseMode) {
        super();
        this.dDiseaseID = diseaseID;
        this.dSicknessID = sicknessID;
        this.dSymptomID = symptomID;
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
        this.dDiseaseMode = diseaseMode;
    }

    public int getDiseaseID() { return dDiseaseID; }

    public void setDiseaseID(int diseaseID) { dDiseaseID = diseaseID; }

    public int getSicknessID() { return dSicknessID; }

    public void setSicknessID(int sicknessID ) { dSicknessID = sicknessID; }

    public int getSymptomID() { return dSymptomID; }

    public void setSymptomID(int symptomID) { dSymptomID = symptomID; }

    public String getGreekName() {
        return dGreekName;
    }

    public void setGreekName(String greekName) {
        dGreekName = greekName;
    }

    public String getDiseaseName() {
        return dDiseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        dDiseaseName = diseaseName;
    }

    public String getDiseaseDesc() {
        return dDiseaseDesc;
    }

    public void setDiseaseDesc(String diseaseDesc) {
        dDiseaseDesc = diseaseDesc;
    }

    public boolean getDiseaseMode() {
        return dDiseaseMode;
    }

    public void setDiseaseMode(boolean diseaseMode) {
        dDiseaseMode = diseaseMode;
    }
}
