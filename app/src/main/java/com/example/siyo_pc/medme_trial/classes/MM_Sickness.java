package com.example.siyo_pc.medme_trial.classes;

public class MM_Sickness {

    private int sSicknessID;
    private String sGreekName;
    private String sSicknessName;
    private String sSicknessDesc;
    private int sMode;

    public MM_Sickness(){
        super();
    }

    public MM_Sickness (int ID, String greekName, String sicknessName, String sicknessDesc, int mode) {
        super();
        this.sSicknessID = ID;
        this.sGreekName = greekName;
        this.sSicknessName = sicknessName;
        this.sSicknessDesc = sicknessDesc;
        this.sMode = mode;
    }

    public MM_Sickness (int ID, String greekName, String sicknessName, String sicknessDesc) {
        super();
        this.sSicknessID = ID;
        this.sGreekName = greekName;
        this.sSicknessName = sicknessName;
        this.sSicknessDesc = sicknessDesc;
    }

    public MM_Sickness (String greekName, String sicknessName, String sicknessDesc) {
        super();
        this.sGreekName = greekName;
        this.sSicknessName = sicknessName;
        this.sSicknessDesc = sicknessDesc;
    }

    public MM_Sickness (String sicknessName) {
        super();
        this.sSicknessName = sicknessName;
    }

    public int GetSicknessID() { return sSicknessID; }

    public void SetSicknessID(int sicknessID ) { sSicknessID = sicknessID; }

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

    public int GetMode() {
        return sMode;
    }

    public void SetMode(int mode) {
        sMode = mode;
    }
}