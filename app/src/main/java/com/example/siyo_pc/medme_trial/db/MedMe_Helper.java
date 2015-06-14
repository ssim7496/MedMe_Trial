package com.example.siyo_pc.medme_trial.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.siyo_pc.medme_trial.Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;

public class MedMe_Helper extends SQLiteOpenHelper {
    private static final String TAG = MedMe_Helper.class.getName();

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "MedMe_Trial";
    private static final String TABLE_SICKNESS = "Sickness";

    public static final String SICKNESS_ID = "SicknessID";
    public static final String SICKNESS_GREEK_NAME = "GreekName";
    public static final String SICKNESS_NAME = "SicknessName";
    public static final String SICKNESS_DESC = "SicknessDesc";
    public static final String SICKNESS_MODE = "Mode";

    public static final String CREATE_SICKNESS = "CREATE TABLE " + TABLE_SICKNESS + " ( " +
            SICKNESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
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
        Log.d(TAG, TABLE_SICKNESS + " table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SICKNESS);
        Log.d(TAG, TABLE_SICKNESS + " table dropped");
        onCreate(db);
    }

    public Cursor getCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT SicknessID _id, * FROM " + TABLE_SICKNESS, null);
        return cur;
    }

    public MM_Sickness GetSicknessByID(int sicknessID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_SICKNESS + " WHERE " + SICKNESS_ID + " = " + sicknessID, null);

        if(cur != null){
            cur.moveToFirst();
            int sickID  =  Integer.parseInt(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_ID)));
            String greekName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_GREEK_NAME));
            String sicknessName = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_NAME));
            String sicknessDesc = cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_DESC));
            boolean sicknessMode = Boolean.parseBoolean(cur.getString(cur.getColumnIndex(MedMe_Helper.SICKNESS_MODE)));

            MM_Sickness sickness = new MM_Sickness(sickID, greekName, sicknessName, sicknessDesc, sicknessMode);
            return sickness;
        }else{
            return null;
        }
    }

    public void AddSickness(MM_Sickness sickness){
        Log.d(TAG, "Adding sickness to " + TABLE_SICKNESS);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, theperson.id);
        values.put(SICKNESS_GREEK_NAME, sickness.GetGreekName());
        values.put(SICKNESS_NAME, sickness.GetSicknessName());
        values.put(SICKNESS_DESC, sickness.GetSicknessDesc());
        values.put(SICKNESS_MODE, 1);

        db.insertWithOnConflict(TABLE_SICKNESS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }
}