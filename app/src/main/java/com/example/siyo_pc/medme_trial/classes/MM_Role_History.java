package com.example.siyo_pc.medme_trial.classes;

import java.util.Date;

public class MM_Role_History {
    private int rhChangerPersonID;
    private String rhChangerRoleID;
    private int rhChangeePersonID;
    private String rhChangeeRoleID;
    private Date rhDateChanged;
    private String rhLogDescription;

    public MM_Role_History() {
        super();
    }

    public MM_Role_History (int changerID, String changerRole, int changeeID, String changeeRole, Date date, String logDesc){
        super();
        this.rhChangerPersonID = changerID;
        this.rhChangerRoleID = changerRole;
        this.rhChangeePersonID = changeeID;
        this.rhChangeeRoleID = changeeRole;
        this.rhDateChanged = date;
        this.rhLogDescription = logDesc;
    }

    public int GetChangerPersonID() { return rhChangerPersonID; }

    public void SetChangerPersonID(int changerID) { rhChangerPersonID = changerID; }

    public String GetChangerRoleID() { return rhChangerRoleID; }

    public void SetChangerRoleID(String changerRole) { rhChangerRoleID = changerRole; }

    public int GetChangeePersonID() { return rhChangeePersonID; }

    public void SetChangeePersonID(int changeeID) { rhChangeePersonID = changeeID; }

    public String GetChangeeRoleID() { return rhChangeeRoleID; }

    public void SetChangeeRoleID(String changeeRole) { rhChangeeRoleID = changeeRole; }

    public Date GetDateChanged() { return rhDateChanged; }

    public void SetDateChanged(Date date) { rhDateChanged = date; }

    public String GetLogDescription() { return rhLogDescription; }

    public void SetLogDescription(String logDesc) { rhLogDescription = logDesc; }
}
