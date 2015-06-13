package com.example.siyo_pc.medme_trial.classes;

public class MM_Disease_Term {
    private int dtDiseaseID;
    private int dtMedTermID;

    public MM_Disease_Term(int diseaseID, int medTermID) {
        super();
        this.dtDiseaseID = diseaseID;
        this.dtMedTermID = medTermID;
    }

    public int getDiseaseID() { return dtDiseaseID; }

    public void setDiseaseID(int ID) { dtDiseaseID = ID; }

    public int getMedTermID() { return dtMedTermID; }

    public void setMedTermID(int ID) { dtMedTermID = ID; }
}
