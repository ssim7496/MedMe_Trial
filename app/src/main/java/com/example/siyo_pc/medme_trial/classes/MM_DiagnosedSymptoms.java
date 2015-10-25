package com.example.siyo_pc.medme_trial.classes;

/**
 * Created by Siyo-PC on 2015/10/25.
 */
public class MM_DiagnosedSymptoms {
    private int diagnsoedSymptoms;
    private int allSymptoms;

    public MM_DiagnosedSymptoms(int diagnsoedSymptoms, int allSymptoms){
        this.diagnsoedSymptoms = diagnsoedSymptoms;
        this.allSymptoms = allSymptoms;
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
}
