package com.example.siyo_pc.medme_trial.classes;

public class MM_Sickness {

    private int sSicknessID;
    private String sGreekName;
    private String sSicknessName;
    private String sSicknessDesc;
    private boolean sMode;

    public MM_Sickness (int ID, String greekName, String sicknessName, String sicknessDesc, boolean mode) {
        super();
        this.sSicknessID = ID;
        this.sGreekName = greekName;
        this.sSicknessName = sicknessName;
        this.sSicknessDesc = sicknessDesc;
        this.sMode = mode;
    }

    public MM_Sickness (String sicknessName) {
        super();
        this.sSicknessName = sicknessName;
    }

    public int GetID() { return sSicknessID; }

    public void SetID(int ID) { sSicknessID = ID; }

    public String GetGreekName() {
        return sGreekName;
    }

    public void SetGreekName(String greekName) {
        sGreekName = greekName;
    }

    public String GetSicknessName() {
        return sSicknessName;
    }

    public void SetSicknessName(String sicknessName) {
        sSicknessName = sicknessName;
    }

    public String GetSicknessDesc() {
        return sSicknessDesc;
    }

    public void SetSicknessDesc(String sicknessDesc) {
        sSicknessDesc = sicknessDesc;
    }

    public boolean GetMode() {
        return sMode;
    }

    public void SetMode(boolean mode) {
        sMode = mode;
    }
}