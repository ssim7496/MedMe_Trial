package com.example.siyo_pc.medme_trial.db;

import org.json.JSONObject;

import java.util.List;

public interface AsyncTaskResponse2 {
        void onTaskCompleted2(List<JSONObject> objectList, int passTypeID);
}
