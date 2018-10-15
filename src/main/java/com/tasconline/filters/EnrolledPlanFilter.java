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

        int l1 = enrolled.length();
        int l2 = plans.length();
        int l = l1 + l2;

        for(int i=0; i<l; i++) {
            JSONObject json = null;
            if(i <= l1 -1) {
                json = (JSONObject)enrolled.get(i);
            } else {
                json = (JSONObject)plans.get(i);
            }
            if(!isInFilterdArray(filteredArr, json.optString("id"))){
                filteredArr.put(json);
            }
        }
        return filteredArr;
    }

    private boolean isInFilterdArray(JSONArray arr, String id) {
        boolean isInArray = false;
        int l = arr.length();
        for(int i=0; i<l; i++) {
            JSONObject json = (JSONObject)arr.get(i);
            if(json.optString("id").equals(id)){
                isInArray = true;
                break;
            }
        }
        return isInArray;
    }
}
