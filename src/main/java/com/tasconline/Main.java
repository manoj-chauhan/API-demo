package com.tasconline;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class Main {

    private static String individualEmail = "manoj@kiwitech.com";

    public final static void main(String[] args) throws Exception {

        JSONObject individual = Individual.getIndividualInfo(individualEmail);
        JSONObject employer = Employment.getEmployerInfo((String) individual.get("id"));
        JSONArray plans = BenefitPlans.getClientBenefitPlans((String) employer.get("parentId"));

    }

}
