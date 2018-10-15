package com.tasconline.filters;

import com.tasconline.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by kiwitech on 15/10/18.
 */
public class FilterByEnrollmentMethod {

    public JSONArray filterByEnrollmentMethod(JSONArray plans) {
        JSONArray arr = new JSONArray();
        int l = plans.length();

        for(int i = 0; i < l; i++) {
            JSONObject plan = (JSONObject) plans.get(i);
            JSONArray methods = plan.optJSONArray("enrollmentMethods");
//            System.out.println( "------- enrollmentMethods " + methods.toString());
            if(methods.toString().contains("OnlineAndMobileEnrollment")) {
                arr.put(plan);
            }
        }

        return arr;
    }
}
