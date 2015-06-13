package com.example.siyo_pc.medme_trial.classes;

public class MM_Sickness_Term {
    private int stSicknessID;
    private int stMedTermID;

    public MM_Sickness_Term(int sicknessID, int medTermID) {
        super();
        this.stSicknessID = sicknessID;
        this.stMedTermID = medTermID;
    }

    public int getSicknessID() { return stSicknessID; }

    public void setSicknessID(int ID) { stSicknessID = ID; }

    public int getMedTermID() { return stMedTermID; }

    public void setMedTermID(int ID) { stMedTermID = ID; }
}
