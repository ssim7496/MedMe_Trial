package com.example.siyo_pc.medme_trial.classes;

public class MM_Medical_Term {
    private int mtID;
    private String mtGreekName;
    private String mtMedTermName;
    private String mtMedTermDesc;
    private String mtMedTermType;
    private boolean mtMedTermMode;

    public MM_Medical_Term (int ID, String greekName, String medTermName, String medTermDesc, String medTermType, boolean medTermMode) {
        super();
        this.mtID = ID;
        this.mtGreekName = greekName;
        this.mtMedTermName = medTermName;
        this.mtMedTermDesc = medTermDesc;
        this.mtMedTermType = medTermType;
        this.mtMedTermMode = medTermMode;
    }

    public int getID() { return mtID; }

    public void setID(int ID) { mtID = ID; }

    public String getGreekName() {
        return mtGreekName;
    }

    public void setGreekName(String greekName) {
        mtGreekName = greekName;
    }

    public String getMedicalTermName() {
        return mtMedTermName;
    }

    public void setMedicalTermName(String medTermName) { mtMedTermName = medTermName; }

    public String getMedicalTermDesc() {
        return mtMedTermDesc;
    }

    public void setMedicalTermDesc(String medTermDesc) {
        mtMedTermDesc = medTermDesc;
    }

    public String getMedicalTermType() {
        return mtMedTermType;
    }

    public void setMedicalTermType(String medTermType) {
        mtMedTermType = medTermType;
    }

    public boolean getMedicalTermMode() {
        return mtMedTermMode;
    }

    public void setMedicalTermMode(boolean medTermMode) {
        mtMedTermMode = medTermMode;
    }
}
