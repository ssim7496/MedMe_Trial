package com.example.siyo_pc.medme_trial.classes;

public class MM_Role {
    private String rRoleID;
    private String rRoleName;
    private String rRoleDescription;
    private boolean rMode;

    public MM_Role() {
        super();
    }

    public MM_Role (String roleID, String name, String desc, boolean mode){
        super();
        this.rRoleID = roleID;
        this.rRoleName = name;
        this.rRoleDescription = desc;
        this.rMode = mode;
    }

    public String GetRoleID() {
        return rRoleID;
    }

    public void SetRoleID(String roleID) {
        rRoleID = roleID;
    }

    public String GetRoleName() {
        return rRoleName;
    }

    public void SetRoleName(String name) {
        rRoleName = name;
    }

    public String GetRoleDescription() {
        return rRoleDescription;
    }

    public void SetRoleDescription(String desc) {
        rRoleDescription = desc;
    }

    public boolean GetMode() {
        return rMode;
    }

    public void SetMode(boolean mode) {
        rMode = mode;
    }
}
