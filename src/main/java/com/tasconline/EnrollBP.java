package com.tasconline;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class EnrollBP {

    private static final String urlTemplate = Constants.ENV_BASE_URL_ACCOUNT + "/profile/%s/benefitAccount/%s/command/%s";


    public static final JSONArray enrollIndividualBP(String payloadTemplate, String profileId, String benefitAccountId, String commandName) throws IOException {
        System.out.println("########################## Enrolling Individual in Benefit Plan ##############################");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String urlString = String.format(urlTemplate, profileId, benefitAccountId, commandName);
        System.out.println("Url :: " + urlString);

        System.out.println("Payload :: " + payloadTemplate);

        JSONArray responseJson = null;

        try {
            HttpPut httpPut = new HttpPut(urlString);

            httpPut.setHeader("Content-Type", "application/json");
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Authorization", Constants.ACCESS_TOKEN);


            StringEntity entity = new StringEntity(payloadTemplate);
            httpPut.setEntity(entity);

            System.out.println("Fetching Employer Detail: " + httpPut.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        System.out.println("EnrollBP Statue" + status);
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + response);
                    }
                }

            };
            String responseBody = httpclient.execute(httpPut, responseHandler);
            System.out.println("Enrollment Done!!");
//            responseJson = new JSONArray(responseBody);
        } finally {
            httpclient.close();
        }
        return responseJson;
    }

    public static String getEnrollBPPayload(JSONObject jsonObject, String email, String commandName, String benefitAccountId, String individualId, String clientId, String hireDate, String payrollScheduleId){
        String planId = (String) jsonObject.get("id");
        String planName = (String) jsonObject.get("name");
        String planDescription = (String) jsonObject.get("description");
        String planStartDate = (String) jsonObject.get("planStartDate");
        String planEndDate = (String) jsonObject.get("planEndDate");
        String electionScheduleType = (String) jsonObject.get("electionScheduleType");
        EnrollBaseModel enrollModel = new EnrollBaseModel();
        enrollModel.setId(Utility.getUUId());
        enrollModel.setEventCorrelationId(Utility.getUUId());
        enrollModel.setProducerId("fd05b24c-0dd7-4af4-976e-844112dac9c3");
        enrollModel.setCreatedById(Utility.getUUId());
        enrollModel.setCreatedBy(email);
        enrollModel.setType(commandName);
        EnrollDataModel dataModel = enrollModel.getData();
        dataModel.setElectionAmount(2500);
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
        dataModel.setCreatedBy(email);
//        dataModel.setLastTransition(commandName);
        dataModel.setLastTransition("EnrolledToEnrolled");

        Gson gson = new Gson();
        Object request = gson.toJson(enrollModel);

        return request.toString();
    }


}
