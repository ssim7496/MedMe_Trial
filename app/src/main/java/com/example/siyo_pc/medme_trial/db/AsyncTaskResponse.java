package com.example.siyo_pc.medme_trial.db;

import org.json.JSONObject;

import java.util.List;

public interface AsyncTaskResponse {
    void onTaskCompleted(List<JSONObject> objectList);
}
