package com.example.siyo_pc.medme_trial.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class MM_Sickness implements Parcelable {

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

    //parcel methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sSicknessID);
        dest.writeString(sGreekName);
        dest.writeString(sSicknessName);
        dest.writeString(sSicknessDesc);
    }

    public static final Parcelable.Creator<MM_Sickness> CREATOR = new Parcelable.Creator<MM_Sickness>() {
        public MM_Sickness createFromParcel(Parcel in) {
            return new MM_Sickness(in);
        }

        public MM_Sickness[] newArray(int size) {
            return  new MM_Sickness[size];
        }
    };

    private MM_Sickness(Parcel in) {
        this.sSicknessID = in.readInt();
        this.sGreekName = in.readString();
        this.sSicknessName = in.readString();
        this.sSicknessDesc = in.readString();
    }
}