package com.example.siyo_pc.medme_trial.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MM_Disease implements Parcelable{
    private int dDiseaseID;
    private String dGreekName;
    private String dDiseaseName;
    private String dDiseaseDesc;
    private int dMode;
    public MM_Disease[] dDiseaseList;

    public MM_Disease () {
        super();
    }

    public MM_Disease (int diseaseID, String greekName, String diseaseName, String diseaseDesc,
                       int mode) {
        super();
        this.dDiseaseID = diseaseID;
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
        this.dMode = mode;
    }

    public MM_Disease (String greekName, String diseaseName, String diseaseDesc,
                       int mode) {
        super();
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
        this.dMode = mode;
    }

    public MM_Disease (int diseaseID, String greekName, String diseaseName, String diseaseDesc) {
        super();
        this.dDiseaseID = diseaseID;
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
    }

    public MM_Disease (String greekName, String diseaseName, String diseaseDesc) {
        super();
        this.dGreekName = greekName;
        this.dDiseaseName = diseaseName;
        this.dDiseaseDesc = diseaseDesc;
    }

    public MM_Disease (String diseaseName) {
        super();
        this.dDiseaseName = diseaseName;
    }

    public MM_Disease (int diseaseID) {
        super();
        this.dDiseaseID = diseaseID;
    }

    public int GetDiseaseID() { return dDiseaseID; }

    public void SetDiseaseID(int diseaseID) { dDiseaseID = diseaseID; }

    public String GetGreekName() {
        return dGreekName;
    }

    public void SetGreekName(String greekName) {
        dGreekName = greekName;
    }

    public String GetDiseaseName() {
        return dDiseaseName;
    }

    public void SetDiseaseName(String diseaseName) {
        dDiseaseName = diseaseName;
    }

    public String GetDiseaseDesc() {
        return dDiseaseDesc;
    }

    public void SetDiseaseDesc(String diseaseDesc) {
        dDiseaseDesc = diseaseDesc;
    }

    public int GetMode() {
        return dMode;
    }

    public void SetMode(int mode) {
        dMode = mode;
    }

    //parcel methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dDiseaseID);
        dest.writeString(dGreekName);
        dest.writeString(dDiseaseName);
        dest.writeString(dDiseaseDesc);
        dest.writeArray(dDiseaseList);
        //dest.writeTypedList(dDiseaseList);
    }

    public static final Parcelable.Creator<MM_Disease> CREATOR = new Parcelable.Creator<MM_Disease>() {
        public MM_Disease createFromParcel(Parcel in) {
            return new MM_Disease(in);
        }

        public MM_Disease[] newArray(int size) {
            return  new MM_Disease[size];
        }
    };

    private MM_Disease(Parcel in) {
        this.dDiseaseID = in.readInt();
        this.dGreekName = in.readString();
        this.dDiseaseName = in.readString();
        this.dDiseaseDesc = in.readString();
        in.readTypedArray(dDiseaseList, CREATOR);
        //in.readTypedList(dDiseaseList, MM_Disease.CREATOR);
    }
}
