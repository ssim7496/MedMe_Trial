package com.example.siyo_pc.medme_trial.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.siyo_pc.medme_trial.classes.MM_Sickness;

import java.util.ArrayList;

public class MedMe_Helper extends SQLiteOpenHelper {
    private static final String TAG = MedMe_Helper.class.getName();

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "MedMe_Trial";
    private static final String TABLE_SICKNESS = "Sickness";
    private static final String TABLE_SYMPTOM = "Symptom";
    private static final String TABLE_DISEASE = "Disease";

    private static final String SICKNESS_ID = "SicknessID";
    private static final String SICKNESS_GREEK_NAME = "GreekName";
    private static final String SICKNESS_NAME = "SicknessName";
    private static final String SICKNESS_DESC = "SicknessDesc";
    private static final String SICKNESS_MODE = "Mode";

    private static final String SYMPTOM_ID = "SymptomID";
    private static final String SYMPTOM_GREEK_NAME = "GreekName";
    private static final String SYMPTOM_NAME = "SymptomName";
    private static final String SYMPTOM_DESC = "SymptomDesc";
    private static final String SYMPTOM_MODE = "Mode";

    private static final String DISEASE_ID = "DiseaseID";
    private static final String DISEASE_SICKNESS_ID = "SicknessID";
    private static final String DISEASE_SYMPTOM_ID = "SymptomID";
    private static final String DISEASE_GREEK_NAME = "GreekName";
    private static final String DISEASE_NAME = "DiseaseName";
    private static final String DISEASE_DESC = "DiseaseDesc";
    private static final String DISEASE_MODE = "Mode";

    private static final String CREATE_SICKNESS = "CREATE TABLE " + TABLE_SICKNESS + " ( " +
            SICKNESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            SICKNESS_GREEK_NAME + " VARCHAR(255) NOT NULL, " +
            SICKNESS_NAME + " VARCHAR(255) NOT NULL, " +
            SICKNESS_DESC + " VARCHAR(255) NOT NULL, " +
            SICKNESS_MODE + " NUMERIC NOT NULL DEFAULT 1)";

    private static final String CREATE_SYMPTOM = "CREATE TABLE " + TABLE_SYMPTOM + " ( " +
            SYMPTOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            SYMPTOM_GREEK_NAME + " VARCHAR(255) NOT NULL, " +
            SYMPTOM_NAME + " VARCHAR(255) NOT NULL, " +
            SYMPTOM_DESC + " VARCHAR(255) NOT NULL, " +
            SYMPTOM_MODE + " NUMERIC NOT NULL DEFAULT 1)";

    private static final String CREATE_DISEASE = "CREATE TABLE " + TABLE_DISEASE + " ( " +
            DISEASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            DISEASE_SICKNESS_ID + " INTEGER , " +
            DISEASE_SYMPTOM_ID + " INTEGER , " +
            DISEASE_GREEK_NAME + " VARCHAR(255) NOT NULL, " +
            DISEASE_NAME + " VARCHAR(255) NOT NULL, " +
            DISEASE_DESC + " VARCHAR(255) NOT NULL, " +
            DISEASE_MODE + " NUMERIC NOT NULL DEFAULT 1)";

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
            boolean sicknessMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE))) != 0;

            MM_Sickness sickness = new MM_Sickness(sickID, greekName, sicknessName, sicknessDesc, sicknessMode);
            return sickness;
        }else{
            return null;
        }
    }

    public ArrayList<MM_Sickness> GetAllSicknesses(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MM_Sickness> listSickness = new ArrayList<MM_Sickness>();

        String query = "SELECT * FROM " + TABLE_SICKNESS;
        Cursor cur = db.rawQuery(query, null);

        if (cur.moveToFirst()) {
            do {
                int sickID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_ID)));
                String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_GREEK_NAME));
                String sicknessName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_NAME));
                String sicknessDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_DESC));
                //boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));
                boolean sicknessMode = Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE))) != 0;
                MM_Sickness sickness = new MM_Sickness(sickID, greekName, sicknessName, sicknessDesc, sicknessMode);
                listSickness.add(sickness);
            }while (cur.moveToNext());
        }

        return listSickness;
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

        db.update(TABLE_SICKNESS, values, SICKNESS_ID + " = ?", new String[]{
                String.valueOf(sickness.GetID())
        });
        db.close();
    }

    public void DeleteSickness(int sicknessID){
        //Log.d(TAG, "Updating sickness to " + TABLE_SICKNESS);
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SICKNESS, SICKNESS_ID + " = ?", new String[]{
                String.valueOf(sicknessID)
        });
    }
}