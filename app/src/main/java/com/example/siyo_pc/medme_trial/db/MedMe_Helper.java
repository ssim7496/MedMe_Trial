package com.example.siyo_pc.medme_trial.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness_Description;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;

import java.util.ArrayList;

public class MedMe_Helper extends SQLiteOpenHelper {
    private static final String TAG = MedMe_Helper.class.getName();

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "MedMe_Trials";
    private static final String TABLE_SICKNESS = "Sickness";
    private static final String TABLE_SYMPTOM = "Symptom";
    private static final String TABLE_DISEASE = "Disease";

    private static final String DISEASE_ID = "DiseaseID";
    private static final String DISEASE_GREEK_NAME = "GreekName";
    private static final String DISEASE_NAME = "DiseaseName";
    private static final String DISEASE_DESC = "DiseaseDesc";
    private static final String DISEASE_MODE = "Mode";

    private static final String SYMPTOM_ID = "SymptomID";
    private static final String SYMPTOM_GREEK_NAME = "GreekName";
    private static final String SYMPTOM_NAME = "SymptomName";
    private static final String SYMPTOM_DESC = "SymptomDesc";
    private static final String SYMPTOM_MODE = "Mode";

    private static final String SICKNESS_ID = "SicknessID";
    private static final String SICKNESS_DISEASE_ID = "DiseaseID";
    private static final String SICKNESS_SYMPTOM_ID = "SymptomID";
    private static final String SICKNESS_GREEK_NAME = "GreekName";
    private static final String SICKNESS_NAME = "SicknessName";
    private static final String SICKNESS_DESC = "SicknessDesc";
    private static final String SICKNESS_MODE = "Mode";

    private static final String CREATE_DISEASE = "CREATE TABLE " + TABLE_DISEASE + " ( " +
            DISEASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            DISEASE_GREEK_NAME + " VARCHAR(255) NOT NULL, " +
            DISEASE_NAME + " VARCHAR(255) NOT NULL, " +
            DISEASE_DESC + " VARCHAR(255) NOT NULL, " +
            DISEASE_MODE + " NUMERIC NOT NULL DEFAULT 1)";

    private static final String CREATE_SYMPTOM = "CREATE TABLE " + TABLE_SYMPTOM + " ( " +
            SYMPTOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            SYMPTOM_GREEK_NAME + " VARCHAR(255) NOT NULL, " +
            SYMPTOM_NAME + " VARCHAR(255) NOT NULL, " +
            SYMPTOM_DESC + " VARCHAR(255) NOT NULL, " +
            SYMPTOM_MODE + " NUMERIC NOT NULL DEFAULT 1)";

    private static final String CREATE_SICKNESS = "CREATE TABLE " + TABLE_SICKNESS + " ( " +
            SICKNESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            SICKNESS_DISEASE_ID + " INTEGER , " +
            SICKNESS_SYMPTOM_ID + " INTEGER , " +
            SICKNESS_GREEK_NAME + " VARCHAR(255) NOT NULL, " +
            SICKNESS_NAME + " VARCHAR(255) NOT NULL, " +
            SICKNESS_DESC + " VARCHAR(255) NOT NULL, " +
            SICKNESS_MODE + " NUMERIC NOT NULL DEFAULT 1)";

    public MedMe_Helper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SICKNESS);
        db.execSQL(CREATE_SYMPTOM);
        db.execSQL(CREATE_DISEASE);
        //Log.d(TAG, TABLE_SICKNESS + " table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SICKNESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYMPTOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISEASE);
        //Log.d(TAG, TABLE_SICKNESS + " table dropped");
        onCreate(db);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /*public Cursor getCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT SicknessID _id, * FROM " + TABLE_SICKNESS, null);
        return cur;
    }*/

    //region Diseases
    public MM_Disease GetDiseaseByID(int diseaseID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DISEASE + " WHERE " + DISEASE_ID + " = " + diseaseID;
        Cursor cur = db.rawQuery(query, null);

        if(cur != null){
            cur.moveToFirst();
            int disID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_ID)));
            String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_GREEK_NAME));
            String diseaseName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_NAME));
            String diseaseDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_DESC));
            //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
            int diseaseMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_MODE)));

            MM_Disease disease = new MM_Disease(disID, greekName, diseaseName, diseaseDesc, diseaseMode);
            return disease;
        }else{
            return null;
        }
    }

    public MM_Disease GetDiseaseByName(String diseaseNamed){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DISEASE + " WHERE " + DISEASE_NAME + " = " + diseaseNamed;
        Cursor cur = db.rawQuery(query, null);

        if(cur != null){
            cur.moveToFirst();
            int disID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_ID)));
            String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_GREEK_NAME));
            String diseaseName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_NAME));
            String diseaseDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_DESC));
            //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
            int diseaseMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_MODE)));

            MM_Disease disease = new MM_Disease(disID, greekName, diseaseName, diseaseDesc, diseaseMode);
            return disease;
        }else{
            return null;
        }
    }

    public ArrayList<MM_Disease> GetAllDiseases(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MM_Disease> listDiseases = new ArrayList<MM_Disease>();

        String query = "SELECT * FROM " + TABLE_DISEASE + " ORDER BY " + DISEASE_NAME + " ASC ";
        Cursor cur = db.rawQuery(query, null);

        if (cur.moveToFirst()) {
            do {
                int disID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_ID)));
                String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_GREEK_NAME));
                String diseaseName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_NAME));
                String diseaseDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_DESC));
                //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
                int diseaseMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_MODE)));

                MM_Disease disease = new MM_Disease(disID, greekName, diseaseName, diseaseDesc, diseaseMode);
                listDiseases.add(disease);
            }while (cur.moveToNext());
        }

        return listDiseases;
    }

    public ArrayList<MM_Disease> GetAllDiseasesForSymptom(int symptomID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MM_Disease> listDiseases = new ArrayList<MM_Disease>();

        String query = "SELECT DISTINCT ( D." + DISEASE_ID + " ) , D." + DISEASE_GREEK_NAME + " , D." + DISEASE_NAME + " , D." + DISEASE_DESC + " , D." + DISEASE_MODE +
                " FROM " + TABLE_DISEASE + " D , " + TABLE_SYMPTOM + " S , " + TABLE_SICKNESS + " SI " +
                " WHERE D." + DISEASE_ID  + " = SI." + SICKNESS_DISEASE_ID + " AND SI." + SICKNESS_SYMPTOM_ID + " = " + symptomID +
                " ORDER BY " + DISEASE_NAME + " ASC ";
        Cursor cur = db.rawQuery(query, null);

        if (cur.moveToFirst()) {
            do {
                int disID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_ID)));
                String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_GREEK_NAME));
                String diseaseName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_NAME));
                String diseaseDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_DESC));
                //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
                int diseaseMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_MODE)));

                MM_Disease disease = new MM_Disease(disID, greekName, diseaseName, diseaseDesc, diseaseMode);
                listDiseases.add(disease);
            }while (cur.moveToNext());
        }

        return listDiseases;
    }

    public void AddDisease(MM_Disease disease){
        Log.d(TAG, "Adding disease to " + TABLE_DISEASE);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DISEASE_GREEK_NAME, disease.GetGreekName());
        values.put(DISEASE_NAME, disease.GetDiseaseName());
        values.put(DISEASE_DESC, disease.GetDiseaseDesc());
        values.put(DISEASE_MODE, 1);

        db.insertWithOnConflict(TABLE_DISEASE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void UpdateDisease(MM_Disease disease){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DISEASE_GREEK_NAME, disease.GetGreekName());
        values.put(DISEASE_NAME, disease.GetDiseaseName());
        values.put(DISEASE_DESC, disease.GetDiseaseDesc());

        db.update(TABLE_DISEASE, values, DISEASE_ID + " = ?", new String[]{
                String.valueOf(disease.GetDiseaseID())
        });
        db.close();
    }

    public void DeleteDisease(int diseaseID){
        //Log.d(TAG, "Updating sickness to " + TABLE_SICKNESS);
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_DISEASE, DISEASE_ID + " = ?", new String[]{
                String.valueOf(diseaseID)
        });
    }
    //endregion

    //region Symptoms
    public MM_Symptom GetSymptomByID(int symptomID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SYMPTOM + " WHERE " + SYMPTOM_ID + " = " + symptomID;
        Cursor cur = db.rawQuery(query, null);

        if(cur != null){
            cur.moveToFirst();
            int sympID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_ID)));
            String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_GREEK_NAME));
            String symptomName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_NAME));
            String symptomDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_DESC));
            //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
            int symptomMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_MODE)));

            MM_Symptom symptom = new MM_Symptom(sympID, greekName, symptomName, symptomDesc, symptomMode);
            return symptom;
        }else{
            return null;
        }
    }

    public MM_Symptom GetSymptomByName(String symptomNamed){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SYMPTOM + " WHERE " + SYMPTOM_NAME + " = " + symptomNamed;
        Cursor cur = db.rawQuery(query, null);

        if(cur != null){
            cur.moveToFirst();
            int sympID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_ID)));
            String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_GREEK_NAME));
            String symptomName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_NAME));
            String symptomDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_DESC));
            //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
            int symptomMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_MODE)));

            MM_Symptom symptom = new MM_Symptom(sympID, greekName, symptomName, symptomDesc, symptomMode);
            return symptom;
        }else{
            return null;
        }
    }

    public ArrayList<MM_Symptom> GetAllSymptoms(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MM_Symptom> listSymptoms = new ArrayList<MM_Symptom>();

        String query = "SELECT * FROM " + TABLE_SYMPTOM + " ORDER BY " + SYMPTOM_NAME + " ASC ";
        Cursor cur = db.rawQuery(query, null);

        if (cur.moveToFirst()) {
            do {
                int sympID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_ID)));
                String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_GREEK_NAME));
                String symptomName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_NAME));
                String symptomDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_DESC));
                //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
                int symptomMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_MODE)));

                MM_Symptom symptom = new MM_Symptom(sympID, greekName, symptomName, symptomDesc, symptomMode);
                listSymptoms.add(symptom);
            }while (cur.moveToNext());
        }

        return listSymptoms;
    }

    public ArrayList<MM_Symptom> GetAllSymptomsForDisease(int diseaseID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MM_Symptom> listSymptoms = new ArrayList<MM_Symptom>();

        String query = "SELECT DISTINCT ( S." + SYMPTOM_ID + " ) , S." + SYMPTOM_GREEK_NAME + " , S." + SYMPTOM_NAME + " , S." + SYMPTOM_DESC + " , S." + SYMPTOM_MODE +
                " FROM " + TABLE_DISEASE + " D , " + TABLE_SYMPTOM + " S , " + TABLE_SICKNESS + " SI " +
                " WHERE S." + SYMPTOM_ID  + " = SI." + SICKNESS_SYMPTOM_ID + " AND SI." + SICKNESS_DISEASE_ID + " = " + diseaseID +
                " ORDER BY " + DISEASE_NAME + " ASC ";
        Cursor cur = db.rawQuery(query, null);

        if (cur.moveToFirst()) {
            do {
                int sympID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_ID)));
                String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_GREEK_NAME));
                String symptomName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_NAME));
                String symptomDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_DESC));
                //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
                int symptomMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_MODE)));

                MM_Symptom symptom = new MM_Symptom(sympID, greekName, symptomName, symptomDesc, symptomMode);
                listSymptoms.add(symptom);
            }while (cur.moveToNext());
        }

        return listSymptoms;
    }

    public void AddSymptom(MM_Symptom symptom){
        Log.d(TAG, "Adding symptom to " + TABLE_SYMPTOM);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SYMPTOM_GREEK_NAME, symptom.GetGreekName());
        values.put(SYMPTOM_NAME, symptom.GetSymptomName());
        values.put(SYMPTOM_DESC, symptom.GetSymptomDesc());
        values.put(SYMPTOM_MODE, 1);

        db.insertWithOnConflict(TABLE_SYMPTOM, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void UpdateSymptom(MM_Symptom symptom){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SYMPTOM_GREEK_NAME, symptom.GetGreekName());
        values.put(SYMPTOM_NAME, symptom.GetSymptomName());
        values.put(SYMPTOM_DESC, symptom.GetSymptomDesc());

        db.update(TABLE_SYMPTOM, values, SYMPTOM_ID + " = ?", new String[]{
                String.valueOf(symptom.GetSymptomID())
        });
        db.close();
    }

    public void DeleteSymptom(int symptomID){
        //Log.d(TAG, "Updating sickness to " + TABLE_SICKNESS);
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SYMPTOM, SYMPTOM_ID + " = ?", new String[]{
                String.valueOf(symptomID)
        });
    }
    //endregion

    //region Sicknesses
    public MM_Sickness GetSicknessByID(int sicknessID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SICKNESS + " WHERE " + SICKNESS_ID + " = " + sicknessID;
        Cursor cur = db.rawQuery(query, null);

        if(cur != null){
            cur.moveToFirst();
            int sickID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_ID)));
            String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_GREEK_NAME));
            String sicknessName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_NAME));
            String sicknessDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_DESC));
            //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
            int sicknessMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));

            MM_Sickness sickness = new MM_Sickness(sickID, greekName, sicknessName, sicknessDesc, sicknessMode);
            return sickness;
        }else{
            return null;
        }
    }

    public MM_Sickness GetSicknessByName(String sicknessNamed){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SICKNESS + " WHERE " + SICKNESS_NAME + " = " + sicknessNamed;
        Cursor cur = db.rawQuery(query, null);

        if(cur != null){
            cur.moveToFirst();
            int sickID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_ID)));
            String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_GREEK_NAME));
            String sicknessName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_NAME));
            String sicknessDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_DESC));
            //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
            int sicknessMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));

            MM_Sickness sickness = new MM_Sickness(sickID, greekName, sicknessName, sicknessDesc, sicknessMode);
            return sickness;
        }else{
            return null;
        }
    }

    public ArrayList<MM_Sickness> GetAllSicknesses(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MM_Sickness> listSicknesses = new ArrayList<MM_Sickness>();

        String query = "SELECT * FROM " + TABLE_SICKNESS + " ORDER BY " + SICKNESS_NAME + " ASC ";
        Cursor cur = db.rawQuery(query, null);

        if (cur.moveToFirst()) {
            do {
                int sickID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_ID)));
                String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_GREEK_NAME));
                String sicknessName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_NAME));
                String sicknessDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_DESC));
                //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
                int sicknessMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));

                MM_Sickness sickness = new MM_Sickness(sickID, greekName, sicknessName, sicknessDesc, sicknessMode);
                listSicknesses.add(sickness);
            }while (cur.moveToNext());
        }

        return listSicknesses;
    }

    public ArrayList<MM_Disease> GetAllDiseasesForSickness(int sicknessID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MM_Disease> listDiseases = new ArrayList<MM_Disease>();

        String query = "SELECT DISTINCT ( D." + DISEASE_ID + " ) , D." + DISEASE_GREEK_NAME + " , D." + DISEASE_NAME + " , D." + DISEASE_DESC + " , D." + DISEASE_MODE +
                " FROM " + TABLE_DISEASE + " D , " + TABLE_SYMPTOM + " S , " + TABLE_SICKNESS + " SI " +
                " WHERE D." + DISEASE_ID  + " = SI." + SICKNESS_DISEASE_ID + " AND SI." + SICKNESS_ID + " = " + sicknessID +
                " ORDER BY " + DISEASE_NAME + " ASC ";
        Cursor cur = db.rawQuery(query, null);

        if (cur.moveToFirst()) {
            do {
                int disID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_ID)));
                String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_GREEK_NAME));
                String diseaseName = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_NAME));
                String diseaseDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_DESC));
                //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
                int diseaseMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_MODE)));

                MM_Disease disease = new MM_Disease(disID, greekName, diseaseName, diseaseDesc, diseaseMode);
                listDiseases.add(disease);
            }while (cur.moveToNext());
        }

        return listDiseases;
    }

    public ArrayList<MM_Symptom> GetAllSymptomsForSickness(int sicknessID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MM_Symptom> listSymptoms = new ArrayList<MM_Symptom>();

        String query = "SELECT DISTINCT ( S." + SYMPTOM_ID + " ) , S." + SYMPTOM_GREEK_NAME + " , S." + SYMPTOM_NAME + " , S." + SYMPTOM_DESC + " , S." + SYMPTOM_MODE +
                " FROM " + TABLE_DISEASE + " D , " + TABLE_SYMPTOM + " S , " + TABLE_SICKNESS + " SI " +
                " WHERE S." + SYMPTOM_ID  + " = SI." + SICKNESS_SYMPTOM_ID + " AND SI." + SICKNESS_ID + " = " + sicknessID +
                " ORDER BY " + DISEASE_NAME + " ASC ";
        Cursor cur = db.rawQuery(query, null);

        if (cur.moveToFirst()) {
            do {
                int sympID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_ID)));
                String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_GREEK_NAME));
                String symptomName = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_NAME));
                String symptomDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SYMPTOM_DESC));
                //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
                int diseaseMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.DISEASE_MODE)));

                MM_Symptom symptom = new MM_Symptom(sympID, greekName, symptomName, symptomDesc, diseaseMode);
                listSymptoms.add(symptom);
            }while (cur.moveToNext());
        }

        return listSymptoms;
    }

    public void AddSickness(MM_Sickness sickness){
        Log.d(TAG, "Adding sickness to " + TABLE_SICKNESS);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SICKNESS_GREEK_NAME, sickness.GetGreekName());
        values.put(SICKNESS_NAME, sickness.GetSicknessName());
        values.put(SICKNESS_DESC, sickness.GetSicknessDesc());
        values.put(SICKNESS_MODE, 1);

        db.insertWithOnConflict(TABLE_SICKNESS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void UpdateSickness(MM_Sickness sickness){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SICKNESS_GREEK_NAME, sickness.GetGreekName());
        values.put(SICKNESS_NAME, sickness.GetSicknessName());
        values.put(SICKNESS_DESC, sickness.GetSicknessDesc());
        values.put(SICKNESS_MODE, 1);

        db.update(TABLE_SICKNESS, values, SICKNESS_ID + " = ? AND " + SICKNESS_DISEASE_ID + " = ? AND " + SICKNESS_SYMPTOM_ID + " = ?", new String[]{
                String.valueOf(sickness.GetSicknessID())
        });
        db.close();
    }

    public void DeleteSickness(int sicknessID, int diseaseID, int symptomID){
        //Log.d(TAG, "Updating sickness to " + TABLE_SICKNESS);
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SICKNESS, SICKNESS_ID + " = ? AND " + SICKNESS_DISEASE_ID + " = ? AND " + SICKNESS_SYMPTOM_ID + " = ?", new String[]{
                String.valueOf(sicknessID), String.valueOf(diseaseID), String.valueOf(symptomID)
        });
    }
    //endregion
}