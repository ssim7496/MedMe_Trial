package com.example.siyo_pc.medme_trial.classes;

/**
 * Created by Siyo-PC on 2015/10/25.
 */
public class MM_DiagnosedSymptoms {
    private int diagnsoedSymptoms;
    private int allSymptoms;
    private double percentage;
    private int totalSymptomsForSickness;

    public MM_DiagnosedSymptoms(int diagnsoedSymptoms, int allSymptoms, double percentage, int totalSymptomsForSickness){
        this.diagnsoedSymptoms = diagnsoedSymptoms;
        this.allSymptoms = allSymptoms;
        this.percentage = percentage;
        this.totalSymptomsForSickness = totalSymptomsForSickness;
    }

    public void SetDiagnosedSymptoms(int diagnosedSymptoms) {
        this.diagnsoedSymptoms = diagnosedSymptoms;
    }

    public int GetDiagnosedSymptoms() {
        return  this.diagnsoedSymptoms;
    }

    public void SetAllSymptoms(int allSymptoms) {
        this.allSymptoms = allSymptoms;
    }

    public int GetAllSymptoms() {
        return  this.allSymptoms;
    }

    public void SetPercentage(int allSymptoms) {
        this.percentage = percentage;
    }

    public double GetPercentage() {
        return  this.percentage;
    }

    public void SetTotalSymptomsForSickness(int totalSymptomsForSickness) {
        this.totalSymptomsForSickness = totalSymptomsForSickness;
    }

    public int GetTotalSymptomsForSickness() {
        return  this.totalSymptomsForSickness;
    }
}
