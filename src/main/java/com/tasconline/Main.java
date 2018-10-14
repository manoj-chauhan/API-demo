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

    private static String individualEmail = "manj.muthukumaresan@gmail.com";

    public final static void main(String[] args) throws Exception {

        JSONObject individual = Individual.getIndividualInfo(individualEmail);
        String individualId = (String) individual.get("id");

        JSONObject employer = Employment.getEmployerInfo(individualId);
        String clientId = (String) employer.get("parentId");


        JSONArray enrolledPlans = IndividualBenefitPlans.getIndividualEnrolledBenefitPlans(individualId, clientId);

//        JSONArray plans = BenefitPlans.getClientBenefitPlans(clientId);

//        for (int i = 0 ; i < plans.length(); i++) {
//            JSONObject jsonObject = (JSONObject) plans.get(i);
//            String planId = (String) jsonObject.get("id");
//            Funding.getFundingSources(clientId, planId);
//        }

    }

}
