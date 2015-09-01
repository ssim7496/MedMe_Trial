package com.example.siyo_pc.medme_trial.classes;

public class MM_Functionality {
    private int fFunctionID;
    private String fFunctionName;
    private String fFunctionDesc;
    //private String fFunctionQuery;
    private  int fMode;

    public MM_Functionality() {
        super();
    }

    public MM_Functionality(int functionID, String name, String desc, int mode) {
        super();
        this.fFunctionID = functionID;
        this.fFunctionName = name;
        this.fFunctionDesc = desc;
        this.fMode = mode;
    }

    public int GetFunctionID() {
        return fFunctionID;
    }

    public void SetFunctionID(int functionID) {
        fFunctionID = functionID;
    }

    public String GetFunctionName() {
        return fFunctionName;
    }

    public void SetFunctionName(String name) {
        fFunctionName = name;
    }

    public String GetFunctionDesc() {
        return fFunctionDesc;
    }

    public void SetFunctionDesc(String desc) {
        fFunctionDesc = desc;
    }

    public int GetMode() {
        return fMode;
    }

    public void SetMode(int mode) {
        fMode = mode;
    }
}
