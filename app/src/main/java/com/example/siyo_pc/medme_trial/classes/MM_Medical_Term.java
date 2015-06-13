package com.example.siyo_pc.medme_trial.classes;

public class MM_Medical_Term {
    private int mtMedTermID;
    private String mtGreekName;
    private String mtMedTermName;
    private String mtMedTermDesc;
    private String mtMedTermType;
    private boolean mtMedTermMode;

    public MM_Medical_Term (int ID, String greekName, String medTermName, String medTermDesc, String medTermType, boolean medTermMode) {
        super();
        this.mtMedTermID = ID;
        this.mtGreekName = greekName;
        this.mtMedTermName = medTermName;
        this.mtMedTermDesc = medTermDesc;
        this.mtMedTermType = medTermType;
        this.mtMedTermMode = medTermMode;
    }

    public int GetID() { return mtMedTermID; }

    public void SetID(int ID) { mtMedTermID = ID; }

    public String GetGreekName() {
        return mtGreekName;
    }

    public void SetGreekName(String greekName) {
        mtGreekName = greekName;
    }

    public String GetMedicalTermName() {
        return mtMedTermName;
    }

    public void SetMedicalTermName(String medTermName) { mtMedTermName = medTermName; }

    public String GetMedicalTermDesc() {
        return mtMedTermDesc;
    }

    public void SetMedicalTermDesc(String medTermDesc) {
        mtMedTermDesc = medTermDesc;
    }

    public String GetMedicalTermType() {
        return mtMedTermType;
    }

    public void SetMedicalTermType(String medTermType) {
        mtMedTermType = medTermType;
    }

    public boolean GetMedicalTermMode() {
        return mtMedTermMode;
    }

    public void SetMedicalTermMode(boolean medTermMode) {
        mtMedTermMode = medTermMode;
    }
}
