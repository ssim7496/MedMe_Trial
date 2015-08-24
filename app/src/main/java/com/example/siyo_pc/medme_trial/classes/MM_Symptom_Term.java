package com.example.siyo_pc.medme_trial.classes;

public class MM_Symptom_Term {
    private int stSymptomID;
    private int stMedTermID;

    public MM_Symptom_Term() {
        super();
    }

    public MM_Symptom_Term(int symptomID, int medTermID) {
        super();
        this.stSymptomID = symptomID;
        this.stMedTermID = medTermID;
    }

    public int GetSymptomID() { return stSymptomID; }

    public void SetSymptomID(int ID) { stSymptomID = ID; }

    public int GetMedTermID() { return stMedTermID; }

    public void SetMedTermID(int ID) { stMedTermID = ID; }
}
