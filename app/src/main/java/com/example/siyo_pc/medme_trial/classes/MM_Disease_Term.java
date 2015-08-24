package com.example.siyo_pc.medme_trial.classes;

public class MM_Disease_Term {
    private int dtDiseaseID;
    private int dtMedTermID;

    public MM_Disease_Term() {
        super();
    }

    public MM_Disease_Term(int diseaseID, int medTermID) {
        super();
        this.dtDiseaseID = diseaseID;
        this.dtMedTermID = medTermID;
    }

    public int GetDiseaseID() { return dtDiseaseID; }

    public void SetDiseaseID(int ID) { dtDiseaseID = ID; }

    public int GetMedTermID() { return dtMedTermID; }

    public void SetMedTermID(int ID) { dtMedTermID = ID; }
}
