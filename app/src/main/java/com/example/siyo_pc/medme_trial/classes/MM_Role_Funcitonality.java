package com.example.siyo_pc.medme_trial.classes;

public class MM_Role_Funcitonality {
    private int rfFunctionID;
    private String rfRoleID;
    private  boolean rfMode;

    public MM_Role_Funcitonality() {
        super();
    }

    public MM_Role_Funcitonality(int functionID, String roleID, boolean mode) {
        super();
        this.rfFunctionID = functionID;
        this.rfRoleID = roleID;
        this.rfMode = mode;
    }

    public int GetFunctionID() {
        return rfFunctionID;
    }

    public void SetFunctionID(int functionID) {
        rfFunctionID = functionID;
    }

    public String GetRoleID() {
        return rfRoleID;
    }

    public void SetRoleID(String roleID) {
        rfRoleID = roleID;
    }

    public boolean GetMode() {
        return rfMode;
    }

    public void SetMode(boolean mode) {
        rfMode = mode;
    }
}
