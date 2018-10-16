package com.tasconline.filters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiwitech on 15/10/18.
 */
public class EnrolledPlanFilter {

    public JSONArray filterOutAlreadyEnrolledPlans(JSONArray enrolled, JSONArray plans){
        JSONArray filteredArr = new JSONArray();

        int l = plans.length();

        for(int i=0; i<l-1; i++) {
            JSONObject json = (JSONObject)plans.get(i);

            if(!isInFilterdArray(enrolled, json.optString("id"))){
                filteredArr.put(json);
            }
        }
        return filteredArr;
    }

    private boolean isInFilterdArray(JSONArray arr, String id) {
        boolean isInArray = false;
        int l = arr.length();
        for(int i=0; i<l-1; i++) {
            JSONObject json = (JSONObject)arr.get(i);
            if(json.optString("id").equals(id)){
                isInArray = true;
                break;
            }
        }
        return isInArray;
    }
}
