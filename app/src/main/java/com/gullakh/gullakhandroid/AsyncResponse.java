package com.gullakh.gullakhandroid;

import android.app.Dialog;

import org.json.JSONObject;

/**
 * Created by sandeepkotian on 05/03/16.
 */
public interface AsyncResponse {
    void processFinish(JSONObject output);
    void processFinishString(String output,Dialog dialog);
}
