package com.example.siyo_pc.medme_trial.classes;

public class MM_Symptom_Term {
    private int stSymptomID;
    private int stMedTermID;

    public MM_Symptom_Term(int symptomID, int medTermID) {
        super();
        this.stSymptomID = symptomID;
        this.stMedTermID = medTermID;
    }

    public int getSymptomID() { return stSymptomID; }

    public void setSymptomID(int ID) { stSymptomID = ID; }

    public int getMedTermID() { return stMedTermID; }

    public void setMedTermID(int ID) { stMedTermID = ID; }
}
