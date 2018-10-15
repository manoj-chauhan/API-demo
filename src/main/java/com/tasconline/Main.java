package com.tasconline;

import java.io.IOException;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
        String hireDate = (String) employer.get("hireDate");

        JSONArray enrolledPlans = IndividualBenefitPlans.getIndividualEnrolledBenefitPlans(individualId, clientId);

        JSONArray plans = BenefitPlans.getClientBenefitPlans(clientId);
        String payrollScheduleId = "";
        for (int i = 0 ; i < plans.length(); i++) {
            JSONObject jsonObject = (JSONObject) plans.get(i);
            String planId = (String) jsonObject.get("id");
            JSONArray funding = Funding.getFundingSources(clientId, planId);
            payrollScheduleId = (String)((JSONObject)funding.get(0)).get("id");
            System.out.println("Funding...."+ funding.get(0).toString());
        }

        String commandName = "StartToEnrolled";
        String benefitAccountId = Utility.getUUId();
        String payloadEnrollBP = EnrollBP.getEnrollBPPayload( (JSONObject)plans.get(2),individualEmail, commandName, benefitAccountId, individualId,clientId, hireDate, payrollScheduleId);
        JSONArray successEnrollingBP = EnrollBP.enrollIndividualBP(payloadEnrollBP, individualId, benefitAccountId, commandName);

    }

}
