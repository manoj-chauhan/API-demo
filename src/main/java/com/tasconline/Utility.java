package com.tasconline;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

public class Utility {

    private static final int INDENTATION = 4;

    public static String getUUId(){
        return UUID.randomUUID().toString();
    }

    public static void logJson(JSONObject obj) {
        System.out.println(obj.toString(INDENTATION));
    }
    public static void logJson(JSONArray obj) {
        System.out.println(obj.toString(INDENTATION));
    }

}
