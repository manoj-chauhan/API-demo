package com.tasconline.filters;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by kiwitech on 15/10/18.
 */
public class EligibilityClassFilter {

    public JSONArray filterByEligibilityClassId(JSONArray jsonArray, int eligibilityClassId) {
        JSONArray filteredArray = new JSONArray();

        int l = jsonArray.length();
        for(int i = 0; i<l; i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            JSONArray arr = json.optJSONArray("eligibilityClasses");
            for(int j = 0; j< arr.length(); j++) {
                int elibilityId = (int) arr.get(j);
                if(elibilityId == eligibilityClassId) {
                    filteredArray.put(json);
                    break;
                }
            }
        }

        return filteredArray;

    }
}
