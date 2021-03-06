package com.example.siyo_pc.medme_trial.classes;

public class MM_Symptom {

    private int sSymptomID;
    private String sGreekName;
    private String sSymptomName;
    private String sSymptomDesc;
    private int sMode;

    public MM_Symptom () {
        super();
    }

    public MM_Symptom (int ID, String greekName, String symptomName, String symptomDesc, int mode) {
        super();
        this.sSymptomID = ID;
        this.sGreekName = greekName;
        this.sSymptomName = symptomName;
        this.sSymptomDesc = symptomDesc;
        this.sMode = mode;
    }

    public MM_Symptom (int ID, String greekName, String symptomName, String symptomDesc) {
        super();
        this.sSymptomID = ID;
        this.sGreekName = greekName;
        this.sSymptomName = symptomName;
        this.sSymptomDesc = symptomDesc;
    }

    public MM_Symptom (String greekName, String symptomName, String symptomDesc) {
        super();
        this.sGreekName = greekName;
        this.sSymptomName = symptomName;
        this.sSymptomDesc = symptomDesc;
    }

    public MM_Symptom (String symptomName) {
        super();
        this.sSymptomName = symptomName;
    }

    public MM_Symptom (int ID) {
        super();
        this.sSymptomID = ID;
    }

    public MM_Symptom (int ID, String symptomName) {
        super();
        this.sSymptomID = ID;
        this.sSymptomName = symptomName;
    }

    public int GetSymptomID() { return sSymptomID; }

    public void SetSymptomID(int ID) { sSymptomID = ID; }

    public String GetGreekName() {
        return sGreekName;
    }

    public void SetGreekName(String greekName) {
        sGreekName = greekName;
    }

    public String GetSymptomName() {
        return sSymptomName;
    }

    public void SetSymptomName(String symptomName) { sSymptomName = symptomName; }

    public String GetSymptomDesc() {
        return sSymptomDesc;
    }

    public void SetSymptomDesc(String symptomDesc) {
        sSymptomDesc = symptomDesc;
    }

    public int GetMode() {
        return sMode;
    }

    public void SetMode(int mode) {
        sMode = mode;
    }
}