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
        String benefitAccountId = getUUId();
        String payloadEnrollBP = getEnrollBPPayload( (JSONObject)plans.get(1),commandName, benefitAccountId, individualId,clientId, hireDate, payrollScheduleId);

        JSONArray successEnrollingBP = EnrollBP.enrollIndividualBP(payloadEnrollBP, individualId, benefitAccountId, commandName);

    }

    public static String getEnrollBPPayload(JSONObject jsonObject, String commandName, String benefitAccountId, String individualId, String clientId, String hireDate, String payrollScheduleId){
        String planId = (String) jsonObject.get("id");
        String planName = (String) jsonObject.get("name");
        String planDescription = (String) jsonObject.get("description");
        String planStartDate = (String) jsonObject.get("planStartDate");
        String planEndDate = (String) jsonObject.get("planEndDate");
        String electionScheduleType = (String) jsonObject.get("electionScheduleType");

        EnrollBaseModel enrollModel = new EnrollBaseModel();
        enrollModel.setId(getUUId());
        enrollModel.setEventCorrelationId(getUUId());
        enrollModel.setProducerId("fd05b24c-0dd7-4af4-976e-844112dac9c3");
        enrollModel.setCreatedById(getUUId());
        enrollModel.setCreatedBy(individualEmail);
        enrollModel.setType(commandName);
        EnrollDataModel dataModel = enrollModel.getData();
        dataModel.setPayrollScheduleId(payrollScheduleId);
        dataModel.setId(benefitAccountId);
        dataModel.setParentId(individualId);
        dataModel.setClientId(clientId);
        dataModel.setPlanId(planId);
        dataModel.setPlanName(planName);
        dataModel.setPlanDescription(planDescription);
        dataModel.setPlanStartDate(planStartDate);
        dataModel.setPlanEndDate(planEndDate);
        dataModel.setHireDate(hireDate);
        dataModel.setElectionScheduleType(electionScheduleType);
        dataModel.setCreatedBy(individualEmail);
        dataModel.setLastTransition(commandName);

        Gson gson = new Gson();
        Object request = gson.toJson(enrollModel);

        return request.toString();
    }

    public static String getUUId(){
        return UUID.randomUUID().toString();
    }

}
