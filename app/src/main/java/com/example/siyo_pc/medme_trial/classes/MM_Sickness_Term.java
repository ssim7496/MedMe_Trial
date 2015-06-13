package com.example.siyo_pc.medme_trial.classes;

public class MM_Sickness_Term {
    private int stSicknessID;
    private int stMedTermID;

    public MM_Sickness_Term(int sicknessID, int medTermID) {
        super();
        this.stSicknessID = sicknessID;
        this.stMedTermID = medTermID;
    }

    public int GetSicknessID() { return stSicknessID; }

    public void SetSicknessID(int ID) { stSicknessID = ID; }

    public int GetMedTermID() { return stMedTermID; }

    public void SetMedTermID(int ID) { stMedTermID = ID; }
}
