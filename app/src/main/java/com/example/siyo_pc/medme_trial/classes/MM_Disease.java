package com.example.siyo_pc.medme_trial.classes;

public class MM_Disease {
    private int dDiseaseID;
    private String dGreekName;
    private String dDiseaseName;
    private String dDiseaseDesc;
    private boolean dMode;

    public MM_Disease () {
        super();
    }

    public MM_Disease (int diseaseID, String greekName, String diseaseName, String diseaseDesc,
                       boolean mode) {
        super();
        this.dDiseaseID = diseaseID;
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
        this.dMode = mode;
    }

    public MM_Disease (String greekName, String diseaseName, String diseaseDesc,
                       boolean mode) {
        super();
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
        this.dMode = mode;
    }

    public MM_Disease (int diseaseID, String greekName, String diseaseName, String diseaseDesc) {
        super();
        this.dDiseaseID = diseaseID;
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
    }

    public MM_Disease (String greekName, String diseaseName, String diseaseDesc) {
        super();
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
    }

    public MM_Disease (String diseaseName) {
        super();
        this.dDiseaseName = diseaseName;
    }

    public MM_Disease (int diseaseID) {
        super();
        this.dDiseaseID = diseaseID;
    }

    public int GetDiseaseID() { return dDiseaseID; }

    public void SetDiseaseID(int diseaseID) { dDiseaseID = diseaseID; }

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
