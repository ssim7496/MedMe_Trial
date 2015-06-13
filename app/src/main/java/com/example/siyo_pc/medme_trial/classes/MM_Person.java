package com.example.siyo_pc.medme_trial.classes;

public class MM_Person {
    private int pPersonID;
    private String pPersonName;
    private String pPersonSurname;
    private String pPersonEmailAddress;
    private String pPersonCellphoneNumber;
    private String pPersonPassword;
    private String pPasswordRecoveryQuestion;
    private String pPasswordRecoveryAnswer;
    private String pPersonRoleID;
    private boolean pMode;

    public MM_Person (int personID, String name, String surname, String emailAddress, String cellNumber, String password, String passwordRecoveryQuestion,
                   String passwordRecoveryAnswer, String personRoleID, boolean mode){
        super();
        this.pPersonID = personID;
        this.pPersonName = name;
        this.pPersonSurname = surname;
        this.pPersonEmailAddress = emailAddress;
        this.pPersonCellphoneNumber = cellNumber;
        this.pPersonPassword = password;
        this.pPasswordRecoveryQuestion = passwordRecoveryQuestion;
        this.pPasswordRecoveryAnswer = passwordRecoveryAnswer;
        this.pPersonRoleID = personRoleID;
        this.pMode = mode;
    }

    public int GetPersonID() { return pPersonID; }

    public void SetPersonID (int personID) {pPersonID = personID;}

    public String GetPersonName() { return pPersonName; }

    public void SetPersonName(String name ) { pPersonName = name; }

    public String GetPersonSurname() { return pPersonSurname; }

    public void SetPersonSurname(String surname ) { pPersonSurname = surname; }

    public String GetPersonEmailAddress() { return pPersonEmailAddress; }

    public void SetPersonEmailAddress(String emailAddress ) { pPersonEmailAddress = emailAddress; }

    public String GetPersonCellphoneNumber() { return pPersonCellphoneNumber; }

    public void SetPersonCellphoneNumber(String cellNumber ) { pPersonCellphoneNumber = cellNumber; }

    public String GetPersonPassword() { return pPersonPassword; }

    public void SetPersonPassword(String password ) { pPersonPassword = password; }

    public String GetPersonRecoveryQuestion() { return pPasswordRecoveryQuestion; }

    public void SetPersonRecoveryQuestion(String passwordRecoveryQuestion ) { pPasswordRecoveryQuestion = passwordRecoveryQuestion; }

    public String GetPersonRecoveryAnswer() { return pPasswordRecoveryAnswer; }

    public void SetPersonRecoveryAnswer(String passwordRecoveryAnswer ) { pPasswordRecoveryAnswer = passwordRecoveryAnswer; }

    public String GetPersonRoleID() { return pPersonRoleID; }

    public void SetPersonRoleID(String personRoleID ) { pPersonRoleID = personRoleID; }

    public boolean GetMode() {
        return pMode;
    }

    public void SetMode(boolean mode) {
        pMode = mode;
    }
}
